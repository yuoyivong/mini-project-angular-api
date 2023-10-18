package com.example.library_management.model.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Message {

    private String senderName;
    private String receiverName;
    private String message;
    private String data;
    private Status status;

}
