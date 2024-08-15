package com.example.springdatajpahomework.dto;

import com.example.springdatajpahomework.model.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCustomerDTO {
    private Integer id;
    private String name;
    private String address;
    private String phoneNumber;
    private Email email;
}
