package com.example.springminiproject.response;

import com.example.springminiproject.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponse {
    private Integer status;
    private String message;
    private String userName;
    private String email;
    private Role role;

}