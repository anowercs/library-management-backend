package org.neurogine.librarymanagementbackend.repository;

import org.neurogine.librarymanagementbackend.entity.Book;
import org.neurogine.librarymanagementbackend.entity.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByBookNameContainingOrRemarkContainingOrDescriptionContaining(String bookName, String remark, String description);

    List<Book> findByCategory(BookCategory category);

    @Query("""
        select b from Book b
        left join fetch b.category
    """)
    List<Book> findAllWithCategory();

}
