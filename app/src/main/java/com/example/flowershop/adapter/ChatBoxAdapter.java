package com.example.flowershop.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flowershop.FlowerDatabase;
import com.example.flowershop.R;
import com.example.flowershop.database.FirebaseDb;
import com.example.flowershop.model.Message;
import com.example.flowershop.model.UserRole;
import com.example.flowershop.util.Formatter;
import com.example.flowershop.util.UserHelper;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ChatBoxAdapter extends FirebaseRecyclerAdapter<Message, ChatBoxAdapter.ViewHolder> {
    private final CompositeDisposable mDisposable = new CompositeDisposable();
    private final Context context;
    private int userId;

    public ChatBoxAdapter(LifecycleOwner parent, Context context, int userId) {
        super(new FirebaseRecyclerOptions
                .Builder<Message>()
                .setQuery(FirebaseDb.getInstance().messageDao().getMessageQueryByUserId(userId),
                        Message.class)
                .setLifecycleOwner(parent)
                .build());
        this.context = context;
        this.userId = userId;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Message model) {
        // TODO: @tmquan2002 set data
        mDisposable.add(FlowerDatabase.getInstance(context).userDao().getById(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((user) -> {
                            if (UserHelper.getAuthUser().getRole() == UserRole.USER) {
                                if (model.isAdmin()) {
                                    holder.username_time_other.setText(String.format("%s - %s", Formatter.time(model.getTimestamp()), "admin"));
                                    holder.message_other.setText(model.getContent());
                                    holder.linear_self.setVisibility(View.GONE);
                                } else {
                                    holder.username_time_self.setText(String.format("%s - %s", Formatter.time(model.getTimestamp()), user.getUsername()));
                                    holder.message_self.setText(model.getContent());
                                    holder.linear_other.setVisibility(View.GONE);
                                }
                            }
                            if (UserHelper.getAuthUser().getRole() == UserRole.ADMIN) {
                                if (model.isAdmin()) {
                                    holder.username_time_self.setText(String.format("%s - %s", Formatter.time(model.getTimestamp()), "admin"));
                                    holder.message_self.setText(model.getContent());
                                    holder.linear_other.setVisibility(View.GONE);
                                } else {
                                    holder.username_time_other.setText(String.format("%s - %s", Formatter.time(model.getTimestamp()), user.getUsername()));
                                    holder.message_other.setText(model.getContent());
                                    holder.linear_self.setVisibility(View.GONE);
                                }
                            }
                        },
                        throwable -> Log.e("GetFailed", "getById: Cannot get the user", throwable)));
        holder.itemView.setOnClickListener(v -> {
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // TODO: @tmquan2002 inflate message view

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_chat, parent, false);

        return new ChatBoxAdapter.ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView username_time_self, message_self;
        TextView username_time_other, message_other;

        LinearLayout linear_self, linear_other;

        public ViewHolder(View itemView) {
            super(itemView);
            // TODO: @tmquan2002 find view by id
            username_time_self = itemView.findViewById(R.id.username_time_self);
            message_self = itemView.findViewById(R.id.message_self);
            username_time_other = itemView.findViewById(R.id.username_time_other);
            message_other = itemView.findViewById(R.id.message_other);
            linear_self = itemView.findViewById(R.id.linear_self);
            linear_other = itemView.findViewById(R.id.linear_other);
        }
    }
}
