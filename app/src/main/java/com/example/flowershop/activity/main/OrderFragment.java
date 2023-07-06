package com.example.flowershop.activity.main;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flowershop.FlowerDatabase;
import com.example.flowershop.R;
import com.example.flowershop.adapter.OrderAdapter;
import com.example.flowershop.model.Order;
import com.example.flowershop.util.UserHelper;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class OrderFragment extends Fragment {
    private final CompositeDisposable mDisposable = new CompositeDisposable();
    List<Order> list;
    private RecyclerView rv;
    private Context context;
    public OrderFragment() {
        // Required empty public constructor
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        rv = view.findViewById(R.id.list);
        getOrder();
        return view;
    }

    private void getOrder() {
        mDisposable.add(FlowerDatabase.getInstance(context).orderDao().getByUserId(UserHelper.getAuthUser().getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((orderList) -> {
                            list = orderList;
                            OrderAdapter adapter = new OrderAdapter(list, context);
                            rv.setAdapter(adapter);
                            rv.setLayoutManager(new LinearLayoutManager(context));
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