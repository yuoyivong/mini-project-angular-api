package com.example.springdatajpahomework.service;

import com.example.springdatajpahomework.model.Order;
import com.example.springdatajpahomework.model.OrderStatus;

import java.util.Map;

public interface OrderService {

    Order createOrderWithProducts(Integer customerId, Map<Integer, Integer> productQuantities);

    void updateOrderByOrderStatus(OrderStatus status, Integer cusId, Integer orderId);
}
