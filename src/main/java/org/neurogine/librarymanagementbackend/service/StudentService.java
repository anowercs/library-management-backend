package org.neurogine.librarymanagementbackend.service;


import lombok.RequiredArgsConstructor;
import org.neurogine.librarymanagementbackend.entity.BookBorrow;
import org.neurogine.librarymanagementbackend.entity.Student;
import org.neurogine.librarymanagementbackend.repository.BookBorrowRepository;
import org.neurogine.librarymanagementbackend.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final BookBorrowRepository bookBorrowRepository;

    public Student add(Student student){
        return studentRepository.save(student);
    }

    public Student update(Student student) {

        Student existing = studentRepository.findById(student.getId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        existing.setNo(student.getNo().toUpperCase());
        existing.setName(student.getName());
        existing.setAge(student.getAge());
        existing.setGender(student.getGender());

        return studentRepository.save(existing);
    }


    public Student getById(Integer id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }


    /*public void delete(Integer id){
        studentRepository.deleteById(id);
    }*/
    /**
     * Delete student with proper validation:
     * 1. Check if student has ACTIVE (unreturned) books - BLOCK deletion
     * 2. If student has RETURNED books - delete those records first, then delete student
     *
     * @param id Student ID
     * @throws RuntimeException if student has active unreturned books
     */
    public void delete(Integer id) {
        // Verify student exists
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Check ACTIVE borrows (returned = false) - CANNOT DELETE IF ANY EXIST
        List<BookBorrow> activeBorrows =
                bookBorrowRepository.findByStudentIdAndReturnedFalse(id);

        if (!activeBorrows.isEmpty()) {
            throw new RuntimeException(
                    "Cannot delete student '" + student.getName() + "'. " +
                            "They have " + activeBorrows.size() + " unreturned book(s)"
            );
        }

        // Get ALL borrow records (including returned ones)
        List<BookBorrow> allBorrows = bookBorrowRepository.findByStudentId(id);

        // Delete all borrow history records first (both active and returned)
        if (!allBorrows.isEmpty()) {
            bookBorrowRepository.deleteAll(allBorrows);
        }

        // Now safe to delete the student
        studentRepository.deleteById(id);
    }

    public List<Student> findAll(){
        return studentRepository.findAll();
    }

    public List<Student> findByKeyword(String keyword){
        return studentRepository.findByNameContaining(keyword);
    }

    public List<Student> search(String keyword) {
        return studentRepository
                .findByNameContainingIgnoreCaseOrNoContainingIgnoreCase(
                        keyword, keyword
                );
    }


}
