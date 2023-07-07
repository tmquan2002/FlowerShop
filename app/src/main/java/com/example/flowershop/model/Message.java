package com.example.flowershop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Message {
    private String content;
    private long timestamp;
    private boolean isAdmin;

    public Message(String content, boolean isAdmin) {
        this.content = content;
        this.isAdmin = isAdmin;
        timestamp = System.currentTimeMillis();
    }
}
