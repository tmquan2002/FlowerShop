package com.example.flowershop.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
public class Flower {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private String name;
    private String description;
    private Integer amount;
    private Double price;
}
