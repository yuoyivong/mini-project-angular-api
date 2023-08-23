package com.example.library_management.controller;

import com.example.library_management.Exception.BlankFieldExceptionHandler;
import com.example.library_management.Exception.IfAlreadyExistValidationException;
import com.example.library_management.model.entity.Category;
import com.example.library_management.model.request.CategoryRequest;
import com.example.library_management.model.response.CategoryResponse;
import com.example.library_management.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/allCategories")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping("/category")
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest categoryRequest) {

        if(categoryRequest.getCategoryName().isBlank()) {
            throw new BlankFieldExceptionHandler("Please fill in the category name.");
        }

//        if(!categoryService.checkIfCategoryAlreadyExist(categoryRequest.getCategoryName())) {
//            throw new IfAlreadyExistValidationException("This category is already exist.");
//        }

        return ResponseEntity.ok(categoryService.createCategory(categoryRequest));

    }

}
