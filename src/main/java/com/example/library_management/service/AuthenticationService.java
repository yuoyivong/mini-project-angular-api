package com.example.library_management.service;

import com.example.library_management.config.JwtService;
import com.example.library_management.model.entity.Role;
import com.example.library_management.model.entity.User;
import com.example.library_management.model.request.AuthenticationRequest;
import com.example.library_management.model.request.RegisterRequest;
import com.example.library_management.model.response.AuthenticationResponse;
import com.example.library_management.model.response.RegisterResponse;
import com.example.library_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public RegisterResponse register(RegisterRequest request) {
        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        userRepository.save(user);
        return RegisterResponse.builder()
                .message("Register successfully!")
                .status(HttpStatus.OK.value())
                .username(request.getUsername())
                .email(request.getEmail())
                .role(request.getRole())
                .build();
    }

    public AuthenticationResponse login(AuthenticationRequest request) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        var user = userRepository.findUserByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Login Successfully")
                .token(jwtToken)
                .build();
    }

    public boolean checkIfUsernameAlreadyExist(String username) {
        return userRepository.findUserByUsername(username).isPresent();
    }

    public boolean checkIfEmailAlreadyExist(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }

    public boolean checkIfPasswordMatchesRegister(String password, String email) {
        Optional<User> user = userRepository.findUserByEmail(email);
        if(user == null) {
            return false;
        }

        return passwordEncoder.matches(password, user.get().getPassword());
    }

}