package com.example.flowershop.activity.main;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.flowershop.R;
import com.example.flowershop.adapter.FlowerAdapter;
import com.example.flowershop.model.Flower;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    ArrayList<Flower> list;
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
        populateList();
    }

    private void populateList() {
        list = new ArrayList<>();
        list.add(new Flower(1, "Alvin", "Description", 12, 23.3));
        list.add(new Flower(2, "Banana", "Description 2", 1, 2.3));
        list.add(new Flower(3, "Cut", "Description 3", 2, 3.3));
        list.add(new Flower(4, "Dice", "Description 4", 24, 2.3));
        list.add(new Flower(5, "Egg", "Description 5", 3, 23.1));
        list.add(new Flower(6, "Quan", "Description 5", 3, 23.1));
        list.add(new Flower(6, "Vines", "Description 5", 3, 23.1));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        rv = view.findViewById(R.id.list);
        FlowerAdapter adapter = new FlowerAdapter(list, context);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(context));
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
                    ArrayList<Flower> filteredList = new ArrayList<>();
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
}