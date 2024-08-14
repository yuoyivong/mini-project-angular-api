package com.example.springdatajpahomework.service;

import com.example.springdatajpahomework.model.Order;
import com.example.springdatajpahomework.request.OrderRequest;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<Order> getAllOrderList();

    Optional<Order> getOrderByOrderId(Long id);

//    Order createNewOrder(OrderRequest orderRequest);

    void deleteOrderById(Long id);

    void updateOrderByOrderId(Long id, OrderRequest orderRequest);
}
