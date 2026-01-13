package org.neurogine.librarymanagementbackend.repository;

import org.neurogine.librarymanagementbackend.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    List<Student> findByNameContaining(String keyword);

    List<Student> findByNameContainingIgnoreCaseOrNoContainingIgnoreCase(
            String name,
            String no
    );

    @Query("SELECT s FROM Student s WHERE LOWER(s.name)" +
            " LIKE LOWER(CONCAT('%', :keyword, '%'))" +
            " OR LOWER(s.no) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Student> searchByKeyword(@Param("keyword") String keyword);


}
