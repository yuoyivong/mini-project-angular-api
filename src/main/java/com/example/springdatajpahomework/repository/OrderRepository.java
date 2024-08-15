package com.example.springdatajpahomework.repository;

import com.example.springdatajpahomework.model.Order;
import com.example.springdatajpahomework.model.OrderStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomer_CustomerId(Integer id);

    @Modifying
    @Transactional
    @Query(value = """
        UPDATE order_tb
        SET status = :status
        WHERE customer_id = :cusId AND order_id = :orderId;
    """, nativeQuery = true)
    void updateOrderByStatusAndOrderId(String status, Integer cusId, Integer orderId);

}
