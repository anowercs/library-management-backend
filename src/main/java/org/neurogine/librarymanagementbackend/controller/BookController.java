package org.neurogine.librarymanagementbackend.controller;


import lombok.RequiredArgsConstructor;
import org.neurogine.librarymanagementbackend.entity.Book;
import org.neurogine.librarymanagementbackend.service.BookService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    public List<Book> list(){
        return bookService.findAll();
    }

    @PostMapping(consumes = "multipart/form-data")
    public Book add(@RequestPart("book") Book book, @RequestPart("file") MultipartFile file) throws IOException{
        if (!file.isEmpty()){
            String uploadDir = "uploads/";
            new File(uploadDir).mkdir();
            String path = uploadDir + file.getOriginalFilename();
            file.transferTo(new File(path));
            book.setPicture(path);
        }
        return bookService.add(book);
    }
    @PutMapping("/{id}")
    public Book update(@PathVariable Integer id, @RequestBody Book book) {
        book.setId(id);
        return bookService.update(book);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        bookService.delete(id);
    }

    @GetMapping("/search")
    public List<Book> search(@RequestParam String keyword) {
        return bookService.findByKeyword(keyword);
    }
}