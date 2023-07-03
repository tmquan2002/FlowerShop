package com.example.flowershop.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(primaryKeys = {"userId", "flowerId"})
public class Cart {
    private Integer userId;
    private Integer flowerId;
    private Integer amount;
}
