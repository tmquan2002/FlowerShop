package com.example.flowershop.activity.admin;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.flowershop.FlowerDatabase;
import com.example.flowershop.R;
import com.example.flowershop.adapter.CartAdapter;
import com.example.flowershop.adapter.ManageOrderAdapter;
import com.example.flowershop.model.Order;
import com.example.flowershop.model.relation.CartAndFlower;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ManageOrdersFragment extends Fragment implements ManageOrderAdapter.OnStatusChangeListener {

    private final CompositeDisposable mDisposable = new CompositeDisposable();
    List<Order> list;
    private RecyclerView rv;
    private Context context;

    public ManageOrdersFragment() {
        // Required empty public constructor
    }

    private void setupRecyclerView(List<Order> list) {
        ManageOrderAdapter adapter = new ManageOrderAdapter(context, list, this);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manage_orders, container, false);
        rv = view.findViewById(R.id.listManage);
        getOrder();
        return view;
    }

    @Override
    public void getOrder() {
        mDisposable.add(FlowerDatabase.getInstance(context).orderDao().getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((orderList) -> {
                            list = orderList;
                            setupRecyclerView(list);
                        },
                        throwable -> Log.e("GetFailed", "getOrdersById: Cannot get the list", throwable)));
    }

    @Override
    public void onStop() {
        super.onStop();

        // clear all the subscriptions
        mDisposable.clear();
    }
}