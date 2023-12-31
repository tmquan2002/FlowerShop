package com.example.flowershop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flowershop.R;
import com.example.flowershop.activity.main.OrderDetailFragment;
import com.example.flowershop.model.Order;
import com.example.flowershop.util.Formatter;

import java.text.DateFormat;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    final Context context;
    private final List<Order> list;

    public OrderAdapter(List<Order> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.item_order, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = list.get(position);
        holder.numOrder.setText(String.format("%s", position + 1));
        holder.id.setText(String.format("ID: %s", order.getId()));
        holder.orderDate.setText(String.format("Order Date: %s", Formatter.datetime(order.getOrderDate())));
        holder.deliverDate.setText(String.format("Deliver Date: %s", order.getDeliveryDate() == null ? "" : Formatter.datetime(order.getDeliveryDate())));
        holder.status.setText(String.format(order.getStatus().toString()));
        holder.itemView.setOnClickListener(v -> {
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            OrderDetailFragment detailFragment = OrderDetailFragment.newInstance(order.getId());
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.navBody, detailFragment).addToBackStack(null).commit();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView numOrder;
        final TextView id;
        final TextView orderDate;
        final TextView deliverDate;
        final TextView status;

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
