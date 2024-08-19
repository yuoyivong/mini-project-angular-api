package com.example.springminiproject.service.serviceImp;

import com.example.springminiproject.repository.CategoryRepository;
import com.example.springminiproject.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImp implements CategoryService {


    private final CategoryRepository categoryRepository;

    public CategoryServiceImp(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
}
