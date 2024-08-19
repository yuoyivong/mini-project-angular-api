package com.example.springminiproject.service.serviceImp;

import com.example.springminiproject.repository.UserRepository;
import com.example.springminiproject.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
