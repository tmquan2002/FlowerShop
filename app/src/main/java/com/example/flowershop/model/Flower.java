package com.example.flowershop.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Flower {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private String name;
    private String description;
    private int amount;
    private double price;
}
