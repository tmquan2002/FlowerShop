package com.example.flowershop.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flowershop.FlowerDatabase;
import com.example.flowershop.R;
import com.example.flowershop.activity.main.ChatBoxActivity;
import com.example.flowershop.database.FirebaseDb;
import com.example.flowershop.model.ChatUser;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserAdapter extends FirebaseRecyclerAdapter<ChatUser, UserAdapter.ViewHolder> {
    private final CompositeDisposable mDisposable = new CompositeDisposable();
    private Context context;

    public UserAdapter(LifecycleOwner parent, Context context) {
        super(new FirebaseRecyclerOptions
                .Builder<ChatUser>()
                .setQuery(FirebaseDb.getInstance().messageDao().getChatUserQuery(),
                        ChatUser.class)
                .setLifecycleOwner(parent)
                .build());
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position, @NonNull ChatUser model) {
        mDisposable.add(FlowerDatabase.getInstance(context).userDao().getById(model.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((user) -> {
                            holder.userName.setText(user.getUsername());
                        },
                        throwable -> Log.e("GetFailed", "getById: Cannot get the user", throwable)));
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ChatBoxActivity.class);
            intent.putExtra("user_id", model.getUserId());
            context.startActivity(intent);
        });
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_user, parent, false);

        return new UserAdapter.ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView userName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userName);
        }
    }
}
