package com.example.flowershop.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flowershop.FlowerDatabase;
import com.example.flowershop.R;
import com.example.flowershop.model.Order;
import com.example.flowershop.model.OrderStatus;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ManageOrderAdapter extends RecyclerView.Adapter<ManageOrderAdapter.ViewHolder> {
    final Context context;
    private final List<Order> list;
    private final CompositeDisposable mDisposable = new CompositeDisposable();
    private final ManageOrderAdapter.OnStatusChangeListener onStatusChangeListener;
    public ManageOrderAdapter(Context context, List<Order> list, OnStatusChangeListener onStatusChangeListener) {
        this.context = context;
        this.list = list;
        this.onStatusChangeListener = onStatusChangeListener;
    }

    @NonNull
    @Override
    public ManageOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.item_manage_order, parent, false);

        return new ManageOrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ManageOrderAdapter.ViewHolder holder, int position) {
        Order order = list.get(position);
        holder.numOrder.setText(String.format("%s", position + 1));
        holder.itemId.setText(String.format("ID: %s", order.getId()));
        holder.orderDate.setText(String.format("Order Date: %s", DateFormat.getDateInstance(DateFormat.SHORT).format(order.getOrderDate())));
        holder.orderDelivery.setText(String.format("Deliver Date: %s", order.getDeliveryDate() == null ? "" : DateFormat.getDateInstance(DateFormat.SHORT).format(order.getDeliveryDate())));
        holder.changeStatus.setText(order.getStatus().toString());
        if (order.getStatus() == OrderStatus.CANCELLED || order.getStatus() == OrderStatus.DELIVERED) {
            holder.changeStatus.setEnabled(false);
        }
        holder.changeStatus.setOnClickListener(v -> {
            Order orderEdit = order;
            if (order.getStatus() == OrderStatus.PENDING) {
                orderEdit.setStatus(OrderStatus.DELIVERING);
            } else if (order.getStatus() == OrderStatus.DELIVERING) {
                orderEdit.setStatus(OrderStatus.DELIVERED);
                orderEdit.setDeliveryDate(new Date());
            }
            mDisposable.add(FlowerDatabase.getInstance(context).orderDao().update(orderEdit)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                                Toast.makeText(context, "Status changed successful", Toast.LENGTH_SHORT).show();
                                onStatusChangeListener.getOrder();
                                notifyItemRemoved(position);
                            },
                            throwable -> Log.e("GetFailed", "getFlower: Cannot get the list", throwable)));
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnStatusChangeListener {
        void getOrder();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView numOrder, itemId, orderDate, orderDelivery;
        Button changeStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            numOrder = itemView.findViewById(R.id.adminNumberOrder);
            itemId = itemView.findViewById(R.id.adminItemId);
            orderDate = itemView.findViewById(R.id.adminItemDate);
            orderDelivery = itemView.findViewById(R.id.adminItemDeliver);
            changeStatus = itemView.findViewById(R.id.adminStatusButton);
        }
    }
}
