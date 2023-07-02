package com.example.flowershop.account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.flowershop.R;
import com.example.flowershop.main.MainActivity;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        TextView toSignIn = findViewById(R.id.toSignIn);
        Button back = findViewById(R.id.btnBack);

        toSignIn.setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        back.setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}