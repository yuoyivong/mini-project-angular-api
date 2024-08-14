package com.example.springdatajpahomework.service.serviceImp;

import com.example.springdatajpahomework.model.Order;
import com.example.springdatajpahomework.model.OrderStatus;
import com.example.springdatajpahomework.repository.OrderRepository;
import com.example.springdatajpahomework.request.OrderRequest;
import com.example.springdatajpahomework.service.OrderService;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImp implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImp(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> getAllOrderList() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> getOrderByOrderId(Long id) {
        return orderRepository.findById(id);
    }

//    @Override
//    public Order createNewOrder(OrderRequest orderRequest) {
//        Order order = new Order();
//        order.setQuantity(orderRequest.getQuantity());
//        order.setOrderDate(LocalDate.now());
//        order.setStatus(OrderStatus.valueOf("PENDING"));
//
//        return orderRepository.save(order);
//    }

    @Override
    public void deleteOrderById(Long id) {

    }

    @Override
    public void updateOrderByOrderId(Long id, OrderRequest orderRequest) {

    }

}
