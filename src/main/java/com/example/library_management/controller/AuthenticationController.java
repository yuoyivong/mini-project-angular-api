package com.example.library_management.controller;

import com.example.library_management.Exception.BlankFieldExceptionHandler;
import com.example.library_management.Exception.IfAlreadyExistValidationException;
import com.example.library_management.model.request.AuthenticationRequest;
import com.example.library_management.model.request.RegisterRequest;
import com.example.library_management.model.response.AuthenticationResponse;
import com.example.library_management.model.response.RegisterResponse;
import com.example.library_management.service.AuthenticationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor

public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @RequestBody RegisterRequest request
    ) {

        if(request.getReaderName().isBlank()) {
            throw new BlankFieldExceptionHandler("Field readerName is empty");
        }

        if(request.getPassword().isBlank()) {
            throw new BlankFieldExceptionHandler("Field password is empty");
        }

        if(request.getEmail().isBlank()) {
            throw new BlankFieldExceptionHandler("Field email is empty");
        }

        if(request.getEmail() == null || !request.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$")) {
            throw new IfAlreadyExistValidationException("Invalid email address.");
        }

        if(service.checkIfUsernameAlreadyExist(request.getReaderName())) {
            throw new IfAlreadyExistValidationException("This username is already taken!");
        }

        if(service.checkIfEmailAlreadyExist(request.getEmail())) {
            throw new IfAlreadyExistValidationException("This email is not single anymore!");
        }

        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest authRequest
    ) throws Exception {

        if(authRequest.getEmail().isBlank()) {
            throw new BlankFieldExceptionHandler("Field email is empty.");
        }

        if(authRequest.getPassword().isBlank()) {
            throw new BlankFieldExceptionHandler("Field password is empty.");
        }

        if(!service.checkIfEmailAlreadyExist(authRequest.getEmail())) {
            throw new IfAlreadyExistValidationException("This email doesn't exist in the system yet. Please register first!");
        }

        if(!service.checkIfPasswordMatchesRegister(authRequest.getPassword(), authRequest.getEmail())) {
            throw new IfAlreadyExistValidationException("Password must match when you register!");
        }


        return ResponseEntity.ok(service.login(authRequest));
    }


}