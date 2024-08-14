package com.example.springdatajpahomework.model;

import com.example.springdatajpahomework.dto.OrderDTO;
import com.example.springdatajpahomework.dto.ProductDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "order_tb")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "total_amount", nullable = false)
    private Float totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "orderList")
    private List<Product> productList;

    public OrderDTO orderDTOResponse() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(this.orderId);
        orderDTO.setOrderDate(this.orderDate);
        orderDTO.setQuantity(this.quantity);
        orderDTO.setStatus(this.status);
        orderDTO.setTotalAmount(this.totalAmount);

        List<ProductDTO> productDTOList = new ArrayList<>();

        for (Product p : this.productList) {
            productDTOList.add(new ProductDTO(p.getProductId(), p.getProductName(), p.getUnitPrice(), p.getDescription()));
        }

        orderDTO.setProductList(productDTOList);
        return orderDTO;
    }


}
