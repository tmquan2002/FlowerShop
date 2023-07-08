package com.example.flowershop.dao;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.rxjava3.EmptyResultSetException;

import com.example.flowershop.exception.UserAlreadyExistException;
import com.example.flowershop.model.User;
import com.example.flowershop.model.UserRole;

import io.reactivex.rxjava3.core.Single;

@Dao
public abstract class UserDao {
    @Query("SELECT * FROM user WHERE id = :id")
    public abstract Single<User> getById(long id);

    @Query("SELECT * FROM user WHERE username = :username AND password = :password")
    public abstract Single<User> getByUsernameAndPassword(String username, String password);

    @Query("SELECT * FROM user WHERE username = :username")
    public abstract Single<User> getByUsername(String username);

    @Insert
    protected abstract Single<Long> add(User user);

    public Single<Long> register(@NonNull User user) {
        user.setRole(UserRole.USER);
        return getByUsername(user.getUsername())
                .flatMap(u -> Single.<Long>error(new UserAlreadyExistException()))
                .onErrorResumeNext(e -> {
                    if (e instanceof EmptyResultSetException) {
                        return add(user);
                    }
                    return Single.error(e);
                });
    }
}
