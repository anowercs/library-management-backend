package org.neurogine.librarymanagementbackend.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.neurogine.librarymanagementbackend.entity.Book;
import org.neurogine.librarymanagementbackend.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    public Book add(Book book){
        return bookRepository.save(book);
    }

    public Book update(Book book){
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
