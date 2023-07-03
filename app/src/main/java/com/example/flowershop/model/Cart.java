package com.example.flowershop.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity(primaryKeys = {"userId", "flowerId"})
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Cart {
    @NonNull
    private Integer userId;
    @NonNull
    private Integer flowerId;
    private Integer amount;
}
