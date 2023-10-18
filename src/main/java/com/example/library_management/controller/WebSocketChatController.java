package com.example.library_management.controller;

import com.example.library_management.model.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebSocketChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/resume")
    @SendTo("/start/initial")
    public String chat(String msg) {
        System.out.println("Chat : " + msg);
        return msg;
    }

    @MessageMapping("/message")
    @SendTo("/start/public")
    public Message receiveMessage(Message msg) {
        System.out.println("Chat : " + msg);
        return msg;
    }

    @MessageMapping("/private-message")
    public Message privateMessage(Message message) {
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(), "/private", message);
        System.out.println(message.toString());
        return message;
    }

    @GetMapping("/test")
    public String test() {
        return "Hello World";
    }


}
