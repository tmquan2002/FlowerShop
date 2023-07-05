package com.example.flowershop.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import androidx.room.rxjava3.EmptyResultSetException;

import com.example.flowershop.FlowerDatabase;
import com.example.flowershop.exception.NotEnoughAmountException;
import com.example.flowershop.model.Cart;
import com.example.flowershop.model.relation.CartAndFlower;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public abstract class CartDao {
    private final FlowerDao flowerDao;

    public CartDao(FlowerDatabase db) {
        flowerDao = db.flowerDao();
    }

    @Query("SELECT * FROM cart WHERE userId = :userId")
    @Transaction
    public abstract Single<List<CartAndFlower>> getByUserId(int userId);

    @Query("SELECT * FROM cart WHERE userId = :userId AND flowerId = :flowerId")
    protected abstract Single<CartAndFlower> getByUserIdAndFlowerId(int userId, int flowerId);

    @Insert
    protected abstract Completable _add(Cart cart);

    @Update
    protected abstract Completable update(Cart cart);

    @Delete
    public abstract Completable delete(Cart cart);

    @Query("DELETE FROM cart WHERE userId = :userId")
    public abstract Completable deleteByUserId(int userId);

    public Completable add(Cart cart) {
        return getByUserIdAndFlowerId(cart.getUserId(), cart.getFlowerId())
                .flatMapCompletable(c -> {
                    int newAmount = c.getCart().getAmount() + cart.getAmount();
                    if (c.getFlower().getAmount() < newAmount) {
                        return Completable.error(new NotEnoughAmountException());
                    }
                    cart.setAmount(newAmount);
                    return update(cart);
                })
                .onErrorResumeNext(e -> {
                    if (e instanceof EmptyResultSetException) {
                        return flowerDao.getById(cart.getFlowerId())
                                .flatMapCompletable(f -> {
                                    if (f.getAmount() < cart.getAmount()) {
                                        return Completable.error(new NotEnoughAmountException());
                                    }
                                    return _add(cart);
                                });
                    }
                    return Completable.error(e);
                });
    }
}
