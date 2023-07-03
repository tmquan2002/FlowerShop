package com.example.flowershop.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private String username;
    private String password;
}
