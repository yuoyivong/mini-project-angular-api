package com.example.library_management.model.response;


import com.example.library_management.model.entity.Role;
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
    private String readerName;
    private String email;
    private Role role;

}