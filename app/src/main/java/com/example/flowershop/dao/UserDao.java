package com.example.flowershop.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.flowershop.model.User;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user WHERE username = :username AND password = :password")
    Single<User> getByUsernameAndPassword(String username, String password);

    @Insert
    Completable add(User user);
}
