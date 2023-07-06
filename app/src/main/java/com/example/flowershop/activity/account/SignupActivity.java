package com.example.flowershop.activity.account;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flowershop.FlowerDatabase;
import com.example.flowershop.R;
import com.example.flowershop.activity.main.MainActivity;
import com.example.flowershop.model.User;
import com.example.flowershop.util.UserHelper;

import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SignupActivity extends AppCompatActivity {
    private final CompositeDisposable mDisposable = new CompositeDisposable();
    EditText username, password, confirm;
    Button signUp, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Objects.requireNonNull(getSupportActionBar()).hide();
        TextView toSignIn = findViewById(R.id.toSignIn);
        back = findViewById(R.id.backBtn);
        signUp = findViewById(R.id.btnSignUp);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        confirm = findViewById(R.id.confirm);

        toSignIn.setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        back.setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        signUp.setOnClickListener(v -> {
            if (!password.getText().toString().equals(confirm.getText().toString())) {
                Toast.makeText(SignupActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();
            } else {
                User newUser = User.builder().username(username.getText().toString()).password(password.getText().toString()).build();
                mDisposable.add(FlowerDatabase
                        .getInstance(SignupActivity.this).userDao().register(newUser)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe((user) -> {
                            Toast.makeText(SignupActivity.this, "Register successful", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }, throwable -> {
                            Log.e("GetFailed", "Register: User already exist", throwable);
                            Toast.makeText(SignupActivity.this, "User already exist", Toast.LENGTH_LONG).show();
                        }));
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();

        // clear all the subscriptions
        mDisposable.clear();
    }
}