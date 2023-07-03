package com.example.flowershop.model;

import androidx.room.Entity;

@Entity(primaryKeys = {"orderId", "flowerId"})
public class OrderDetails {
    private Integer orderId;
    private Integer flowerId;
    private Integer amount;
}
