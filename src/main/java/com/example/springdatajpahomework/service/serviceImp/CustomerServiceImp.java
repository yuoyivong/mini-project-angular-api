package com.example.springdatajpahomework.service.serviceImp;

import com.example.springdatajpahomework.dto.CustomerDTO;
import com.example.springdatajpahomework.model.*;
import com.example.springdatajpahomework.repository.CustomerRepository;
import com.example.springdatajpahomework.repository.OrderRepository;
import com.example.springdatajpahomework.repository.ProductRepository;
import com.example.springdatajpahomework.request.CustomerRequest;
import com.example.springdatajpahomework.request.OrderRequest;
import com.example.springdatajpahomework.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Optional;

@Service
public class CustomerServiceImp implements CustomerService {

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    private final OrderServiceImp orderServiceImp;

    public CustomerServiceImp(CustomerRepository customerRepository, ProductRepository productRepository, OrderRepository orderRepository, OrderServiceImp orderServiceImp) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.orderServiceImp = orderServiceImp;
    }

//    @Override
//    public List<Customer> getAllCustomers() {
//        return customerRepository.findAll();
//    }

//    @Override
//    public List<CustomerDTO> getCustomerList() {
//
//        return customerRepository.findAll().stream().map(Customer::customerResponse).collect(Collectors.toList());
//    }
//
    @Override
    public List<CustomerDTO> getCustomerList(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Customer> customerPage = customerRepository.findAll(pageable);

        return customerPage.stream().map(Customer::customerResponse).toList();

    }

    @Override
    public Optional<CustomerDTO> getCustomerById(Long id) {
        return customerRepository.findById(id).map(Customer::customerResponse);
    }

    @Override
    public CustomerDTO addNewCustomer(CustomerRequest customerRequest) {
        Email email = insertEmail(customerRequest.getEmail());

        //        insert info to customer entity
        Customer newCustomer = new Customer();
        newCustomer.setName(customerRequest.getCustomerName());
        newCustomer.setEmail(email);
        newCustomer.setAddress(customerRequest.getAddress());
        newCustomer.setPhoneNumber(customerRequest.getPhoneNumber());

//        save customer
        Customer savedCustomer = customerRepository.save(newCustomer);
//
        Map<Integer, Integer> productQuantities = new HashMap<>();

        for (OrderRequest o : customerRequest.getOrderList()) {
           productQuantities.put(o.getProductId(), o.getQuantity());
        }

        //        call order function to add order to table order
        orderServiceImp.createOrderWithProducts(savedCustomer.getCustomerId(), productQuantities);

        List<Order> orderList = orderRepository.findByCustomer_CustomerId(savedCustomer.getCustomerId());
        savedCustomer.setOrderList(orderList);

        return savedCustomer.customerResponse();

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

//    Insert email to email table
    private Email insertEmail(String e) {
        //        insert email to email entity
        Email email = new Email();
        email.setEmail(e);
        return email;
    }

//    Insert order to order table
//    private Order insertOrder(Integer productId, Integer quantity, Customer newCustomer) {
//        //        Float unitPrice = productRepository.getByUnitPrice(customerRequest.getOrderList().getFirst().getProductName());
//        Float unitPrice = productRepository.getByUnitPrice(productId);
//
//        Order order = new Order();
//        order.setOrderDate(LocalDate.now());
//        order.setCustomer(newCustomer);
//        order.setTotalAmount(quantity * unitPrice);
//        order.setStatus(OrderStatus.valueOf("PENDING"));
//
//        orderRepository.save(order);
////        List<Integer> proId = new ArrayList<>();
////        proId.add(productId);
////        for(Integer pId : proId) {
////            orderRepository.insertIdIntoPOTable(pId, order.getOrderId());
////        }
////        orderRepository.insertIdIntoPOTable(productId, order.getOrderId());
//
//        return order;
//
//    }
}
