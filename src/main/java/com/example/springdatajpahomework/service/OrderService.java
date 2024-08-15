package com.example.springdatajpahomework.service;

import com.example.springdatajpahomework.dto.OrderDTO;
import com.example.springdatajpahomework.model.Order;
import com.example.springdatajpahomework.model.OrderStatus;
import com.example.springdatajpahomework.request.OrderRequest;

import java.util.List;
import java.util.Map;

public interface OrderService {

    Order createOrderWithProducts(Integer customerId, Map<Integer, Integer> productQuantities);

    void updateOrderByOrderStatus(OrderStatus status, Integer cusId, Integer orderId);

    OrderDTO createNewOrder(Integer cusId, List<OrderRequest> orderRequest);

    List<OrderDTO> getAllOrderByCustomerId(Integer cusId);

    OrderDTO getOrderByOrderId(Integer orderId);

}
