package com.example.flowershop.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Builder
@Getter
@Setter
public class User {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private String username;
    private String password;
    private UserRole role;
}
