package com.example.library_management.repository;

import com.example.library_management.model.entity.Book;
import jakarta.persistence.NamedQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query(value = """
        SELECT b.* , c.category_name
        FROM book b
            INNER JOIN book_details bd ON b.book_id = bd.book_id
            INNER JOIN category c ON bd.category_id = c.category_id
        WHERE c.category_id = :category_id
    """, nativeQuery = true)
    List<Book> findBooksByCategoryId(@Param("category_id") Integer category_id);

}