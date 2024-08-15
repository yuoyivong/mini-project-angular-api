package com.example.springdatajpahomework.service;

import com.example.springdatajpahomework.dto.CreateCustomerDTO;
import com.example.springdatajpahomework.dto.CustomerDTO;
import com.example.springdatajpahomework.request.CustomerRequest;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

//    List<Customer> getAllCustomers();
//    List<CustomerDTO> getCustomerList();

    List<CustomerDTO> getCustomerList(int pageNo, int pageSize, String sortBy, String sortDirection);

//    Optional<Customer> getCustomerById(Long id);
    Optional<CustomerDTO> getCustomerById(Long id);

    CreateCustomerDTO addNewCustomer(CustomerRequest customerRequest);

    void deleteCustomerById(Long id);

    void updateCustomerById(Long id, CustomerRequest customerRequest);

}
