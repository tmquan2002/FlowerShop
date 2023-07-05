package com.example.flowershop.activity.main;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flowershop.FlowerDatabase;
import com.example.flowershop.R;
import com.example.flowershop.adapter.CartAdapter;
import com.example.flowershop.model.Order;
import com.example.flowershop.model.relation.CartAndFlower;
import com.example.flowershop.util.UserHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CartFragment extends Fragment {

    private final CompositeDisposable mDisposable = new CompositeDisposable();
    List<CartAndFlower> list;
    private RecyclerView rv;
    private Context context;
    private TextView cartTotal;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        rv = view.findViewById(R.id.cartList);
        cartTotal = view.findViewById(R.id.cart_total);
        Button btnOrder = view.findViewById(R.id.btnOrder);
        btnOrder.setOnClickListener(v -> {
            //TODO: Open dialog to add user shipping info
            order(UserHelper.getAuthUser().getId());
            //TODO: Navigate to order detail to show what user has ordered
        });
        getCart();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.topbar, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search).setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(@NonNull MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(@NonNull MenuItem item) {
                CartAdapter adapter = new CartAdapter(list, context);
                rv.setAdapter(adapter);
                rv.setLayoutManager(new LinearLayoutManager(context));
                return true;
            }
        });
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query == null || query.trim().isEmpty()) {
                    CartAdapter adapter = new CartAdapter(list, context);
                    rv.setAdapter(adapter);
                    rv.setLayoutManager(new LinearLayoutManager(context));
                } else {
                    List<CartAndFlower> filteredList = new ArrayList<>();
                    for (CartAndFlower cartAndFlower : list) {
                        if (cartAndFlower.getFlower().getName().toLowerCase().contains(query.toLowerCase())) {
                            filteredList.add(cartAndFlower);
                        }
                    }
                    CartAdapter adapter = new CartAdapter(filteredList, context);
                    rv.setAdapter(adapter);
                    rv.setLayoutManager(new LinearLayoutManager(context));
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void getCart() {
        mDisposable.add(FlowerDatabase.getInstance(context).cartDao().getByUserId(UserHelper.getAuthUser().getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((cartAndFlowerList) -> {
                            list = cartAndFlowerList;
                            CartAdapter adapter = new CartAdapter(list, context);
                            rv.setAdapter(adapter);
                            rv.setLayoutManager(new LinearLayoutManager(context));

                            //Total Cart Price
                            double total = 0;
                            for (CartAndFlower cartAndFlower : list) {
                                total = total + cartAndFlower.getCart().getAmount() * cartAndFlower.getFlower().getPrice();
                            }
                            cartTotal.setText(String.valueOf(total));
                        },
                        throwable -> Log.e("GetFailed", "getCart: Cannot get the list", throwable)));
    }

    private void order(int userID) {
        //TODO: Replace with dialog info
        Order order = Order.builder().phone("09992").address("2231/213").userId(userID).build();
        mDisposable.add(FlowerDatabase.getInstance(context).orderDao().createOrder(order)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((orderId) -> Toast.makeText(getActivity(), "Added", Toast.LENGTH_LONG).show(),
                        throwable -> Log.e("GetFailed", "createOrder: Cannot add order", throwable)));
    }

    @Override
    public void onStop() {
        super.onStop();

        // clear all the subscriptions
        mDisposable.clear();
    }
}