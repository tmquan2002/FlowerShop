package com.example.flowershop.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity(primaryKeys = {"userId", "flowerId"})
@Builder
@Getter
@Setter
public class Cart {
    private Integer userId;
    private Integer flowerId;
    private Integer amount;
}
