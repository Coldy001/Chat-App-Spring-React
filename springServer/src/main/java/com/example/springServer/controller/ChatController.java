package com.example.springServer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.springServer.model.Message;

@CrossOrigin
public class ChatController {

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/private")
    public Message sendSpecificUser(@RequestBody Message message) {
        // System.out.println(message.toString());
        simpMessagingTemplate.convertAndSendToUser(message.getTo(), "/specific", message);
        return message;
    }

    // @MessageMapping("/chat")
    // public Message recMessage(@Payload Message message) {
    // simpMessagingTemplate.convertAndSendToUser(message.getTo(),
    // "/queue/messages", message);
    // System.out.println(message.toString());
    // return message;
    // }
}
