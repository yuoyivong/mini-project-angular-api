package com.example.springminiproject.controller;

import com.example.springminiproject.model.Category;
import com.example.springminiproject.model.User;
import com.example.springminiproject.request.CategoryRequest;
import com.example.springminiproject.request.UserRequest;
import com.example.springminiproject.response.ApiResponse;
import com.example.springminiproject.response.dto.CategoryDTO;
import com.example.springminiproject.response.dto.UserDTO;
import com.example.springminiproject.service.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/author/category")
@SecurityRequirement(name = "bearerAuth")
public class CategoryController {

    private final CategoryService categoryService;


    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryDTO>> createNewCategory(@RequestBody CategoryRequest categoryRequest) {
        ApiResponse<CategoryDTO> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.CREATED);
        apiResponse.setMessage("A new category is created successfully.");
        apiResponse.setPayload(categoryService.insertNewCategory(categoryRequest));

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);

    }

    //    get all users
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<CategoryDTO>>> getAllCategories(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "categoryId") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {

        List<CategoryDTO> categoryDTOList = categoryService.getAllCategories(pageNo, pageSize, sortBy, sortDirection);
        ApiResponse<List<CategoryDTO>> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("Get all categories successfully.");
        apiResponse.setPayload(categoryDTOList);
        return ResponseEntity.ok().body(apiResponse);
    }

    //    get user by id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryDTO>> getCategoryByCateId(@PathVariable Long id) {
        ApiResponse<CategoryDTO> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("Get category with id " + id + " successfully.");
        apiResponse.setPayload(categoryService.getCategoryByCategoryId(id));
        return ResponseEntity.ok().body(apiResponse);
    }

    //   delete product by id
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> deleteCategoryById(@PathVariable Long id) {
        categoryService.deleteCategoryByCategoryId(id);

        ApiResponse<Category> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("Category with id " + id + " is deleted successfully.");
//        apiResponse.setPayload(null);
        return ResponseEntity.ok().body(apiResponse);

    }

    //    update user by id
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryDTO>> updateCategoryById(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest) {
        categoryService.updateCategoryByCategoryId(id, categoryRequest);
        ApiResponse<CategoryDTO> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("Category id " + id + " is updated successfully.");
        apiResponse.setPayload(categoryService.getCategoryByCategoryId(id));

        return ResponseEntity.ok().body(apiResponse);
    }

}

