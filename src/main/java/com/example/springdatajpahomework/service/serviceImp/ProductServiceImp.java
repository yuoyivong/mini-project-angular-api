package com.example.springdatajpahomework.service.serviceImp;

import com.example.springdatajpahomework.dto.ProductDTO;
import com.example.springdatajpahomework.model.Customer;
import com.example.springdatajpahomework.model.Product;
import com.example.springdatajpahomework.repository.ProductRepository;
import com.example.springdatajpahomework.request.ProductRequest;
import com.example.springdatajpahomework.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepository;
    public ProductServiceImp(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDTO> getAllProducts(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Product> productPage = productRepository.findAll(pageable);

        return productPage.stream().map(Product::productDTOResponse).collect(Collectors.toList());

    }

    @Override
    public Optional<ProductDTO> getProductById(Long id) {
        return productRepository.findById(id).map(Product::productDTOResponse);
    }

    @Override
    public ProductDTO addNewProduct(ProductRequest productRequest) {
        Product newProduct = new Product();
        newProduct.setProductName(productRequest.getProductName());
        newProduct.setUnitPrice(productRequest.getUnitPrice());
        newProduct.setDescription(productRequest.getDescription());
        return productRepository.save(newProduct).productDTOResponse();
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
