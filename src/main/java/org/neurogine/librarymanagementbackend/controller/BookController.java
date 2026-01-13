package org.neurogine.librarymanagementbackend.controller;


import lombok.RequiredArgsConstructor;
import org.neurogine.librarymanagementbackend.entity.Book;
import org.neurogine.librarymanagementbackend.service.BookService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/books")
@CrossOrigin
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    private static final String IMAGE_DIR =
            "/home/anower/All/Interview/Library-Management/library_book_images";

    @GetMapping
    public List<Book> list(){
        return bookService.findAll();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Book add(
            @RequestParam String bookName,
            @RequestParam String description,
            @RequestParam String remark,
            @RequestParam Integer categoryId,
            @RequestParam MultipartFile file

    ) throws IOException{

        Path imageDir = Paths.get(IMAGE_DIR);
        Files.createDirectories(imageDir);
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path imagePath = imageDir.resolve(filename);
        file.transferTo(imagePath.toFile());

        // 4. Build book entity
        Book book = new Book();
        book.setBookName(bookName);
        book.setDescription(description);
        book.setRemark(remark);
        book.setCategoryId(categoryId);

        // ✅ STORE ONLY FILENAME
        book.setPicture(filename);

        return bookService.add(book);

    }

    @GetMapping("/{id}")
    public Book getById(@PathVariable Integer id) {
        return bookService.getById(id);
    }


    @PutMapping(
            value = "/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public Book update(
            @PathVariable Integer id,
            @RequestParam String bookName,
            @RequestParam String description,
            @RequestParam String remark,
            @RequestParam Integer categoryId,
            @RequestParam(required = false) MultipartFile file
    ) throws IOException {

        return bookService.updateWithImage(
                id, bookName, description, remark, categoryId, file
        );
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        bookService.delete(id);
    }


    @GetMapping("/search")
    public List<Book> search(@RequestParam(required = false) String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            return bookService.findAll();
        }

        return bookService.findByKeyword(keyword);
    }

}