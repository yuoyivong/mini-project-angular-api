package com.example.library_management.controller;

import com.example.library_management.Exception.BlankFieldExceptionHandler;
import com.example.library_management.Exception.IfAlreadyExistValidationException;
import com.example.library_management.Exception.NotFoundException;
import com.example.library_management.model.entity.Book;
import com.example.library_management.model.entity.Category;
import com.example.library_management.model.request.CategoryRequest;
import com.example.library_management.model.response.CategoryResponse;
import com.example.library_management.service.BookService;
import com.example.library_management.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CategoryController {

    private final CategoryService categoryService;

//    get all categories
    @GetMapping("/allCategories")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

//    get category by id
    @GetMapping("/category/{category_id}")
    public ResponseEntity<List<Category>> getCategoryById(@PathVariable Integer category_id) {

        if(!categoryService.isCategoryIdExist(category_id)) {
            throw new NotFoundException("This category id is not found.");
        }

        return ResponseEntity.ok(categoryService.getCategoryById(category_id));
    }

//    add new category
    @PostMapping("/category")
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest categoryRequest) {

        if(categoryRequest.getCategoryName().isBlank()) {
            throw new BlankFieldExceptionHandler("Please fill in the category name.");
        }

       if(categoryService.isCategoryNameExist(categoryRequest.getCategoryName())) {
           throw new IfAlreadyExistValidationException("This category already existed.");
       }

        return ResponseEntity.ok(categoryService.createCategory(categoryRequest));

    }

//    update category by id
    @PutMapping("/category/{category_id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Integer category_id,
                                                           @RequestBody CategoryRequest categoryRequest) throws ChangeSetPersister.NotFoundException {

        if(categoryRequest.getCategoryName().isBlank()) {
            throw new BlankFieldExceptionHandler("Please fill in the category name.");
        }

        if(!categoryService.isCategoryIdExist(category_id)) {
            throw new IfAlreadyExistValidationException("Category id doesn't exist yet.");
        }

        if(categoryService.isCategoryIdExist(category_id) && categoryService.isCategoryNameExist(categoryRequest.getCategoryName())) {
            throw new IfAlreadyExistValidationException("It already existed.");
        }

        return ResponseEntity.ok(categoryService.updateCategory(category_id, categoryRequest));
    }

//    delete category by id
    @DeleteMapping("/category/{category_id}")
    public ResponseEntity<CategoryResponse> deleteCategoryById(@PathVariable Integer category_id) {

        if(!categoryService.isCategoryIdExist(category_id)) {
            throw new IfAlreadyExistValidationException("Cannot delete not existed category id.");
        }

        return ResponseEntity.ok(categoryService.deleteCategoryById(category_id));
    }
}