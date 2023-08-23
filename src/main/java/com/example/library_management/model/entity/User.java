package com.example.library_management.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="users")
public class User implements UserDetails {
    @Id
    @Column(name= "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID user_id;

    @NotBlank
    @Column(name = "username", unique = true)
    private String username;

    @NotBlank
    @Column(name = "email", unique = true)
    private String email;
    @NotBlank
    private String password;
//    @NotBlank
    @Enumerated(EnumType.STRING)
    private Role role;

//    @NotNull
//    private Integer total_published_books;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

//    @Override
//    public String getUsername() {
//        return username;
//    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
