package com.example.flowershop.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flowershop.FlowerDatabase;
import com.example.flowershop.R;
import com.example.flowershop.activity.admin.NoAnimationLinearLayoutManager;
import com.example.flowershop.adapter.ChatBoxAdapter;
import com.example.flowershop.database.FirebaseDb;
import com.example.flowershop.model.Message;
import com.example.flowershop.model.UserRole;
import com.example.flowershop.util.UserHelper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ChatBoxActivity extends AppCompatActivity {
    private final CompositeDisposable mDisposable = new CompositeDisposable();
    ChatBoxAdapter adapter;
    Button back, send;
    TextView messageSend, userNameTitle;
    DatabaseReference base;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);

        back = findViewById(R.id.backChatBtn);
        back.setOnClickListener(v -> finish());
        send = findViewById(R.id.send);
        messageSend = findViewById(R.id.type_message_block);
        userNameTitle = findViewById(R.id.username_title);

        rv = findViewById(R.id.recyclerView);
        base = FirebaseDatabase.getInstance().getReference();
        if (UserHelper.getAuthUser().getRole() == UserRole.USER) {
            userNameTitle.setText("admin");
            adapter = new ChatBoxAdapter(this, this, UserHelper.getAuthUser().getId());
        } else {
            Intent intent = getIntent();
            int user_id = intent.getIntExtra("user_id", 2);
            mDisposable.add(FlowerDatabase.getInstance(ChatBoxActivity.this).userDao().getById(user_id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe((user) -> userNameTitle.setText(user.getUsername()),
                            throwable -> Log.e("GetFailed", "getById: Cannot get the user", throwable)));
            adapter = new ChatBoxAdapter(this, this, user_id);
        }
        rv.setAdapter(adapter);
        rv.setLayoutManager(new NoAnimationLinearLayoutManager(this));

        send.setOnClickListener(v -> {
            String messageText = messageSend.getText().toString().trim();
            if (messageText.isEmpty())
                return;

            Message message;
            if (UserHelper.getAuthUser().getRole() == UserRole.USER) {
                message = new Message(messageText, false);
                FirebaseDb.getInstance().messageDao().sendMessage(UserHelper.getAuthUser().getId(), message);
            } else {
                message = new Message(messageText, true);
                Intent intent = getIntent();
                int user_id = intent.getIntExtra("user_id", 2);
                FirebaseDb.getInstance().messageDao().sendMessage(user_id, message);
            }
            messageSend.setText("");
        });
    }
}