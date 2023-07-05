package com.example.flowershop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flowershop.R;
import com.example.flowershop.activity.main.DetailFragment;
import com.example.flowershop.model.relation.CartAndFlower;

import java.util.Collections;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    Context context;
    private List<CartAndFlower> list = Collections.emptyList();

    public CartAdapter(List<CartAndFlower> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.cart_items, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartAndFlower cartAndFlower = list.get(position);
        holder.itemName.setText(cartAndFlower.getFlower().getName());
        holder.itemAmount.setText(String.format("Amount: %s", cartAndFlower.getCart().getAmount()));
        Glide.with(context).load(cartAndFlower.getFlower().getImageUrl()).into(holder.imageView);
        holder.middleLinear.setOnClickListener(v -> {
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            DetailFragment detailFragment = DetailFragment.newInstance(cartAndFlower.getFlower());
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.navBody, detailFragment).addToBackStack(null).commit();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, itemAmount;
        ImageView imageView, edit, delete;
        LinearLayout middleLinear;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemAddress);
            itemAmount = itemView.findViewById(R.id.itemPhone);
            imageView = itemView.findViewById(R.id.itemImage);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
            middleLinear = itemView.findViewById(R.id.middleLinear);
        }
    }
}
