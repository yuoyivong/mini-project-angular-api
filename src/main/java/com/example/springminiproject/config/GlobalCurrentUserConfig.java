package com.example.springminiproject.config;

import com.example.springminiproject.response.dto.UserDTO;
import com.example.springminiproject.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Configuration
public class GlobalCurrentUserConfig {

    private final UserService userService;

    public GlobalCurrentUserConfig(UserService userService) {
        this.userService = userService;
    }

    public UserDTO getCurrentUserInformation(Authentication auth) {
        System.out.println("Auth : " + auth);
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        return userService.findUserByEmail(userDetails.getUsername());
    }

}
