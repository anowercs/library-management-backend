package org.neurogine.librarymanagementbackend.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "books")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String bookName;

    private String picture;

    @Column(length = 1000)
    private String description;

    private String remark;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private BookCategory category;

    // ---------- NOT A DB COLUMN ----------
    @Transient
    private Integer categoryId;
}
