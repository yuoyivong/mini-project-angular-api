package com.example.springminiproject.service;

import com.example.springminiproject.request.UserRequest;
import com.example.springminiproject.response.dto.UserDTO;
import com.example.springminiproject.response.dto.UserEntityDTO;

import java.util.List;

public interface UserService {

    UserDTO createNewUser(UserRequest userRequest);

    List<UserDTO> getAllUsers(int pageNo, int pageSize, String sortBy, String sortDirection);

    UserDTO getUserByUserId(Long id);

    void deleteUserByUserId(Long id);

    void updateUserById(Long id, UserRequest userRequest);

}
