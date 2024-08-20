package com.example.springminiproject.controller;

import com.example.springminiproject.model.User;
import com.example.springminiproject.request.UserRequest;
import com.example.springminiproject.response.ApiResponse;
import com.example.springminiproject.response.dto.UserDTO;
import com.example.springminiproject.response.dto.UserEntityDTO;
import com.example.springminiproject.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserDTO>> createNewUser(@RequestBody UserRequest userRequest) {
        ApiResponse<UserDTO> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.CREATED);
        apiResponse.setMessage("A new user is created successfully.");
        apiResponse.setPayload(userService.createNewUser(userRequest));

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);

    }

//    get all users
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllUsers(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "userId") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {

        List<UserDTO> userDTOList = userService.getAllUsers(pageNo, pageSize, sortBy, sortDirection);
        ApiResponse<List<UserDTO>> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("Get all users successfully.");
        apiResponse.setPayload(userDTOList);
        return ResponseEntity.ok().body(apiResponse);
    }

//    get user by id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserByUserId(@PathVariable Long id) {
        ApiResponse<UserDTO> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("Get user with id " + id + " successfully.");
        apiResponse.setPayload(userService.getUserByUserId(id));
        return ResponseEntity.ok().body(apiResponse);
    }

    //   delete product by id
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> deleteUserById(@PathVariable Long id) {
        userService.deleteUserByUserId(id);

        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("User with id " + id + " is deleted successfully.");
//        apiResponse.setPayload(null);
        return ResponseEntity.ok().body(apiResponse);

    }

//    update user by id
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> updateUserById(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        userService.updateUserById(id, userRequest);
        ApiResponse<UserDTO> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("User id " + id + " is updated successfully.");
        apiResponse.setPayload(userService.getUserByUserId(id));

        return ResponseEntity.ok().body(apiResponse);
    }

}
