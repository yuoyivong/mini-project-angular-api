package com.example.library_management.controller;

import com.example.library_management.Exception.BlankFieldExceptionHandler;
import com.example.library_management.model.request.AuthenticationRequest;
import com.example.library_management.model.request.RegisterRequest;
import com.example.library_management.model.response.AuthenticationResponse;
import com.example.library_management.service.AuthenticationService;
import jakarta.validation.Valid;
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
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody @Valid RegisterRequest request
    ) {

        if(request.getUsername().isBlank()) {
            throw new BlankFieldExceptionHandler("Field username is empty");
        }

        if(request.getEmail().isBlank()) {
            throw new BlankFieldExceptionHandler("Field email is empty");
        }

        if(request.getPassword().isBlank()) {
            throw new BlankFieldExceptionHandler("Field password is empty");
        }

        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.login(request));
    }
}