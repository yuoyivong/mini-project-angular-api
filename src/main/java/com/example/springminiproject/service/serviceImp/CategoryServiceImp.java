package com.example.springminiproject.service.serviceImp;

import com.example.springminiproject.exception.AlreadyExistsException;
import com.example.springminiproject.exception.BlankFieldException;
import com.example.springminiproject.exception.NotFoundException;
import com.example.springminiproject.model.Category;
import com.example.springminiproject.model.User;
import com.example.springminiproject.repository.CategoryArticleRepository;
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
        checkCategoryName(categoryRequest.getCategoryName(), user.getUserId());

        try {
            User u = new User();
            u.setUserId(user.getUserId());
            u.setUsername(user.getUsername());
            u.setEmail(user.getEmail());
            u.setAddress(user.getAddress());
            u.setPhoneNumber(user.getPhoneNumber());
            u.setCreatedAt(user.getCreatedAt());
            u.setUpdatedAt(user.getUpdatedAt());
            u.setRole(user.getRole());

            Category category = new Category();
            category.setCategoryName(categoryRequest.getCategoryName());
            category.setCreatedAt(LocalDateTime.now());
            category.setUser(u);
//        category.setAmountOfArticles(categoryArticleRepository.countArticleByCategoryId(category.getCategoryId()));

            return categoryRepository.save(category).categoryDTOResponse();

        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (AlreadyExistsException e) {
            throw new AlreadyExistsException(e.getMessage());
        }

    }

    @Override
    public List<CategoryDTO> getAllCategories(Long userId, int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        categoryRepository.updateAmountOfArticlesField();

        Page<Category> categoryPage = categoryRepository.findAllByUser_UserId(userId, pageable);

        return categoryPage.stream().map(Category::categoryDTOResponse).collect(Collectors.toList());

    }

    @Override
    public CategoryDTO getCategoryByCategoryId(Long id, Long userId) {
        return categoryRepository.findByCategoryIdAndUser_UserId(id, userId).map(Category::categoryDTOResponse)
                .orElseThrow(() -> new NotFoundException("Category id " + id + " not found."));
    }

    @Override
    public void deleteCategoryByCategoryId(Long id, Long userId) {
        checkExistCategoryId(id, userId);

        try {
            categoryRepository.deleteCategoryByCategoryIdAndAndUser_UserId(id, userId);
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    @Override
    public void updateCategoryByCategoryId(Long id, CategoryRequest categoryRequest, Long userId) {
        checkExistCategoryId(id, userId);
        checkCategoryName(categoryRequest.getCategoryName(), userId);

        try {
            categoryRepository.updateCategoryByCategoryId(id, categoryRequest.getCategoryName(), userId);
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    private void checkCategoryName(String categoryName, Long userId) {
        if(categoryName.isBlank()) {
            throw new BlankFieldException("Category name is required.");
        }

        if(categoryRepository.findCategoryByCategoryNameAndUser_UserId(categoryName, userId).isPresent()) {
            throw new AlreadyExistsException("Category already exists.");
        }

    }

    private void checkExistCategoryId(Long id, Long userId) {
        if(categoryRepository.findByCategoryIdAndUser_UserId(id, userId).isEmpty()) {
            throw new NotFoundException("Category id " + id + " not found.");
        }
    }

}
