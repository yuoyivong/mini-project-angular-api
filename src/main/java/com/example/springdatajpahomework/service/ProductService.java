package com.example.springdatajpahomework.service;


import com.example.springdatajpahomework.dto.ProductDTO;
import com.example.springdatajpahomework.model.Product;
import com.example.springdatajpahomework.request.ProductRequest;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductDTO> getAllProducts(int pageNo, int pageSize, String sortBy, String sortDirection);

    Optional<ProductDTO> getProductById(Long id);

//    Product addNewProduct(ProductRequest productRequest);
    ProductDTO addNewProduct(ProductRequest productRequest);

    void deleteProductById(Long id);

    void updateProductById(Long id, ProductRequest productRequest);

}
