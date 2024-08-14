package com.example.springdatajpahomework.model;

import com.example.springdatajpahomework.dto.CustomerDTO;
import com.example.springdatajpahomework.dto.OrderDTO;
import com.example.springdatajpahomework.dto.ProductDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "customer")
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "customer_name", nullable = false)
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private Email email;

    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orderList;

    public CustomerDTO customerResponse() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(this.customerId);
        customerDTO.setName(this.name);
        customerDTO.setEmail(this.email);
        customerDTO.setAddress(this.address);
        customerDTO.setPhoneNumber(this.phoneNumber);

        List<OrderDTO> orderDTOList = new ArrayList<>();

        for (Order o : this.orderList) {
            orderDTOList.add(new OrderDTO(o.getOrderId(), o.getOrderDate(), o.getQuantity(), o.getTotalAmount(), o.getStatus(), o.orderDTOResponse().getProductList()));
        }

        customerDTO.setOrderList(orderDTOList);

        return customerDTO;
    }

}
