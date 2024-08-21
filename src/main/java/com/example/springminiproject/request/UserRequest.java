package com.example.springminiproject.request;

import com.example.springminiproject.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class UserRequest {

    private String username;

    @NotBlank(message = "Please add your email address.")
    @Email
    private String email;

    private String address;

    @Pattern(regexp = "^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$")
    private String phoneNumber;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")
    @NotBlank
    private String password;

    @NotBlank(message = "Who do you want to be? An AUTHOR or READER?")
    private Role role;

}
