package org.neurogine.librarymanagementbackend.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "book_categories")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String categoryName;

    private String remark;
}
