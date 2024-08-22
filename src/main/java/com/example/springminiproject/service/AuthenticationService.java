package com.example.springminiproject.service;

import com.example.springminiproject.request.LoginRequest;
import com.example.springminiproject.request.UserRequest;
import com.example.springminiproject.response.LoginResponse;
import com.example.springminiproject.response.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationService {

    UserDTO register(UserRequest userRequest) throws Exception;

    LoginResponse login(LoginRequest loginRequest) throws Exception;

//    UserDetails loadUserByUsername(String email);

}
