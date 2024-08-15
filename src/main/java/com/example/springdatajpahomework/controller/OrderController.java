package com.example.springdatajpahomework.controller;

import com.example.springdatajpahomework.dto.CustomerDTO;
import com.example.springdatajpahomework.dto.OrderDTO;
import com.example.springdatajpahomework.model.OrderStatus;
import com.example.springdatajpahomework.request.OrderRequest;
import com.example.springdatajpahomework.response.ApiResponse;
import com.example.springdatajpahomework.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

//    create new order
    @PostMapping("/{customerId}")
    public ResponseEntity<ApiResponse<OrderDTO>> insertNewOrder(@PathVariable Integer customerId, @RequestBody List<OrderRequest> orderRequest) {
        OrderDTO orderDTO = orderService.createNewOrder(customerId, orderRequest);
        ApiResponse<OrderDTO> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.CREATED);
        apiResponse.setMessage("A new order is created successfully.");
        apiResponse.setPayload(orderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

//    get order by order id
    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderDTO>> getOrderByOrderId(@PathVariable Integer orderId) {
        OrderDTO orderDTO = orderService.getOrderByOrderId(orderId);
        ApiResponse<OrderDTO> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("Get order id " + orderId + " successfully.");
        apiResponse.setPayload(orderDTO);
        return ResponseEntity.ok().body(apiResponse);
    }

//    get a list of order by customer id
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getOrdersByCustomerId(@PathVariable Integer customerId) {
        List<OrderDTO> orderDTO = orderService.getAllOrderByCustomerId(customerId);
        ApiResponse<List<OrderDTO>> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("Get all orders with customer id " + customerId + " successfully.");
        apiResponse.setPayload(orderDTO);
        return ResponseEntity.ok().body(apiResponse);
    }

//    update order status
    @PutMapping("/status")
    public ResponseEntity<ApiResponse<OrderDTO>> updateOrderStatus(@RequestParam OrderStatus status, @RequestParam Integer cusId, @RequestParam Integer orderId) {
        orderService.updateOrderByOrderStatus(status, cusId, orderId);

        OrderDTO getOrderAfterUpdateStatus = orderService.getOrderByOrderId(orderId);
        ApiResponse<OrderDTO> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("Successfully update the status of order to " + status + ".");
        apiResponse.setPayload(getOrderAfterUpdateStatus);
        return ResponseEntity.ok().body(apiResponse);
    }

}
