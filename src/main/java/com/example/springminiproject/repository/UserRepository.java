package com.example.springminiproject.repository;

import com.example.springminiproject.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Modifying
    @Transactional
    @Query(value = """
        UPDATE user_tb 
        SET username = :username,
            email = :email,
            address = :address,
            phone_number = :phoneNumber,
            updated_at = CURRENT_TIMESTAMP
        WHERE user_id = :id
    """, nativeQuery = true)
    void updateUserByUserId(Long id, String username, String email, String address, String phoneNumber);

    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByUsername(String userName);
}
