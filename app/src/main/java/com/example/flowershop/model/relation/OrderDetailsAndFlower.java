package com.example.flowershop.model.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.flowershop.model.Flower;
import com.example.flowershop.model.OrderDetails;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailsAndFlower {
    @Embedded
    private OrderDetails orderDetails;
    @Relation(parentColumn = "flowerId", entityColumn = "id")
    private Flower flower;
}
