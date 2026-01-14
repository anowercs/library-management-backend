package org.neurogine.librarymanagementbackend.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.neurogine.librarymanagementbackend.entity.Book;
import org.neurogine.librarymanagementbackend.entity.BookBorrow;
import org.neurogine.librarymanagementbackend.entity.Student;
import org.neurogine.librarymanagementbackend.repository.BookBorrowRepository;
import org.neurogine.librarymanagementbackend.repository.BookRepository;
import org.neurogine.librarymanagementbackend.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BookBorrowService {

    private final BookBorrowRepository borrowRepository;
    private final BookRepository bookRepository;
    private final StudentRepository studentRepository;

    public BookBorrow borrowBook(Integer bookId, Integer studentId) {

        if (borrowRepository.existsByBookIdAndReturnedFalse(bookId)) {
            throw new RuntimeException("Book is already borrowed");
        }

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        BookBorrow borrow = BookBorrow.builder()
                .book(book)
                .student(student)
                .borrowDate(LocalDate.now())
                .dueDate(LocalDate.now().plusDays(14)) // admin policy
                .returned(false)
                .build();

        return borrowRepository.save(borrow);
    }

      // For old code compatibility
    public void returnBook(Integer borrowId) {
        returnBook(borrowId, null);
    }

    // For return UI
    public void returnBook(Integer borrowId, LocalDate returnDate) {
        BookBorrow borrow = borrowRepository.findById(borrowId)
                .orElseThrow(() -> new RuntimeException("Borrow record not found"));

        if (borrow.isReturned()) {
            throw new RuntimeException("Book already returned");
        }

        borrow.setReturned(true);
        borrow.setReturnDate(
                returnDate != null ? returnDate : LocalDate.now()
        );
    }



    public List<BookBorrow> getBorrowedBooks() {
        return borrowRepository.findByReturnedFalse();
    }

    public List<BookBorrow> getActiveBorrowsByStudentNo(String studentNo) {

        String normalizedNo = studentNo.trim();

        Student student = studentRepository
                .findByNoIgnoreCase(normalizedNo)
                .orElseThrow(() -> new RuntimeException(
                        "Student not found with no: " + normalizedNo
                ));

        return borrowRepository.findByStudentIdAndReturnedFalse(student.getId());
    }



}
