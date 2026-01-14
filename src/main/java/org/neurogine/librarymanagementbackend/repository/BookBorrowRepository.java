package org.neurogine.librarymanagementbackend.repository;

import org.neurogine.librarymanagementbackend.entity.BookBorrow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookBorrowRepository extends JpaRepository<BookBorrow, Integer> {

    // Is book currently borrowed?
    boolean existsByBookIdAndReturnedFalse(Integer bookId);

    // All currently borrowed books
    List<BookBorrow> findByReturnedFalse();
    List<BookBorrow> findByStudentIdAndReturnedFalse(Integer studentId);


    // Borrow history of a student
    List<BookBorrow> findByStudentId(Integer studentId);
}
