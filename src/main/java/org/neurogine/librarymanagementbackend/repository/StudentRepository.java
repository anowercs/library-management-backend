package org.neurogine.librarymanagementbackend.repository;

import org.neurogine.librarymanagementbackend.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    List<Student> findByNameContaining(String keyword);
}
