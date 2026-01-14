package org.neurogine.librarymanagementbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Table(name = "students")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String no;

    @Column(nullable = false)
    private String name;

    private Integer age;

    private String gender;

    @JsonIgnore
    @OneToMany(mappedBy = "student")
    private List<BookBorrow> borrows;

    @PrePersist
    @PreUpdate
    private void normalizeNo() {
        if (no != null) {
            no = no.toUpperCase(); // or toUpperCase()
        }
    }
}
