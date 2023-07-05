package com.example.flowershop.activity.main;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.flowershop.FlowerDatabase;
import com.example.flowershop.R;
import com.example.flowershop.model.Cart;
import com.example.flowershop.model.Flower;
import com.example.flowershop.util.UserHelper;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DetailFragment extends Fragment {
    static Flower currentFlower;
    private final CompositeDisposable mDisposable = new CompositeDisposable();
    int currentAmountChoose = 0;
    Context context;

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance(Flower flower) {
        //Get flower details from item and add hto args bundle
        currentFlower = flower;
        return new DetailFragment();
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
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        TextView name = view.findViewById(R.id.flower_name);
        TextView description = view.findViewById(R.id.flower_description);
        TextView amount = view.findViewById(R.id.flower_amount);
        TextView price = view.findViewById(R.id.flower_price);
        TextView total = view.findViewById(R.id.total);
        ImageView imageView = view.findViewById(R.id.flower_image);
        EditText amountChoose = view.findViewById(R.id.choose_amount);

        Button plus = view.findViewById(R.id.plus);
        Button minus = view.findViewById(R.id.minus);
        Button addToCart = view.findViewById(R.id.btnOrder);

        //On Click plus and minus
        plus.setOnClickListener(v -> {
            if (currentAmountChoose < currentFlower.getAmount()) {
                currentAmountChoose = currentAmountChoose + 1;
                amountChoose.setText(String.valueOf(currentAmountChoose));
            }
        });
        minus.setOnClickListener(v -> {
            if (currentAmountChoose > 0) {
                currentAmountChoose = currentAmountChoose - 1;
                amountChoose.setText(String.valueOf(currentAmountChoose));
            }
        });

        //On Edit Text Change
        amountChoose.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    currentAmountChoose = Integer.parseInt(s.toString());
                } catch (NumberFormatException ex) {
                    currentAmountChoose = 0;
                }
                if (currentFlower != null) {
                    total.setText(String.format("%s VND", currentFlower.getPrice() * currentAmountChoose));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //Button AddToCart
        addToCart.setOnClickListener(v -> {
            if (currentAmountChoose > currentFlower.getAmount()) {
                Toast.makeText(getActivity(), "Shop doesn't have enough product", Toast.LENGTH_LONG).show();
            } else if (currentAmountChoose == 0) {
                Toast.makeText(getActivity(), "Empty amount chosen", Toast.LENGTH_LONG).show();
            } else if (UserHelper.getAuthUser() == null) {
                addToCart.setEnabled(false);
                Toast.makeText(getActivity(), "Please login first", Toast.LENGTH_LONG).show();
            } else {
                addToCart();
                Toast.makeText(getActivity(), "Added", Toast.LENGTH_LONG).show();
            }
        });

        //Get flower details and set to text view
        if (currentFlower != null) {
            name.setText(currentFlower.getName());
            description.setText(currentFlower.getDescription());
            amount.setText(String.format("Amount: %s", currentFlower.getAmount()));
            price.setText(String.format("%s VND", currentFlower.getPrice()));
            Glide.with(this).load(currentFlower.getImageUrl()).into(imageView);
            total.setText(String.format("%s VND", currentFlower.getPrice() * currentAmountChoose));
        }
        return view;
    }

    private void addToCart() {
        Cart cart = new Cart(UserHelper.getAuthUser().getId(), currentFlower.getId(), currentAmountChoose);
        mDisposable.add(FlowerDatabase.getInstance(context).cartDao().add(cart)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Toast.makeText(getActivity(), "Added", Toast.LENGTH_LONG).show(),
                        throwable -> Log.e("GetFailed", "getFlower: Cannot get the list", throwable)));
    }
}