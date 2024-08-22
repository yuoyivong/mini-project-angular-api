package com.example.springminiproject.service.serviceImp;

import com.example.springminiproject.config.JwtService;
import com.example.springminiproject.exception.*;
import com.example.springminiproject.model.Role;
import com.example.springminiproject.model.User;
import com.example.springminiproject.repository.UserRepository;
import com.example.springminiproject.request.LoginRequest;
import com.example.springminiproject.request.UserRequest;
import com.example.springminiproject.response.LoginResponse;
import com.example.springminiproject.response.dto.UserDTO;
import com.example.springminiproject.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private boolean checkEmail;


    @Override
    public UserDTO register(UserRequest request) throws Exception {
        createNewUserValidation(request);

        try {
            var user = User.builder()
                    .username(request.getUsername())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .phoneNumber(request.getPhoneNumber())
                    .address(request.getAddress())
                    .role(request.getRole())
                    .createdAt(LocalDateTime.now())
                    .build();
            userRepository.save(user);

            return user.userDTOResponse();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    @Override
    public LoginResponse login(LoginRequest request) throws Exception {
        checkCredentials(request.getEmail(), request.getPassword());

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

        return LoginResponse.builder()
                .token(jwtToken)
                .build();
    }

//    public boolean checkIfUsernameAlreadyExist(String userName) {
//        return userRepository.findUserByUsername(userName).isPresent();
//    }
//
//    public boolean checkIfEmailAlreadyExist(String email) {
//        return userRepository.findUserByEmail(email).isPresent();
//    }
//
//    public boolean checkIfPasswordMatchesRegister(String password, String email) {
//        Optional<User> user = userRepository.findUserByEmail(email);
//        return user.filter(value -> passwordEncoder.matches(password, value.getPassword())).isPresent();
//
//    }

    private void createNewUserValidation(UserRequest userRequest) {

//        email field checked
        if(userRequest.getEmail().isBlank()) {
            throw new BlankFieldException("Email is required.");
        }

        if(!userRequest.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$")) {
            throw new RegexPatternException("Invalid email address.");
        }

//        UserDTO existingUser = userRepository.findUserByEmail(userRequest.getEmail()).orElseThrow(null).userDTOResponse();

        if(userRepository.findUserByEmail(userRequest.getEmail()).isPresent()) {
            throw new AlreadyExistsException("This email is already exists.");
        }

//        phone number field checked
        if(!userRequest.getPhoneNumber().isBlank() && !userRequest.getPhoneNumber().matches("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$")) {
            throw new RegexPatternException("Phone number should follow this format: 012-333-4321 or 012 333 4321.");
        }

//        password checked
        if(userRequest.getPassword().isBlank()) {
            throw new BlankFieldException("Password is required.");
        }

        if(!userRequest.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            throw new RegexPatternException("Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character.");
        }

//        role checked - pending
//        if(userRequest.getRole() == null) {
//            throw new BlankFieldException("Role is required.");
//        }
//
//        if(!userRequest.getRole().equals(Role.AUTHOR)
//                && !userRequest.getRole().equals(Role.READER)) {
//            throw new InvalidFieldException("You can choose to be a READER or an AUTHOR.");
//        }
//        try {
//            Role role = Role.valueOf(userRequest.getRole().toString().toUpperCase());
//            System.out.println("Role :  " + role);
//        } catch (InvalidFieldException e) {
//            throw new InvalidFieldException("You can choose to be a READER or an AUTHOR.");
//        }

    }

//    validate email and password when login
    private void checkCredentials(String email, String password) {
        Optional<User> user = userRepository.findUserByEmail(email);

        if(email.isBlank()) {
            throw new BlankFieldException("Email is required.");
        }

        if(!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$")) {
            throw new RegexPatternException("Invalid email address.");
        }

        if(userRepository.findUserByEmail(email).isEmpty()) {
            throw new NotFoundException("Email not found.");
        }

        if(password.isBlank()) {
            throw new BlankFieldException("Password is required.");
        }

        if(user.filter(value -> passwordEncoder.matches(password, value.getPassword())).isEmpty()) {
            throw new MismatchException("Password does not match.");
        }
    }

}
