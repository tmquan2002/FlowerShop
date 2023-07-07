package com.example.flowershop.database;

import com.example.flowershop.FlowerDatabase;
import com.example.flowershop.dao.MessageDao;
import com.example.flowershop.util.Constant;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDb {
    private static volatile FirebaseDb instance;
    private static volatile FirebaseDatabase database;
    private volatile MessageDao messageDao;

    private FirebaseDb() {
        database = FirebaseDatabase.getInstance(Constant.DATABASE_INSTANCE);
    }

    public static FirebaseDb getInstance() {
        if (instance == null) {
            synchronized (FlowerDatabase.class) {
                if (instance == null)
                    instance = new FirebaseDb();
            }
        }
        return instance;
    }

    public MessageDao messageDao() {
        if (messageDao == null) {
            synchronized (FlowerDatabase.class) {
                if (messageDao == null)
                    messageDao = new MessageDao(database);
            }
        }
        return messageDao;
    }
}
