package com.example.library_management.repository;

import com.example.library_management.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findBooksByCategoryId(Integer categoryId);

}