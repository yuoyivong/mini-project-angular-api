//package com.example.springminiproject.utils;
//
//import com.example.springminiproject.response.dto.UserDTO;
//import com.example.springminiproject.service.UserService;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//
//public class GlobalCurrentUser {
//    private final UserService userService;
//
//
//    public GlobalCurrentUser(UserService userService) {
//        this.userService = userService;
//    }
//
//    public GlobalCurrentUser() {
//
//    }
//
//    public UserDTO getCurrentUserInformation() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        UserDetails userDetails = (UserDetails) auth.getPrincipal();
//        return userService.findUserByEmail(userDetails.getUsername());
//    }
//
//}
