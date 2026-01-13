package org.neurogine.librarymanagementbackend.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.neurogine.librarymanagementbackend.entity.Book;
import org.neurogine.librarymanagementbackend.entity.BookCategory;
import org.neurogine.librarymanagementbackend.repository.BookBorrowRepository;
import org.neurogine.librarymanagementbackend.repository.BookCategoryRepository;
import org.neurogine.librarymanagementbackend.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@Service
@Transactional
public class BookService {

    private static final String IMAGE_DIR = "/home/anower/All/Interview/Library-Management/library_book_images";
    private final BookRepository bookRepository;
    private final BookBorrowRepository bookBorrowRepository;

    private final BookCategoryRepository categoryRepository;

    public Book add(Book book) {
        if (book.getCategoryId() != null) {
            BookCategory category = categoryRepository
                    .findById(book.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            book.setCategory(category);
        }
        return bookRepository.save(book);
    }


    public Book getById(Integer id) {
        return bookRepository.findByIdWithCategory(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public Book updateWithImage(
            Integer id,
            String bookName,
            String description,
            String remark,
            Integer categoryId,
            MultipartFile file
    ) throws IOException {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        book.setBookName(bookName);
        book.setDescription(description);
        book.setRemark(remark);

        // update category
        BookCategory category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        book.setCategory(category);


        if (file != null && !file.isEmpty()) {


            if (book.getPicture() != null) {
                Files.deleteIfExists(Paths.get(IMAGE_DIR, book.getPicture()));
            }

            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path imagePath = Paths.get(IMAGE_DIR,filename );
            Files.createDirectories(imagePath.getParent());
            file.transferTo(imagePath.toFile());

            book.setPicture(filename);
        }

        return bookRepository.save(book);
    }

    public Book update(Book book) {
        if (book.getCategoryId() != null) {
            BookCategory category = categoryRepository
                    .findById(book.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            book.setCategory(category);
        }
        return bookRepository.save(book);
    }

    public void delete(Integer id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // 🔴 delete image file from disk
        if (book.getPicture() != null) {
            Path imagePath = Paths.get(
                    "/home/anower/All/Interview/Library-Management/library_book_images",
                    book.getPicture()
            );
            try {
                Files.deleteIfExists(imagePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        bookRepository.delete(book);
    }

    public List<Book> findAll() {
        return bookRepository.findAllWithCategory();
    }


    public List<Book> findByKeyword(String keyword) {
        return bookRepository.searchWithCategory(keyword);
    }

    public List<Book> findAllWithAvailability() {
        List<Book> books = bookRepository.findAllWithCategory();

        books.forEach(book -> {
            boolean borrowed = bookBorrowRepository.existsByBookIdAndReturnedFalse(book.getId());

            book.setAvailable(!borrowed);
        });

        return books;
    }

}
