package com.example.springdatajpahomework.service.serviceImp;

import com.example.springdatajpahomework.model.Product;
import com.example.springdatajpahomework.repository.ProductRepository;
import com.example.springdatajpahomework.request.ProductRequest;
import com.example.springdatajpahomework.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImp(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product addNewProduct(ProductRequest productRequest) {
        Product newProduct = new Product();
        newProduct.setProductName(productRequest.getProductName());
        newProduct.setUnitPrice(productRequest.getUnitPrice());
        newProduct.setDescription(productRequest.getDescription());
        return productRepository.save(newProduct);
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void updateProductById(Long id, ProductRequest productRequest) {
        String productName = productRequest.getProductName();
        Float unitPrice = productRequest.getUnitPrice();
        String description = productRequest.getDescription();

        productRepository.updateProductByProductId(id, productName, unitPrice, description);
    }
}
