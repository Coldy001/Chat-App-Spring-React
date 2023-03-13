package com.example.springServer.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.example.springServer.model.Conversation;
import com.example.springServer.model.Message;
import com.example.springServer.model.User;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public User saveUser(User user) {
        return mongoTemplate.save(user, "users");
    }

    @Override
    public List<User> getAllUsers() {
        return mongoTemplate.findAll(User.class, "users");
    }

    @Override
    public User getContactsOfUser(String username) {
        return mongoTemplate.findOne(Query.query(Criteria.where("username").is(username)),
                User.class, "users");
    }

    @Override
    public User authUser(String username, String password) {
        return mongoTemplate.findOne(Query.query(Criteria.where("username").is(username)),
                User.class, "users");
    }

    @Override
    public void addContact(String username, String contactName) {
        // Add Contact
        mongoTemplate.updateFirst(Query.query(Criteria.where("username").is(username)),
                new Update().push("contacts", username + "-" + contactName), User.class, "users");
        mongoTemplate.updateFirst(Query.query(Criteria.where("username").is(contactName)),
                new Update().push("contacts", username + "-" + contactName), User.class, "users");
        // Add Contact Chat
        Conversation conCov = new Conversation();
        conCov.setChatid(username + "-" + contactName);
        conCov.setMembers(new String[] { username, contactName });
        mongoTemplate.save(conCov, "chats");
    }

    @Override
    public void addChat(Message message) {
        List<String> members = new ArrayList<>();
        members.add(message.getFrom());
        members.add(message.getTo());
        Query query = new Query();
        query.addCriteria(Criteria.where("members").all(members));
        mongoTemplate.updateFirst(query, new Update().push("messages", message), "chats");
    }

    @Override
    public Conversation getChat(String sender, String receiver) {
        List<String> members = new ArrayList<>();
        members.add(sender);
        members.add(receiver);
        Query query = new Query();
        query.addCriteria(Criteria.where("members").all(members));
        return mongoTemplate.findOne(query, Conversation.class, "chats");
    }
}
