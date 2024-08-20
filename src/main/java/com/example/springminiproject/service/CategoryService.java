package com.example.springminiproject.service;

import com.example.springminiproject.request.CategoryRequest;
import com.example.springminiproject.response.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {

    CategoryDTO insertNewCategory(CategoryRequest categoryRequest);

    List<CategoryDTO> getAllCategories(int pageNo, int pageSize, String sortBy, String sortDirection);

    CategoryDTO getCategoryByCategoryId(Long id);

    void deleteCategoryByCategoryId(Long id);

    void updateCategoryByCategoryId(Long id, CategoryRequest categoryRequest);

}
