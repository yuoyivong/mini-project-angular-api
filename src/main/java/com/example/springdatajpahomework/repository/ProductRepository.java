package com.example.springdatajpahomework.repository;

import com.example.springdatajpahomework.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Modifying
    @Transactional
    @Query("""
        UPDATE Product p
        SET p.productName = :productName,
            p.unitPrice = :unitPrice,
            p.description = :description
        WHERE p.productId = :id
    """)
    void updateProductByProductId(Long id, String productName, Float unitPrice, String description);

//    get unit price of product
    @Query("""
        SELECT p.unitPrice
        FROM Product p
        WHERE p.productName = :productName
    """)
    Float getByUnitPrice(String productName);

}

