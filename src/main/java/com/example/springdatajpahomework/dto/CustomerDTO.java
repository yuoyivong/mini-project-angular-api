package com.example.springdatajpahomework.dto;

import com.example.springdatajpahomework.model.Customer;
import com.example.springdatajpahomework.model.Email;
import com.example.springdatajpahomework.model.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    private Integer id;
    private String name;
    private String address;
    private String phoneNumber;
    private Email email;
    private List<OrderDTO> orderList;

}
