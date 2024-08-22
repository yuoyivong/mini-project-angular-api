package com.example.springminiproject.response;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String error;



}
