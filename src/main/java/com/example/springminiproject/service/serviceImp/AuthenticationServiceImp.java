package com.example.springminiproject.service.serviceImp;

import com.example.springminiproject.config.JwtService;
import com.example.springminiproject.model.User;
import com.example.springminiproject.repository.UserRepository;
import com.example.springminiproject.request.LoginRequest;
import com.example.springminiproject.request.RegisterRequest;
import com.example.springminiproject.response.LoginResponse;
import com.example.springminiproject.response.RegisterResponse;
import com.example.springminiproject.service.AuthenticationService;
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
public class AuthenticationServiceImp implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
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
                .userName(request.getUsername())
                .email(request.getEmail())
                .role(request.getRole())
                .build();
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (DisabledException e) {
//            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
//            throw new Exception("INVALID_CREDENTIALS", e);
        }

        var user = userRepository.findUserByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        return LoginResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Login Successfully")
                .token(jwtToken)
                .build();
    }

    public boolean checkIfUsernameAlreadyExist(String userName) {
        return userRepository.findUserByUsername(userName).isPresent();
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
