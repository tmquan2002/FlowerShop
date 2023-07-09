package com.example.flowershop.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flowershop.R;
import com.example.flowershop.activity.admin.NoAnimationLinearLayoutManager;
import com.example.flowershop.adapter.ChatBoxAdapter;
import com.example.flowershop.model.UserRole;
import com.example.flowershop.util.UserHelper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatBoxActivity extends AppCompatActivity {

    ChatBoxAdapter adapter;
    Button back;
    DatabaseReference base;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);

        back = findViewById(R.id.backChatBtn);
        back.setOnClickListener(v -> finish());

        rv = findViewById(R.id.recyclerView);
        base = FirebaseDatabase.getInstance().getReference();
        if (UserHelper.getAuthUser().getRole() == UserRole.USER) {
            adapter = new ChatBoxAdapter(this, this, UserHelper.getAuthUser().getId());
        } else {
            Intent intent = getIntent();
            int user_id = intent.getIntExtra("user_id", 2);
            adapter = new ChatBoxAdapter(this, this, user_id);
        }
        rv.setAdapter(adapter);
        rv.setLayoutManager(new NoAnimationLinearLayoutManager(this));
    }
}