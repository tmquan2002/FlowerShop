package com.example.flowershop.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity(primaryKeys = {"orderId", "flowerId"})
@AllArgsConstructor
@Builder
@Getter
@Setter
public class OrderDetails {
    @NonNull
    private Integer orderId;
    @NonNull
    private Integer flowerId;
    private Double price;
    private Integer amount;
}
