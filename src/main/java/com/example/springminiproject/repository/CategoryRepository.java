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

import java.util.Optional;

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

//    update amount_of_articles field in category table based on cateId
    @Modifying
    @Transactional
    @Query(value = """
        UPDATE category
        SET amount_of_articles = subquery.article_count
        FROM (
                 SELECT c.category_id, COUNT(ac.article_id) AS article_count
                 FROM category c
                          LEFT JOIN category_article ac
                          ON c.category_id = ac.category_id
                 GROUP BY c.category_id
             ) AS subquery
        WHERE category.category_id = subquery.category_id;
    """, nativeQuery = true)
    void updateAmountOfArticlesField();


    Optional<Category> findByCategoryIdAndUser_UserId(Long categoryId, Long userId);

    @Modifying
    @Transactional
    @Query(value = """
        DELETE FROM category
        WHERE category_id = :cateId AND user_id = :userId
    """, nativeQuery = true)
    void deleteCategoryByCategoryIdAndAndUser_UserId(Long cateId, Long userId);

    Optional<Category> findCategoryByCategoryName(String categoryName);

}
