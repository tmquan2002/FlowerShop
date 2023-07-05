package com.example.flowershop.dao;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.flowershop.exception.UserAlreadyExistException;
import com.example.flowershop.model.User;
import com.example.flowershop.model.UserRole;

import io.reactivex.rxjava3.core.Single;

@Dao
public abstract class UserDao {
    @Query("SELECT * FROM user WHERE username = :username AND password = :password")
    public abstract Single<User> getByUsernameAndPassword(String username, String password);

    @Query("SELECT * FROM user WHERE username = :username")
    public abstract Single<User> getByUsername(String username);

    @Insert
    protected abstract Single<Long> add(User user);

    public Single<Long> register(@NonNull User user) {
        user.setRole(UserRole.USER);
        return getByUsername(user.getUsername())
                .flatMap(u -> {
                    Log.i("UserDao", "FLAT MAP");
                    return Single.<Long>error(new UserAlreadyExistException());
                })
                .onErrorResumeNext(e -> {
                    Log.e("UserDao", e.getMessage(), e);
                    if (e instanceof UserAlreadyExistException) {
                        return Single.error(e);
                    }
                    return add(user);
                });
    }
}
