package com.example.library_management.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    @NotBlank(message = "Username cannot be blank.")
    private String username;

    @NotBlank(message = "Email cannot be blank.")
    @Email
    private String email;

    @NotBlank(message = "Password cannot be blank.")
    private String password;
}