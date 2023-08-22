package com.example.library_management.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

//    private final UserService userService;
//
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }
//
//    @GetMapping("/{user_id}")
//    public ResponseEntity<?> getUserById(@PathVariable Integer user_id) {
//        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById((user_id)));
//    }

    @GetMapping("/")
    public String test() {
        return "Hello World";
    }


}
