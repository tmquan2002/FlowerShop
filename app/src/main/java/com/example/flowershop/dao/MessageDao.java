package com.example.flowershop.dao;

import com.example.flowershop.database.FirebaseDb;
import com.example.flowershop.model.ChatUser;
import com.example.flowershop.model.Message;
import com.example.flowershop.util.Constant;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;
import java.util.Map;

public class MessageDao {
    private final DatabaseReference messagesRef;

    public MessageDao(FirebaseDatabase database) {
        messagesRef = database.getReference(Constant.DATABASE_REF_MESSAGES);
    }

    public Query getChatUserQuery() {
        return messagesRef.orderByChild(Constant.DATABASE_REF_LAST_MESSAGE);
    }

    public Query getMessageQueryByUserId(int userId) {
        return messagesRef.child(String.valueOf(userId)).child(Constant.DATABASE_REF_MESSAGES);
    }

    public Task<Void> sendMessage(int userId, Message message) {
        DatabaseReference chatUserRef = messagesRef.child(String.valueOf(userId));
        String newMessageKey = chatUserRef.child(Constant.DATABASE_REF_MESSAGES).push().getKey();

        Map<String, Object> update = new ChatUser(userId, newMessageKey).toMap();
        update.put(String.format("%s/%s", Constant.DATABASE_REF_MESSAGES, newMessageKey), message);

        return chatUserRef.updateChildren(update);
    }
}
