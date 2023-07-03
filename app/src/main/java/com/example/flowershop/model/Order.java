package com.example.flowershop.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Order {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private Integer userId;
    private String address;
    private String phone;
    private OrderStatus status;
    private Date orderDate;
    private Date deliveryDate;
}
