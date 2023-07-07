package com.example.flowershop.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flowershop.FlowerDatabase;
import com.example.flowershop.R;
import com.example.flowershop.activity.main.DetailFragment;
import com.example.flowershop.model.Cart;
import com.example.flowershop.model.relation.CartAndFlower;
import com.example.flowershop.util.UserHelper;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private final List<CartAndFlower> list;
    private final CompositeDisposable mDisposable = new CompositeDisposable();
    Context context;
    private final OnItemChangeListener cartAdapterListener;

    public CartAdapter(List<CartAndFlower> list, Context context, OnItemChangeListener cartAdapterListener) {
        this.list = list;
        this.context = context;
        this.cartAdapterListener = cartAdapterListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.item_cart, parent, false);

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
        holder.delete.setOnClickListener(v -> {
            Cart cart = Cart.builder().userId(UserHelper.getAuthUser().getId()).flowerId(cartAndFlower.getFlower().getId()).amount(cartAndFlower.getCart().getAmount()).build();
            mDisposable.add(FlowerDatabase.getInstance(context).cartDao().delete(cart)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                                Toast.makeText(context, "Deleted!", Toast.LENGTH_SHORT).show();
                                list.remove(position);
                                cartAdapterListener.getCart();
                                notifyItemRemoved(position);
                            },
                            throwable -> Log.e("GetFailed", "createOrder: Cannot add order", throwable)));
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemChangeListener {
        void getCart();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, itemAmount;
        ImageView imageView, delete;
        LinearLayout middleLinear;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemCartName);
            itemAmount = itemView.findViewById(R.id.itemCartAmount);
            imageView = itemView.findViewById(R.id.itemImage);
            delete = itemView.findViewById(R.id.delete);
            middleLinear = itemView.findViewById(R.id.middleLinear);
        }
    }
}
