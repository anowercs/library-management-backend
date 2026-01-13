package org.neurogine.librarymanagementbackend.repository;

import org.neurogine.librarymanagementbackend.entity.Book;
import org.neurogine.librarymanagementbackend.entity.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByBookNameContainingOrRemarkContainingOrDescriptionContaining(String bookName, String remark, String description);

    List<Book> findByCategory(BookCategory category);

    @Query("""
        select distinct b from Book b
        left join fetch b.category
    """)
    List<Book> findAllWithCategory();


    @Query("SELECT b FROM Book b LEFT JOIN FETCH b.category WHERE b.id = :id")
    Optional<Book> findByIdWithCategory(@Param("id") Integer id);

}
