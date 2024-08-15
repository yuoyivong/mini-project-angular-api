package com.example.springdatajpahomework.service.serviceImp;

import com.example.springdatajpahomework.dto.CustomerDTO;
import com.example.springdatajpahomework.model.Customer;
import com.example.springdatajpahomework.model.Email;
import com.example.springdatajpahomework.model.Order;
import com.example.springdatajpahomework.model.OrderStatus;
import com.example.springdatajpahomework.repository.CustomerRepository;
import com.example.springdatajpahomework.repository.OrderRepository;
import com.example.springdatajpahomework.repository.ProductRepository;
import com.example.springdatajpahomework.request.CustomerRequest;
import com.example.springdatajpahomework.service.CustomerService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImp implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public CustomerServiceImp(CustomerRepository customerRepository, ProductRepository productRepository, OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

//    @Override
//    public List<Customer> getAllCustomers() {
//        return customerRepository.findAll();
//    }

    @Override
    public List<CustomerDTO> getCustomerList() {
        return customerRepository.findAll().stream().map(Customer::customerResponse).collect(Collectors.toList());
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(Long id) {
        return customerRepository.findById(id).map(Customer::customerResponse);
    }

    @Override
    public Customer addNewCustomer(CustomerRequest customerRequest) {
//        insert email to email entity
        Email email = new Email();
        email.setEmail(customerRequest.getEmail());

//        Float unitPrice = productRepository.getByUnitPrice(customerRequest.getOrderList().getFirst().getProductName());
        Float unitPrice = productRepository.getByUnitPrice(customerRequest.getOrderList().getFirst().getProductName());

        Integer productId = productRepository.getByProductId(customerRequest.getOrderList().getFirst().getProductName());
//        insert info to customer entity
        Customer newCustomer = new Customer();
        newCustomer.setName(customerRequest.getCustomerName());
        newCustomer.setEmail(email);
        newCustomer.setAddress(customerRequest.getAddress());
        newCustomer.setPhoneNumber(customerRequest.getPhoneNumber());

        Order order = new Order();
        Integer qty = customerRequest.getOrderList().getFirst().getQuantity();

        order.setOrderDate(LocalDate.now());
        order.setCustomer(newCustomer);
        order.setQuantity(qty);
        order.setTotalAmount(qty * unitPrice);
        order.setStatus(OrderStatus.valueOf("PENDING"));
        List<Order> orderList = new ArrayList<>();
        newCustomer.setOrderList(orderList);
        orderRepository.save(order);
        orderRepository.insertIdIntoPOTable()

        return customerRepository.save(newCustomer);
    }

    @Override
    public void deleteCustomerById(Long id) {
        if(customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
        } else {
            System.out.println("No content");
        }
    }

    @Override
    public void updateCustomerById(Long id, CustomerRequest customerRequest) {
//        Email updatedEmail = new Email();
//        updatedEmail.setEmail(customerRequest.getEmail());

//        Customer updatedCustomer = new Customer();
//        updatedCustomer.setName(customerRequest.getCustomerName());
        String customerName = customerRequest.getCustomerName();
        String address = customerRequest.getAddress();
        String phoneNumber = customerRequest.getPhoneNumber();
        String email = customerRequest.getEmail();

        customerRepository.updateCustomerByCustomerId(id, customerName, address, phoneNumber, email);

    }
}
