package com.example.springminiproject.repository;

import com.example.springminiproject.model.Category;
import com.example.springminiproject.model.User;
import com.example.springminiproject.response.dto.CategoryDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        WHERE category_id = :id AND user_id = :userId
    """, nativeQuery = true)
    void updateCategoryByCategoryId(Long id, String cateName, Long userId);

    Page<Category> findAllByUser_UserId(Long userId, Pageable pageable);

    Category findByCategoryIdAndUser_UserId(Long categoryId, Long userId);

    @Modifying
    @Transactional
    @Query(value = """
        DELETE FROM category
        WHERE category_id = :cateId AND user_id = :userId
    """, nativeQuery = true)
    void deleteCategoryByCategoryIdAndAndUser_UserId(Long cateId, Long userId);
}
