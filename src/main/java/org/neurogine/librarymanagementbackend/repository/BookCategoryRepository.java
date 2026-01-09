package org.neurogine.librarymanagementbackend.repository;

import org.neurogine.librarymanagementbackend.entity.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookCategoryRepository extends JpaRepository<BookCategory, Integer> {
    List<BookCategory> findByCategoryNameContainingOrRemarkContaining(String categoryName, String remark);
}
