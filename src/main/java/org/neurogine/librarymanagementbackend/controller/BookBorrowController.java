package org.neurogine.librarymanagementbackend.controller;

import lombok.RequiredArgsConstructor;
import org.neurogine.librarymanagementbackend.entity.BookBorrow;
import org.neurogine.librarymanagementbackend.service.BookBorrowService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/borrows")
@RequiredArgsConstructor
@CrossOrigin
public class BookBorrowController {

    private final BookBorrowService bookBorrowService;

    // ✅ Borrow book (ADMIN action)
    @PostMapping
    public Map<String, String> borrow(
            @RequestParam Integer bookId,
            @RequestParam Integer studentId
    ) {
        bookBorrowService.borrowBook(bookId, studentId);
        return Map.of("message", "Book borrowed successfully");
    }

    // ✅ Return book
    @PutMapping("/{id}/return")
    public Map<String, String> returnBook(@PathVariable Integer id) {
        bookBorrowService.returnBook(id);
        return Map.of("message", "Book returned successfully");
    }

    // ✅ List currently borrowed books
    @GetMapping
    public List<BookBorrow> listBorrowed() {
        return bookBorrowService.getBorrowedBooks();
    }
}
