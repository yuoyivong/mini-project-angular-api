package com.example.springdatajpahomework.controller;

import com.example.springdatajpahomework.dto.CreateCustomerDTO;
import com.example.springdatajpahomework.dto.CustomerDTO;
import com.example.springdatajpahomework.model.Customer;
import com.example.springdatajpahomework.model.OrderStatus;
import com.example.springdatajpahomework.request.CustomerRequest;
import com.example.springdatajpahomework.response.ApiResponse;
import com.example.springdatajpahomework.service.CustomerService;
import com.example.springdatajpahomework.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;
    private final OrderService orderService;

    public CustomerController(CustomerService customerService, OrderService orderService) {
        this.customerService = customerService;
        this.orderService = orderService;
    }

    //    add new customer
    @PostMapping
    public ResponseEntity<ApiResponse<CreateCustomerDTO>> insertNewCustomer(@RequestBody CustomerRequest customerRequest) {
        CreateCustomerDTO newCustomer = customerService.addNewCustomer(customerRequest);
        ApiResponse<CreateCustomerDTO> customerApiResponse = new ApiResponse<>();
        customerApiResponse.setStatus(HttpStatus.CREATED);
        customerApiResponse.setMessage("A new customer is inserted successfully.");
        customerApiResponse.setPayload(newCustomer);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerApiResponse);
    }

    //    get all customers
//    @GetMapping("/all")
//    public ResponseEntity<ApiResponse<List<Customer>>> getAllCustomers() {
//        List<Customer> customerList = customerService.getAllCustomers();
//        ApiResponse<List<Customer>> customerApiResponse = new ApiResponse<>();
//        customerApiResponse.setStatus(HttpStatus.OK);
//        customerApiResponse.setMessage("Get all customers successfully.");
//        customerApiResponse.setPayload(customerList);
//        return ResponseEntity.ok().body(customerApiResponse);
//    }
//
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<CustomerDTO>>> getAllCustomers(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection
    ) {
        List<CustomerDTO> customerList = customerService.getCustomerList(pageNo, pageSize, sortBy, sortDirection);

        ApiResponse<List<CustomerDTO>> customerApiResponse = new ApiResponse<>();
        customerApiResponse.setStatus(HttpStatus.OK);
        customerApiResponse.setMessage("Get all customers successfully.");
        customerApiResponse.setPayload(customerList);
        return ResponseEntity.ok().body(customerApiResponse);
    }

    //    get customer by id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Optional<CustomerDTO>>> getCustomerById(@PathVariable Long id) {
//        Optional<Customer> customer = customerService.getCustomerById(id);
        Optional<CustomerDTO> customer = customerService.getCustomerById(id);
        ApiResponse<Optional<CustomerDTO>> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("Get customer id " + id + " successfully.");
        apiResponse.setPayload(customer);
        return ResponseEntity.ok().body(apiResponse);
    }

    //    delete customer by id
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Customer>> deleteCustomerById(@PathVariable Long id) {
        customerService.deleteCustomerById(id);
        ApiResponse<Customer> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("Customer id " + id + " is deleted successfully.");
        apiResponse.setPayload(null);
        return ResponseEntity.ok().body(apiResponse);
    }

    //  update customer by id
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Optional<CustomerDTO>>> updateCustomerByCustomerId(@PathVariable Long id, @RequestBody CustomerRequest customerRequest) {
        customerService.updateCustomerById(id, customerRequest);

        Optional<CustomerDTO> updatedCustomer = customerService.getCustomerById(id);
        ApiResponse<Optional<CustomerDTO>> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("Customer id " + id + " is updated successfully.");
        apiResponse.setPayload(updatedCustomer);
        return ResponseEntity.ok().body(apiResponse);
    }

}
