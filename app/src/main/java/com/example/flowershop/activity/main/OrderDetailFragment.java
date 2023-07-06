package com.example.flowershop.activity.main;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flowershop.FlowerDatabase;
import com.example.flowershop.R;
import com.example.flowershop.adapter.OrderDetailAdapter;
import com.example.flowershop.model.Order;
import com.example.flowershop.model.relation.OrderDetailsAndFlower;

import java.text.DateFormat;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class OrderDetailFragment extends Fragment {
    static int currentOrderId;
    static Order currentOrder;
    private final CompositeDisposable mDisposable = new CompositeDisposable();
    List<OrderDetailsAndFlower> list;
    RecyclerView rvOrderDetail;
    Context context;
    TextView orderDate, address, phone, status, deliverDate, total;
    double orderTotal = 0;

    public OrderDetailFragment() {
        // Required empty public constructor
    }

    public static OrderDetailFragment newInstance(int orderId) {
        //Get flower details from item and add hto args bundle
        currentOrderId = orderId;
        return new OrderDetailFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_detail, container, false);
        rvOrderDetail = view.findViewById(R.id.rvOrderDetail);
        orderDate = view.findViewById(R.id.orderDetailOrderDate);
        address = view.findViewById(R.id.orderDetailAddress);
        phone = view.findViewById(R.id.orderDetailPhone);
        status = view.findViewById(R.id.orderDetailStatus);
        deliverDate = view.findViewById(R.id.orderDetailDeliveryDate);
        total = view.findViewById(R.id.orderDetailTotal);
        getOrderInfo();
        getOrderDetail();
        return view;
    }

    private void getOrderDetail() {
        mDisposable.add(FlowerDatabase.getInstance(context).orderDetailsDao().getByOrderId(currentOrderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((orderDetailsAndFlowerList) -> {
                            list = orderDetailsAndFlowerList;
                            for (OrderDetailsAndFlower orderDetailsAndFlower : orderDetailsAndFlowerList) {
                                orderTotal = orderTotal + orderDetailsAndFlower.getOrderDetails().getAmount() * orderDetailsAndFlower.getOrderDetails().getPrice();
                            }
                            total.setText(String.format("Total: %s VND", orderTotal));
                            OrderDetailAdapter adapter = new OrderDetailAdapter(list, context);
                            rvOrderDetail.setAdapter(adapter);
                            rvOrderDetail.setLayoutManager(new LinearLayoutManager(context));
                        },
                        throwable -> Log.e("GetFailed", "getByOrderId: Cannot get the list", throwable)));
    }

    private void getOrderInfo() {
        mDisposable.add(FlowerDatabase.getInstance(context).orderDao().getById(currentOrderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((order) -> {
                            currentOrder = order;
                            orderDate.setText(String.format("Order Date: %s", DateFormat.getDateInstance(DateFormat.SHORT).format(order.getOrderDate())));
                            address.setText(String.format("Address: %s", order.getAddress()));
                            phone.setText(String.format("Phone: %s", order.getPhone()));
                            status.setText(String.format("%s", order.getStatus()));
                            deliverDate.setText(String.format("Deliver Date: %s", order.getDeliveryDate() == null ? "" : DateFormat.getDateInstance(DateFormat.SHORT).format(order.getDeliveryDate())));
                        },
                        throwable -> Log.e("GetFailed", "getByOrderId: Cannot get the list", throwable)));
    }
}