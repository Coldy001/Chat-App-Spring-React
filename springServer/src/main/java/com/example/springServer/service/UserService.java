package com.example.springServer.service;

import java.util.List;

import com.example.springServer.model.Conversation;
import com.example.springServer.model.Message;
import com.example.springServer.model.User;

public interface UserService {
    public User saveUser(User user);

    public List<User> getAllUsers();

    public User authUser(String username, String password);

    public void addContact(String username, String contactName);

    public User getContactsOfUser(String username);

    public void addChat(Message message);

    public Conversation getChat(String sender, String receiver);
}
