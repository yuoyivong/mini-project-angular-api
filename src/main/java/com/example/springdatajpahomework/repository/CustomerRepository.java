package com.example.springdatajpahomework.repository;

import com.example.springdatajpahomework.model.Customer;
import com.example.springdatajpahomework.request.CustomerRequest;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

//    @Modifying
//    @Query("UPDATE Customer c SET c.name = 'demon' WHERE c.customerId = :id")
//    Customer updateCustomerByCustomerId(Long id);

    @Modifying
    @Transactional
    @Query(value = """
                WITH updated_email AS (
                    UPDATE email e
                    SET email = :email
                    WHERE e.id = (SELECT email_id FROM customer WHERE customer_id = :id)
                    RETURNING e.id
                )
                UPDATE customer c
                SET customer_name = :cusName,
                    address = :address,
                    phone_number = :phoneNumber,
                    email_id = (SELECT id FROM updated_email)
                WHERE c.customer_id = :id
            """, nativeQuery = true)
    int updateCustomerByCustomerId(Long id, String cusName, String address, String phoneNumber, String email);

}
