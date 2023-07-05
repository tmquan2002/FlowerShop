package com.example.flowershop.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.flowershop.model.OrderDetails;
import com.example.flowershop.model.relation.OrderDetailsAndFlower;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface OrderDetailsDao {
    @Query("SELECT * FROM orderdetails WHERE orderId = :orderId")
    @Transaction
    Single<List<OrderDetailsAndFlower>> getByOrderId(int orderId);

    @Insert
    Completable add(List<OrderDetails> orderDetailsList);
}
