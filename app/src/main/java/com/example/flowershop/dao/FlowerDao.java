package com.example.flowershop.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.flowershop.model.Flower;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface FlowerDao {
    @Query("SELECT * FROM flower")
    Single<List<Flower>> getAll();

    @Query("SELECT * FROM flower WHERE id = :id")
    Single<Flower> getById(int id);

    @Query("SELECT * FROM flower WHERE name LIKE '%' || :name || '%'")
    Single<List<Flower>> getByName(String name);
}
