package org.neurogine.librarymanagementbackend.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.neurogine.librarymanagementbackend.entity.Book;
import org.neurogine.librarymanagementbackend.entity.BookCategory;
import org.neurogine.librarymanagementbackend.repository.BookCategoryRepository;
import org.neurogine.librarymanagementbackend.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    public void delete(Integer id){
        bookRepository.deleteById(id);
    }

    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    public List<Book> findByKeyword(String keyword){
        return bookRepository.findByBookNameContainingOrRemarkContainingOrDescriptionContaining(
                keyword, keyword, keyword);
    }
}
