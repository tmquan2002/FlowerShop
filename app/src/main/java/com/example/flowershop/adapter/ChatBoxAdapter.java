package com.example.flowershop.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.activity.ComponentActivity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flowershop.database.FirebaseDb;
import com.example.flowershop.model.Message;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ChatBoxAdapter extends FirebaseRecyclerAdapter<Message, ChatBoxAdapter.ViewHolder> {
    public ChatBoxAdapter(ComponentActivity parent, int userId) {
        super(new FirebaseRecyclerOptions
                .Builder<Message>()
                .setQuery(FirebaseDb.getInstance().messageDao().getMessageQueryByUserId(userId),
                        Message.class)
                .setLifecycleOwner(parent)
                .build());
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Message model) {
        // TODO: @tmquan2002 set data
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // TODO: @tmquan2002 inflate message view
//        View view = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.item_message, parent, false);
        return null;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
            // TODO: @tmquan2002 find view by id
        }
    }
}
