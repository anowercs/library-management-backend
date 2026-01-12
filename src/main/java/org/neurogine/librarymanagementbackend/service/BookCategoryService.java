package org.neurogine.librarymanagementbackend.service;

import lombok.RequiredArgsConstructor;
import org.neurogine.librarymanagementbackend.entity.BookCategory;
import org.neurogine.librarymanagementbackend.repository.BookCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BookCategoryService {
    private final BookCategoryRepository bookCategoryRepository;

    public BookCategory add(BookCategory bookCategory) {
        return bookCategoryRepository.save(bookCategory);
    }

    public BookCategory update(BookCategory bookCategory) {
        return bookCategoryRepository.save(bookCategory);
    }

    public void delete(Integer id) {
        bookCategoryRepository.deleteById(id);
    }

    public List<BookCategory> findAll() {
        return bookCategoryRepository.findAll();
    }

    public List<BookCategory> findByKeyword(String keyword) {
        return bookCategoryRepository
                .findByCategoryNameContainingOrRemarkContaining(keyword, keyword);
    }
}
