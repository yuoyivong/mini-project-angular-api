package com.example.library_management.model;

public class User {
    private Integer user_id;
    private String username;
    private String email;
    private String password;
    private Integer total_published_books;
    private String role;

//    default constructor
    public User() {}

//    contructor with parameters
    public User(Integer user_id, String username, String email, String password, Integer total_published_books, String role) {
        this.user_id = user_id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.total_published_books = total_published_books;
        this.role = role;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getTotal_published_books() {
        return total_published_books;
    }

    public void setTotal_published_books(Integer total_published_books) {
        this.total_published_books = total_published_books;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
