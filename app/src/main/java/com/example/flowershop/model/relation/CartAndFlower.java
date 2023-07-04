package com.example.flowershop.model.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.flowershop.model.Cart;
import com.example.flowershop.model.Flower;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartAndFlower {
    @Embedded
    private Cart cart;
    @Relation(parentColumn = "flowerId", entityColumn = "id")
    private Flower flower;
}
