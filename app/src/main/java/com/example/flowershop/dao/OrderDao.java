package com.example.flowershop.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.flowershop.FlowerDatabase;
import com.example.flowershop.model.Order;
import com.example.flowershop.model.OrderDetails;
import com.example.flowershop.model.OrderStatus;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

@Dao
public abstract class OrderDao {
    private final OrderDetailsDao orderDetailsDao;
    private final CartDao cartDao;

    public OrderDao(FlowerDatabase db) {
        orderDetailsDao = db.orderDetailsDao();
        cartDao = db.cartDao();
    }

    @Query("SELECT * FROM `order` WHERE userId = :userId")
    public abstract Single<List<Order>> getByUserId(int userId);

    @Query("SELECT * FROM `order` WHERE id = :id")
    public abstract Single<Order> getById(int id);

    @Query("SELECT * FROM `order`")
    public abstract Single<List<Order>> getAll();

    @Insert
    protected abstract Single<Long> add(Order order);

    @Update
    public abstract Completable update(Order order);

    public Single<Long> createOrder(Order order) {
        return Single.create(emitter -> emitter.onSuccess(_createOrder(order)));
    }

    @Transaction
    protected long _createOrder(Order order) {
        order.setOrderDate(new Date());
        order.setStatus(OrderStatus.PENDING);

        int orderId = (int) add(order).blockingGet().longValue();
        if (orderId > 0) {
            Completable odAddObs = cartDao.getByUserId(order.getUserId())
                    .map(cartList -> cartList.stream()
                            .map(cart -> new OrderDetails(orderId, cart.getCart().getFlowerId(), cart.getFlower().getPrice(), cart.getCart().getAmount()))
                            .collect(Collectors.toList()))
                    .flatMapCompletable(orderDetailsDao::add);
            Completable.mergeArray(odAddObs, cartDao.deleteByUserId(order.getUserId()))
                    .blockingAwait();
        }
        return orderId;
    }
}
