package com.example.flowershop.activity.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.flowershop.R;

public class ChatBoxActivity extends AppCompatActivity {

    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);

        back = findViewById(R.id.backChatBtn);
        back.setOnClickListener(v -> finish());
    }
}