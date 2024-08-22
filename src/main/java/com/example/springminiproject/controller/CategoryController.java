package com.example.springminiproject.controller;

import com.example.springminiproject.config.GlobalCurrentUserConfig;
import com.example.springminiproject.model.Category;
import com.example.springminiproject.model.User;
import com.example.springminiproject.request.CategoryRequest;
import com.example.springminiproject.response.ApiResponse;
import com.example.springminiproject.response.dto.CategoryDTO;
import com.example.springminiproject.response.dto.UserDTO;
import com.example.springminiproject.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
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
    private final GlobalCurrentUserConfig currentUserConfig;

    public CategoryController(CategoryService categoryService, GlobalCurrentUserConfig currentUserConfig) {
        this.categoryService = categoryService;
        this.currentUserConfig = currentUserConfig;
    }

    @PostMapping
    @Operation(summary = "Create a new category")
    public ResponseEntity<ApiResponse<CategoryDTO>> createNewCategory(@RequestBody CategoryRequest categoryRequest) {
        UserDTO user = currentUserConfig.getCurrentUserInformation();
        ApiResponse<CategoryDTO> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.CREATED);
        apiResponse.setMessage("A new category is created successfully.");
        apiResponse.setPayload(categoryService.insertNewCategory(user, categoryRequest));

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);

    }

    //    get all users
    @GetMapping("/all")
    @Operation(summary = "Get all categories")
    public ResponseEntity<ApiResponse<List<CategoryDTO>>> getAllCategories(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "categoryId") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {

        Long userId = currentUserConfig.getCurrentUserInformation().getUserId();
        List<CategoryDTO> categoryDTOList = categoryService.getAllCategories(userId, pageNo, pageSize, sortBy, sortDirection);
        ApiResponse<List<CategoryDTO>> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("Get all categories successfully.");
        apiResponse.setPayload(categoryDTOList);
        return ResponseEntity.ok().body(apiResponse);
    }

    //    get user by id
    @GetMapping("/{id}")
    @Operation(summary = "Get category by its id")
    public ResponseEntity<ApiResponse<CategoryDTO>> getCategoryByCateId(@PathVariable Long id) {
        Long userId = currentUserConfig.getCurrentUserInformation().getUserId();
        ApiResponse<CategoryDTO> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("Get category with id " + id + " successfully.");
        apiResponse.setPayload(categoryService.getCategoryByCategoryId(id, userId));
        return ResponseEntity.ok().body(apiResponse);
    }

    //   delete product by id
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete category by its id")
    public ResponseEntity<ApiResponse<Category>> deleteCategoryById(@PathVariable Long id) {
        Long userId = currentUserConfig.getCurrentUserInformation().getUserId();
        categoryService.deleteCategoryByCategoryId(id, userId);

        ApiResponse<Category> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("Category with id " + id + " is deleted successfully.");
//        apiResponse.setPayload(null);
        return ResponseEntity.ok().body(apiResponse);

    }

    //    update user by id
    @PutMapping("/{id}")
    @Operation(summary = "Update category by id")
    public ResponseEntity<ApiResponse<CategoryDTO>> updateCategoryById(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest) {
        Long userId = currentUserConfig.getCurrentUserInformation().getUserId();
        categoryService.updateCategoryByCategoryId(id, categoryRequest, userId);
        ApiResponse<CategoryDTO> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("Category id " + id + " is updated successfully.");
        apiResponse.setPayload(categoryService.getCategoryByCategoryId(userId, id));

        return ResponseEntity.ok().body(apiResponse);
    }

}

