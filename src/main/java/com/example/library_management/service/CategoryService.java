package com.example.library_management.service;

import com.example.library_management.model.entity.Category;
import com.example.library_management.model.request.CategoryRequest;
import com.example.library_management.model.response.CategoryResponse;
import com.example.library_management.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
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

//    get category by id
    public CategoryResponse getCategoryById(Integer categoryId) {
        categoryRepository.findById(categoryId);
        return CategoryResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Category is added successfully.")

                .build();
    }

//    add new category

    public CategoryResponse createCategory(CategoryRequest categoryRequest) {

        var category = Category.builder()
                .categoryName(categoryRequest.getCategoryName())
                .build();

        categoryRepository.save(category);

        return CategoryResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Category is added successfully.")
                .categoryName(category.getCategoryName())
                .build();
    }

//    update category by id
    public CategoryResponse updateCategory(Integer category_id, CategoryRequest categoryRequest) throws ChangeSetPersister.NotFoundException {
        Category existingCategory = categoryRepository.findById(category_id).orElseThrow(ChangeSetPersister.NotFoundException::new);

        existingCategory.setCategoryName(categoryRequest.getCategoryName());
        categoryRepository.save(existingCategory);

        return CategoryResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Category is updated successfully.")
                .categoryName(categoryRequest.getCategoryName())
                .build();
    }

//    delete category by id
    public CategoryResponse deleteCategoryById(Integer category_id) {
        categoryRepository.deleteById(category_id);

        return CategoryResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Category is deleted successfully.")
                .categoryName("Not existed anymore!")
                .build();
    }


    public boolean isCategoryNameExist(String categoryName) {
        return categoryRepository.findCategoryByCategoryName(categoryName).isPresent();
    }

    public boolean isCategoryIdExist(Integer category_id) {
        return categoryRepository.findById(category_id).isPresent();
    }

}