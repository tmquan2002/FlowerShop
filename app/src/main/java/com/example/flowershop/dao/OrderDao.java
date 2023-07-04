package com.example.flowershop.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.flowershop.model.Order;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface OrderDao {
    @Query("SELECT * FROM `order` WHERE userId = :userId")
    Single<List<Order>> getByUserId(int userId);

    @Query("SELECT * FROM `order` WHERE id = :id")
    Single<Order> getById(int id);

    @Query("SELECT * FROM `order`")
    Single<List<Order>> getAll();

    @Insert
    Completable add(Order order);

    @Update
    Completable update(Order order);
}
