package com.example.flowershop.activity.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.flowershop.R;
import com.example.flowershop.model.Flower;

public class DetailFragment extends Fragment {

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance(Flower flower) {
        DetailFragment detailFragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString("Name", flower.getName());
        detailFragment.setArguments(args);
        return detailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        TextView name = view.findViewById(R.id.flower_name);
        if(getArguments()!=null){
            name.setText(getArguments().getString("Name"));
        } else {
            name.setText("Flower Name");
        }
        return view;
    }
}