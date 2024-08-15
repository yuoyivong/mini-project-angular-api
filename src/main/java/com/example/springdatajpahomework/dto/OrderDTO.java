package com.example.springdatajpahomework.dto;

import com.example.springdatajpahomework.model.Order;
import com.example.springdatajpahomework.model.OrderStatus;
import com.example.springdatajpahomework.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Integer id;
    private LocalDate orderDate;
    private Float totalAmount;
    private OrderStatus status;
    private List<ProductDTO> productList;

}
