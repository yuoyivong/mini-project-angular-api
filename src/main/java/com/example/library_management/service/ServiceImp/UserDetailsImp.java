package com.example.library_management.service.ServiceImp;

import com.example.library_management.model.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class UserDetailsImp implements UserDetails {

    private static final long serialVersionUID = 1L;

    private UUID user_id;
    private String username;
    private String email;

    @JsonIgnore
    private String password;
    private Integer total_published_books;
    private String role;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImp(UUID user_id, String username, String email, String password, Integer total_published_books, String role) {
        this.user_id = user_id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.total_published_books = total_published_books;
        this.role = role;
//        this.authorities = authorities;
    }

    public static UserDetailsImp build(User user) {

        return new UserDetailsImp(user.getUser_id(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getTotal_published_books(),
                user.getRole()
                );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public UUID getUser_id() {
        return user_id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

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
