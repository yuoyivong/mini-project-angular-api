package com.example.library_management.repository;

import com.example.library_management.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.*;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findCategoryByCategoryName(String categoryName);

    List<Category> findCategoryByCategoryId(Integer categoryId);

    List<Category> findCategoriesByBookId(Integer bookId);

}