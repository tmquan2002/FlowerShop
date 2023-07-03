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

    public Flower(Integer id, String name, String description, int amount, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getAmount() {
        return amount;
    }

    public double getPrice() {
        return price;
    }
}
