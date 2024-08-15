package com.example.springdatajpahomework.service.serviceImp;

import com.example.springdatajpahomework.model.*;
import com.example.springdatajpahomework.repository.CustomerRepository;
import com.example.springdatajpahomework.repository.OrderProductsRepository;
import com.example.springdatajpahomework.repository.OrderRepository;
import com.example.springdatajpahomework.repository.ProductRepository;
import com.example.springdatajpahomework.service.OrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImp implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderProductsRepository orderProductsRepository;

    public OrderServiceImp(OrderRepository orderRepository, CustomerRepository customerRepository, ProductRepository productRepository, OrderProductsRepository orderProductsRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.orderProductsRepository = orderProductsRepository;
    }

    @Override
    public Order createOrderWithProducts(Integer customerId, Map<Integer, Integer> productQuantities) {
        Customer customer = customerRepository.findById(Long.valueOf(customerId)).orElseThrow(() -> new RuntimeException("Customer not found"));

        Order order = new Order();
        order.setOrderDate(LocalDate.now());
        order.setCustomer(customer);
        order.setStatus(OrderStatus.PENDING);

        Set<Long> productIdsAsLong = productQuantities.keySet().stream().map(Long::valueOf).collect(Collectors.toSet());
        Set<Product> productSet = new HashSet<>(productRepository.findAllById(productIdsAsLong));

        float totalAmount = 20.0f;
        for (Product p : productSet) {
            Integer qty = productQuantities.get(p.getProductId());

            ProductOrder productOrder = new ProductOrder();
            productOrder.setProduct(p);
            productOrder.setOrder(order);
            productOrder.setQuantity(qty);

            orderProductsRepository.save(productOrder);
            order.getOrderProduct().add(productOrder);
//            orderProductsRepository.insertIdIntoPOTable(p.getProductId(), order.getOrderId());
            totalAmount += p.getUnitPrice() * qty;
        }

        order.setTotalAmount(totalAmount);
        orderRepository.save(order);

        return order;

    }

    @Override
    public void updateOrderByOrderStatus(OrderStatus status, Integer cusId, Integer orderId) {
        orderRepository.updateOrderByStatusAndOrderId(String.valueOf(status), cusId, orderId);
    }


}
