package com.example.springminiproject.repository;

import com.example.springminiproject.model.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Modifying
    @Transactional
    @Query(value = """
        UPDATE category 
        SET category_name = :cateName,
            updated_at = CURRENT_TIMESTAMP
        WHERE category_id = :id
    """, nativeQuery = true)
    void updateCategoryByCategoryId(Long id, String cateName);

}
