package org.neurogine.librarymanagementbackend.entity;

import jakarta.persistence.*;
import lombok.*;


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

    @PrePersist
    @PreUpdate
    private void normalizeNo() {
        if (no != null) {
            no = no.toUpperCase(); // or toUpperCase()
        }
    }
}
