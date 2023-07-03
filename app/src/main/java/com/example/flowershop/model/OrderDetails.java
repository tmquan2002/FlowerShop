package com.example.flowershop.model;

import androidx.room.Entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity(primaryKeys = {"orderId", "flowerId"})
@Builder
@Getter
@Setter
public class OrderDetails {
    private Integer orderId;
    private Integer flowerId;
    private Double price;
    private Integer amount;
}
