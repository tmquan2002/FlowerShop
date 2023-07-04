package com.example.flowershop.activity.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.flowershop.R;
import com.example.flowershop.model.Flower;

public class DetailFragment extends Fragment {
    int amountValue = 0;

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance(Flower flower) {
        DetailFragment detailFragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString("Name", flower.getName());
        args.putString("Description", flower.getDescription());
        args.putInt("Amount", flower.getAmount());
        args.putDouble("Price", flower.getPrice());
        args.putString("Image", flower.getImageUrl());
        detailFragment.setArguments(args);
        return detailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        TextView name = view.findViewById(R.id.flower_name);
        TextView description = view.findViewById(R.id.flower_description);
        TextView amount = view.findViewById(R.id.flower_amount);
        TextView price = view.findViewById(R.id.flower_price);
        TextView total = view.findViewById(R.id.total);
        ImageView imageView = view.findViewById(R.id.flower_image);
        EditText amountChoose = view.findViewById(R.id.choose_amount);
        //On Edit Text Change
        amountChoose.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    amountValue = Integer.parseInt(s.toString());
                } catch (NumberFormatException ex) {
                    amountValue = 0;
                }
                if (getArguments() != null) {
                    total.setText(String.format("%s VND", getArguments().getDouble("Price") * amountValue));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if (getArguments() != null) {
            name.setText(getArguments().getString("Name"));
            description.setText(getArguments().getString("Description"));
            amount.setText(String.format("Amount: %s", getArguments().getInt("Amount")));
            price.setText(String.format("%s VND", getArguments().getDouble("Price")));
            Glide.with(this).load(getArguments().getString("Image")).into(imageView);
            total.setText(String.format("%s VND", getArguments().getDouble("Price") * amountValue));
        }
        return view;
    }
}