package com.example.flowershop;

import android.app.Application;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.flowershop.dao.CartDao;
import com.example.flowershop.dao.Converter;
import com.example.flowershop.dao.FlowerDao;
import com.example.flowershop.dao.OrderDao;
import com.example.flowershop.dao.OrderDetailsDao;
import com.example.flowershop.dao.UserDao;
import com.example.flowershop.model.Cart;
import com.example.flowershop.model.Flower;
import com.example.flowershop.model.Order;
import com.example.flowershop.model.OrderDetails;
import com.example.flowershop.model.User;

@Database(entities = {User.class, Cart.class, Flower.class, Order.class, OrderDetails.class}, version = 1)
@TypeConverters({Converter.class})
public abstract class FlowerDatabase extends RoomDatabase {
    private static FlowerDatabase instance;

    public static FlowerDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            FlowerDatabase.class,
                            "FlowerShop")
                    .createFromAsset("database/FlowerShop.db")
                    .build();
        }
        return instance;
    }

    public abstract CartDao cartDao();

    public abstract FlowerDao flowerDao();

    public abstract OrderDao orderDao();

    public abstract OrderDetailsDao orderDetailsDao();

    public abstract UserDao userDao();
}
