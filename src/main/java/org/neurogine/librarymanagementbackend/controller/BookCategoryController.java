package org.neurogine.librarymanagementbackend.controller;


import lombok.RequiredArgsConstructor;
import org.neurogine.librarymanagementbackend.entity.BookCategory;
import org.neurogine.librarymanagementbackend.service.BookCategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@CrossOrigin
public class BookCategoryController {

    private final BookCategoryService bookCategoryService;

    @GetMapping
    public List<BookCategory> list() {
        return bookCategoryService.findAll();
    }

    @PostMapping
    public BookCategory add(@RequestBody BookCategory bookCategory) {
        return bookCategoryService.add(bookCategory);
    }

    @PutMapping("/{id}")
    public BookCategory update(
            @PathVariable Integer id,
            @RequestBody BookCategory bookCategory
    ) {
        bookCategory.setId(id);
        return bookCategoryService.update(bookCategory);
    }

    @GetMapping("/{id}")
    public BookCategory getById(@PathVariable Integer id) {
        return bookCategoryService.getById(id);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        bookCategoryService.delete(id);
    }

    @GetMapping("/search")
    public List<BookCategory> search(@RequestParam String keyword) {
        return bookCategoryService.findByKeyword(keyword);
    }
}
