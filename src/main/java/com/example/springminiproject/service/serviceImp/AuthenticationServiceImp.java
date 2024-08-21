package com.example.springminiproject.service.serviceImp;

import com.example.springminiproject.config.JwtService;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    @Override
    public UserDTO register(UserRequest request) {
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

        System.out.println("User find by email : " + user);
        return LoginResponse.builder()
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
        return user.filter(value -> passwordEncoder.matches(password, value.getPassword())).isPresent();

    }

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Optional<User> userDetail = userRepository.findUserByEmail(email);
//        return userDetail.map(UserInfoDetails::new).orElseThrow(()-> new UsernameNotFoundException("User not found."));
//    }

}
