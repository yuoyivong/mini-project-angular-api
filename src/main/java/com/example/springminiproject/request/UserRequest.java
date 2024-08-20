package com.example.springminiproject.request;

import lombok.Data;

@Data
public class UserRequest {

    private String username;
    private String email;
    private String address;
    private String phoneNumber;

}
