package com.example.springServer.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Document(collection = "chats")
public class Conversation {
    // @Id
    // private String Id;
    private String chatid;
    private String[] members;
    private List<Message> messages;

}

// @AllArgsConstructor
// @NoArgsConstructor
// @Getter
// @Setter
// @ToString
// class Members {
// private String senderName;
// private String receiverName;
// }