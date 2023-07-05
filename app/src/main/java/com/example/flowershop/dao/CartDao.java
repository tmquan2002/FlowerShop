package com.example.flowershop.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.flowershop.model.Cart;
import com.example.flowershop.model.relation.CartAndFlower;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface CartDao {
    @Query("SELECT * FROM cart WHERE userId = :userId")
    @Transaction
    Single<List<CartAndFlower>> getByUserId(int userId);

    @Insert
    Completable add(Cart cart);

    @Update
    Completable update(Cart cart);

    @Delete
    Completable delete(Cart cart);

    @Query("DELETE FROM cart WHERE userId = :userId")
    Completable deleteByUserId(int userId);
}
