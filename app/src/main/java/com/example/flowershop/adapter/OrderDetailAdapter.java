package com.example.flowershop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flowershop.R;
import com.example.flowershop.model.relation.OrderDetailsAndFlower;

import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder> {
    Context context;
    private final List<OrderDetailsAndFlower> list;

    public OrderDetailAdapter(List<OrderDetailsAndFlower> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.item_order_detail_and_flower, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderDetailsAndFlower orderDetailsAndFlower = list.get(position);
        holder.flowerName.setText(orderDetailsAndFlower.getFlower().getName());
        holder.amount.setText(String.format("Amount: %s", orderDetailsAndFlower.getOrderDetails().getAmount()));
        holder.price.setText(String.format("%s VND", orderDetailsAndFlower.getOrderDetails().getPrice()));
        Glide.with(context).load(orderDetailsAndFlower.getFlower().getImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView flowerName, amount, price;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            flowerName = itemView.findViewById(R.id.orderDetailListFlowerName);
            amount = itemView.findViewById(R.id.orderDetailListAmount);
            price = itemView.findViewById(R.id.orderDetailListPrice);
            imageView = itemView.findViewById(R.id.orderDetailListImage);
        }
    }
}
