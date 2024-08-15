package com.example.springdatajpahomework.model;

import com.example.springdatajpahomework.dto.OrderDTO;
import com.example.springdatajpahomework.dto.ProductDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

//    @Column(nullable = false)
//    private Integer quantity;

    @Column(name = "total_amount")
    private Float totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;

//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "orderList")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "order")
    private List<ProductOrder> orderProduct = new ArrayList<>();

    public OrderDTO orderDTOResponse() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(this.orderId);
        orderDTO.setOrderDate(this.orderDate);
        orderDTO.setStatus(this.status);
        orderDTO.setTotalAmount(this.totalAmount);

        List<ProductDTO> productDTOList = new ArrayList<>();

        for (ProductOrder po : this.orderProduct) {
            Product p = po.getProduct();
            productDTOList.add(new ProductDTO(p.getProductId(), p.getProductName(), p.getUnitPrice(), p.getDescription()));
        }

        orderDTO.setProductList(productDTOList);
        return orderDTO;
    }


}
