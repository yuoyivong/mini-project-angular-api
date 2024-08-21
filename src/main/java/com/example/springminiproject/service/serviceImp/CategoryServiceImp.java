package com.example.springminiproject.service.serviceImp;

import com.example.springminiproject.model.Category;
import com.example.springminiproject.model.User;
import com.example.springminiproject.repository.CategoryRepository;
import com.example.springminiproject.request.CategoryRequest;
import com.example.springminiproject.response.dto.CategoryDTO;
import com.example.springminiproject.response.dto.UserDTO;
import com.example.springminiproject.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImp implements CategoryService {


    private final CategoryRepository categoryRepository;

    public CategoryServiceImp(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDTO insertNewCategory(UserDTO user, CategoryRequest categoryRequest) {
        User u = new User();
        u.setUserId(user.getUserId());
        Category category = new Category();
        category.setCategoryName(categoryRequest.getCategoryName());
        category.setCreatedAt(LocalDateTime.now());
        category.setUser(u);

        return categoryRepository.save(category).categoryDTOResponse();

    }

    @Override
    public List<CategoryDTO> getAllCategories(Long userId, int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Category> categoryPage = categoryRepository.findAllByUser_UserId(userId, pageable);

        return categoryPage.stream().map(Category::categoryDTOResponse).collect(Collectors.toList());

    }

    @Override
    public CategoryDTO getCategoryByCategoryId(Long userId, Long id) {
        return categoryRepository.findByCategoryIdAndUser_UserId(id, userId).categoryDTOResponse();
    }

    @Override
    public void deleteCategoryByCategoryId(Long id, Long userId) {
        categoryRepository.deleteCategoryByCategoryIdAndAndUser_UserId(id, userId);
    }

    @Override
    public void updateCategoryByCategoryId(Long id, CategoryRequest categoryRequest, Long userId) {
        categoryRepository.updateCategoryByCategoryId(id, categoryRequest.getCategoryName(), userId);
    }
}
