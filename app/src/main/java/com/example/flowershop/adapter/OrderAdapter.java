package com.example.flowershop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flowershop.R;
import com.example.flowershop.model.Order;

import java.util.Collections;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    Context context;
    private List<Order> list = Collections.emptyList();

    public OrderAdapter(List<Order> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.order_items, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = list.get(position);
        holder.numOrder.setText(position + 1);
        holder.id.setText(order.getId());
        holder.orderDate.setText(String.format("Order Date: %s", order.getOrderDate()));
        holder.deliverDate.setText(String.format("Deliver Date; %s", order.getDeliveryDate()));
        //TODO: Add edit and delete function
        holder.itemView.setOnClickListener(v -> {
//            AppCompatActivity activity = (AppCompatActivity) v.getContext();
//            DetailFragment detailFragment = DetailFragment.newInstance(order);
//            activity.getSupportFragmentManager().beginTransaction().replace(R.id.navBody, detailFragment).addToBackStack(null).commit();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView numOrder, id, orderDate, deliverDate, status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            numOrder = itemView.findViewById(R.id.numberOrder);
            id = itemView.findViewById(R.id.itemId);
            orderDate = itemView.findViewById(R.id.itemDate);
            deliverDate = itemView.findViewById(R.id.itemDeliver);
            status = itemView.findViewById(R.id.itemStatus);
        }
    }
}
