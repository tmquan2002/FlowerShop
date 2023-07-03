package com.example.flowershop.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.flowershop.R;
import com.example.flowershop.adapter.FlowerAdapter;
import com.example.flowershop.model.Flower;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    ArrayList<Flower> list;
    private RecyclerView rv;

    public HomeFragment() {
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView rv = view.findViewById(R.id.list);

        list = new ArrayList<>();
        list.add(new Flower(1, "Name", "Description", 12, 23.3));
        list.add(new Flower(2, "Name", "Description 2", 1, 2.3));
        list.add(new Flower(3, "Name", "Description 3", 2, 3.3));
        list.add(new Flower(4, "Name", "Description 4", 24, 2.3));
        list.add(new Flower(5, "Name", "Description 5", 3, 23.1));

        FlowerAdapter adapter = new FlowerAdapter(list, view.getContext());
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(view.getContext()));
        return view;
    }
}