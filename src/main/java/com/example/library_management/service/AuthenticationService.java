package com.example.library_management.service;

import com.example.library_management.Exception.EmailExceptionHandler;
import com.example.library_management.config.JwtService;
import com.example.library_management.model.entity.Role;
import com.example.library_management.model.entity.User;
import com.example.library_management.model.request.AuthenticationRequest;
import com.example.library_management.model.request.RegisterRequest;
import com.example.library_management.model.response.AuthenticationResponse;
import com.example.library_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {

//        if(request.getEmail().isBlank()) {
//            throw new EmailExceptionHandler("Email cannot be blank!");
//        }
//
//        return null;
        var user = User.builder()
                .user_name(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.NORMAL_USER)
                .build();

        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findUserByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
