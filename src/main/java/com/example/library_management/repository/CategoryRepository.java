package com.example.library_management.repository;

import com.example.library_management.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.*;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

//    Optional<Category> findCategoryByCategory_name(String category_name);
//    Optional<Category> findCategoryByCategory_id(Integer category_id);
    Optional<Category> findCategoryByCategoryName(String categoryName);
}
