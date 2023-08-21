package com.example.library_management.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="users")

public class User {
    @Id
    @Column(name= "user_id")
    @GeneratedValue
    private UUID user_id;

    @NotBlank
    @Column(name = "username", unique = true, columnDefinition = "VARCHAR(100)")
    private String username;

    @NotBlank
    @Column(name = "email", unique = true)
    private String email;
    @NotBlank
    private String password;
    private Integer total_published_books;
    @NotBlank
    private String role;

}
