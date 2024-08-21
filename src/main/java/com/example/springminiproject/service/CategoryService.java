package com.example.springminiproject.service;

import com.example.springminiproject.model.User;
import com.example.springminiproject.request.CategoryRequest;
import com.example.springminiproject.response.dto.CategoryDTO;
import com.example.springminiproject.response.dto.UserDTO;

import java.util.List;

public interface CategoryService {

    CategoryDTO insertNewCategory(UserDTO user, CategoryRequest categoryRequest);

    List<CategoryDTO> getAllCategories(Long userId, int pageNo, int pageSize, String sortBy, String sortDirection);

    CategoryDTO getCategoryByCategoryId(Long userId, Long id);

    void deleteCategoryByCategoryId(Long userId, Long id);

    void updateCategoryByCategoryId(Long id, CategoryRequest categoryRequest, Long userId);

}
