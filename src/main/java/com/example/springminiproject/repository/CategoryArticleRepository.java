package com.example.springminiproject.repository;

import com.example.springminiproject.model.CategoryArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryArticleRepository extends JpaRepository<CategoryArticle, Long> {

}
