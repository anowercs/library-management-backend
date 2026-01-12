package org.neurogine.librarymanagementbackend.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.neurogine.librarymanagementbackend.entity.Book;
import org.neurogine.librarymanagementbackend.entity.BookCategory;
import org.neurogine.librarymanagementbackend.repository.BookCategoryRepository;
import org.neurogine.librarymanagementbackend.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@RequiredArgsConstructor
@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;

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


    public List<Book> findByKeyword(String keyword){
        return bookRepository.findByBookNameContainingOrRemarkContainingOrDescriptionContaining(
                keyword, keyword, keyword);
    }
}
