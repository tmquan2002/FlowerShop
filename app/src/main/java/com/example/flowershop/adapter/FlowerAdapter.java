package com.example.flowershop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flowershop.R;
import com.example.flowershop.activity.main.DetailFragment;
import com.example.flowershop.model.Flower;

import java.util.Collections;
import java.util.List;

public class FlowerAdapter extends RecyclerView.Adapter<FlowerAdapter.ViewHolder> {
    Context context;
    private List<Flower> list = Collections.emptyList();

    public FlowerAdapter(List<Flower> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.flower_items, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Flower flower = list.get(position);
        holder.itemName.setText(flower.getName());
        holder.itemDescription.setText(flower.getDescription());
        holder.price.setText(String.format("%s VND", flower.getPrice()));
        Glide.with(context).load(flower.getImageUrl()).into(holder.imageView);
        holder.itemView.setOnClickListener(v -> {
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            DetailFragment detailFragment = DetailFragment.newInstance(flower);
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.navBody, detailFragment).addToBackStack(null).commit();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView itemDescription;
        TextView price;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.flowerHomePrice);
            itemName = itemView.findViewById(R.id.flowerHomeName);
            itemDescription = itemView.findViewById(R.id.flowerHomeDescription);
            imageView = itemView.findViewById(R.id.flowerHomeImage);
        }
    }
}
