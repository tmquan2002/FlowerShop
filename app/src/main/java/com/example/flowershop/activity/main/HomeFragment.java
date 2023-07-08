package com.example.flowershop.activity.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flowershop.FlowerDatabase;
import com.example.flowershop.R;
import com.example.flowershop.activity.account.LoginActivity;
import com.example.flowershop.activity.user.MapViewActivity;
import com.example.flowershop.adapter.FlowerAdapter;
import com.example.flowershop.model.Flower;
import com.example.flowershop.util.UserHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeFragment extends Fragment {
    private final CompositeDisposable mDisposable = new CompositeDisposable();
    List<Flower> list;
    FloatingActionButton add, email, map;
    TextView emailTv, mapTv;
    Boolean isAllFabVisible;
    private RecyclerView rv;
    private Context context;

    public HomeFragment() {
        // require a empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        rv = view.findViewById(R.id.list);
        add = view.findViewById(R.id.fab_add_id);
        email = view.findViewById(R.id.fab_email_id);
        map = view.findViewById(R.id.fab_map_id);
        emailTv = view.findViewById(R.id.fab_email_textview);
        mapTv = view.findViewById(R.id.fab_map_textview);

        //Flower List
        getFlower();

        //Floating buttons
        email.setVisibility(View.GONE);
        map.setVisibility(View.GONE);
        emailTv.setVisibility(View.GONE);
        mapTv.setVisibility(View.GONE);
        isAllFabVisible = false;

        add.setOnClickListener(v -> {
            if (!isAllFabVisible) {
                email.show();
                map.show();
                emailTv.setVisibility(View.VISIBLE);
                mapTv.setVisibility(View.VISIBLE);
                isAllFabVisible = true;
            } else {
                email.setVisibility(View.GONE);
                map.setVisibility(View.GONE);
                emailTv.setVisibility(View.GONE);
                mapTv.setVisibility(View.GONE);
                isAllFabVisible = false;
            }
        });

        email.setOnClickListener(v -> {
            if (UserHelper.getAuthUser() == null) {
                startActivity(new Intent(context, LoginActivity.class));
            } else {
                startActivity(new Intent(context, ChatBoxActivity.class));
            }
        });

        map.setOnClickListener(v -> {
            if (UserHelper.getAuthUser() == null) {
                startActivity(new Intent(context, LoginActivity.class));
            } else {
                startActivity(new Intent(context, MapViewActivity.class));
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.top_bar, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search).setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(@NonNull MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(@NonNull MenuItem item) {
                FlowerAdapter adapter = new FlowerAdapter(list, context);
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
                    FlowerAdapter adapter = new FlowerAdapter(list, context);
                    rv.setAdapter(adapter);
                    rv.setLayoutManager(new LinearLayoutManager(context));
                } else {
                    List<Flower> filteredList = new ArrayList<>();
                    for (Flower flower : list) {
                        if (flower.getName().toLowerCase().contains(query.toLowerCase())) {
                            filteredList.add(flower);
                        }
                    }
                    FlowerAdapter adapter = new FlowerAdapter(filteredList, context);
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

    private void getFlower() {
        mDisposable.add(FlowerDatabase.getInstance(context).flowerDao().getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((flowerList) -> {
                            list = flowerList;
                            FlowerAdapter adapter = new FlowerAdapter(list, context);
                            rv.setAdapter(adapter);
                            rv.setLayoutManager(new LinearLayoutManager(context));
                        },
                        throwable -> Log.e("GetFailed", "getFlower: Cannot get the list", throwable)));
    }

    @Override
    public void onStop() {
        super.onStop();

        // clear all the subscriptions
        mDisposable.clear();
    }
}