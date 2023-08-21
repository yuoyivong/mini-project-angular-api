package com.example.library_management.service;

import com.example.library_management.Exception.CustomAuthException;
import com.example.library_management.model.dto.UserDTO;
import com.example.library_management.model.entity.User;

public interface UserService {
//    User validateUser(String email, String password) throws CustomAuthException;
//
//    User registerUser(String username, String email, String password) throws CustomAuthException;

    UserDTO getUserById(Integer user_id);

}
