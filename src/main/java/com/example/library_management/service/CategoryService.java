package com.example.library_management.service;

import com.example.library_management.model.entity.Category;
import com.example.library_management.model.request.CategoryRequest;
import com.example.library_management.model.response.CategoryResponse;
import com.example.library_management.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

//    public CategoryResponse getCategoryByName(String categoryName) {
//
//        categoryRepository.findCategoryByCategory_name(categoryName);
//
//        return CategoryResponse.builder()
//                .status(HttpStatus.OK.value())
//                .category_name(categoryName)
//                .build();
//    }

    public CategoryResponse createCategory(CategoryRequest categoryRequest) {

        var category = Category.builder()
                .categoryName(categoryRequest.getCategoryName())
                .build();

        categoryRepository.save(category);
        return CategoryResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Category added successfully.")
                .categoryName(category.getCategoryName())
                .build();
    }

//    public CategoryResponse updateCategory()

    public boolean checkIfCategoryAlreadyExist(String categoryName) {
        return categoryRepository.findCategoryByCategoryName(categoryName).isPresent();
    }
}
