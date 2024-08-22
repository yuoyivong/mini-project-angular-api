package com.example.springminiproject.request;

import com.example.springminiproject.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRequest {

    private String username;

    @NotBlank(message = "The email is required.")
    @Email(message = "Invalid email address.")
    private String email;

    private String address;

    @Pattern(regexp = "^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$",
            message = "Phone number should follow this format: 012-333-4321 or 012 333 4321")
    private String phoneNumber;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character.")
    @NotBlank
    private String password;

    @NotEmpty(message = "Who do you want to be? An AUTHOR or READER?")
    @NotBlank
    private Role role;

}
