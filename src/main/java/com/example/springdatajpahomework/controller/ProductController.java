package com.example.springdatajpahomework.controller;

import com.example.springdatajpahomework.dto.ProductDTO;
import com.example.springdatajpahomework.model.Product;
import com.example.springdatajpahomework.request.ProductRequest;
import com.example.springdatajpahomework.response.ApiResponse;
import com.example.springdatajpahomework.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //   insert new product
    @PostMapping
    public ResponseEntity<ApiResponse<ProductDTO>> insertNewProduct(@RequestBody ProductRequest productRequest) {
        ProductDTO newProduct = productService.addNewProduct(productRequest);
//        newProduct.setProductName(productRequest.getProductName());
//        newProduct.setUnitPrice(productRequest.getUnitPrice());
//        newProduct.setDescription(productRequest.getDescription());

        ApiResponse<ProductDTO> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.CREATED);
        apiResponse.setMessage("A new product is inserted successfully.");
        apiResponse.setPayload(newProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    //   get all products
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getAllProducts(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "productId") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection
    ) {
        List<ProductDTO> productList = productService.getAllProducts(pageNo, pageSize, sortBy, sortDirection);
        ApiResponse<List<ProductDTO>> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("Get all products successfully.");
        apiResponse.setPayload(productList);
        return ResponseEntity.ok().body(apiResponse);
    }

    //   get product by product id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Optional<ProductDTO>>> getProductByProductId(@PathVariable Long id) {
        Optional<ProductDTO> product = productService.getProductById(id);
        ApiResponse<Optional<ProductDTO>> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("Get product with id " + id + " successfully.");
        apiResponse.setPayload(product);
        return ResponseEntity.ok().body(apiResponse);
    }

    //   delete product by id
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);

        ApiResponse<Product> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("Product with id " + id + " is deleted successfully.");
        apiResponse.setPayload(null);
        return ResponseEntity.ok().body(apiResponse);
    }

    //    update product by id
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Optional<ProductDTO>>> updatedProductById(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        productService.updateProductById(id, productRequest);

        Optional<ProductDTO> updatedProduct = productService.getProductById(id);
        ApiResponse<Optional<ProductDTO>> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("Product id " + id + " is updated successfully.");
        apiResponse.setPayload(updatedProduct);
        return ResponseEntity.ok().body(apiResponse);

    }

}
