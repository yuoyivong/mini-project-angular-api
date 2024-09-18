package spring.monster.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.monster.model.request.UserRequest;
import spring.monster.model.response.ApiResponse;
import spring.monster.model.response.dto.UserResponse;
import spring.monster.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@SecurityRequirement(name = "Oauth2KeycloakAuth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//    create new user
    @PostMapping("/user")
    public ResponseEntity<ApiResponse<UserResponse>> addNewUser(@RequestBody UserRequest userRequest) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.CREATED);
        apiResponse.setMessage("User created successfully");
        apiResponse.setPayload(userService.createNewUser(userRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);

    }

//    get all users
    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        ApiResponse<List<UserResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("Get all users successfully");
        apiResponse.setPayload(userService.getAllUsers());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

//    get user by username
    @GetMapping("/user/username")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getUserByUsername(@RequestParam String username) {
        ApiResponse<List<UserResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("Get user with username " + username + " successfully");
        apiResponse.setPayload(userService.getUserByUsername(username));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

//    get user by user id
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable String userId) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("Get user with id " + userId + " successfully");
        apiResponse.setPayload(userService.getUserById(userId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

//    get user by email
    @GetMapping("/user/email")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getUserByEmail(@RequestParam String email) {
        ApiResponse<List<UserResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("Get user with email " + email + " successfully");
        apiResponse.setPayload(userService.getUserByEmail(email));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

//    update user by id
    @PutMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(@PathVariable String userId, @RequestBody UserRequest userRequest) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("Update user with id " + userId + " successfully");
        apiResponse.setPayload(userService.updateUser(userId, userRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

//    delete user by user id
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("Delete user with id " + userId + " successfully");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
