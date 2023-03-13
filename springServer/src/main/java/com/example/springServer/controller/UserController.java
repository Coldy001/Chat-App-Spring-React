package com.example.springServer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springServer.model.Conversation;
import com.example.springServer.model.Message;
import com.example.springServer.model.User;
import com.example.springServer.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RestController
@RequestMapping("/endUser")
@CrossOrigin()
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String add(@RequestBody User user) {
        userService.saveUser(user);
        return "Registered";
    }

    @GetMapping("/getAll")
    public List<User> list() {
        return userService.getAllUsers();
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) throws JsonProcessingException {
        User dbCreds = userService.authUser(user.getUsername(), user.getPassword());
        ObjectMapper obj = new ObjectMapper();

        if (dbCreds.getUsername().equals(user.getUsername()) && dbCreds.getPassword().equals(user.getPassword())) {
            return obj.writeValueAsString(dbCreds);
        } else {
            return "Incorrect Creds";
        }
    }

    @PostMapping("/addContact")
    public String addContact(@RequestBody Members members) {
        userService.addContact(members.getSenderName(), members.getReceiverName());
        return "Contact Added Successfully";
    }

    @PostMapping("/getContactList")
    public User getContactList(@RequestBody String username) {
        return userService.getContactsOfUser(username);
    }

    @PostMapping("/addChat")
    public void addChat(@RequestBody Message message) {
        userService.addChat(message);
    }

    @PostMapping("/getChat")
    public Conversation getChat(@RequestBody Members members) {
        return userService.getChat(members.getSenderName(), members.getReceiverName());
    }

}

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
class Members {
    private String senderName;
    private String receiverName;
}