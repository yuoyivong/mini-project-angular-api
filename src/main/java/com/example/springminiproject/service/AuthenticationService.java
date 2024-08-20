package com.example.springminiproject.service;

import com.example.springminiproject.request.LoginRequest;
import com.example.springminiproject.request.RegisterRequest;
import com.example.springminiproject.response.LoginResponse;
import com.example.springminiproject.response.RegisterResponse;

public interface AuthenticationService {

    RegisterResponse register(RegisterRequest registerRequest);

    LoginResponse login(LoginRequest loginRequest);
}
