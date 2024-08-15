package com.example.springdatajpahomework.repository;

import com.example.springdatajpahomework.model.ProductOrder;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductsRepository extends JpaRepository<ProductOrder, Long> {

    @Modifying
    @Transactional
    @Query(value = """
        INSERT INTO product_order(product_id, order_id)
        VALUES (:productId, :orderId)
    """, nativeQuery = true)
    int insertIdIntoPOTable(Integer productId, Integer orderId);

}
