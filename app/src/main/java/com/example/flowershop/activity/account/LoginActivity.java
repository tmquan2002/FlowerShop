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
import com.example.flowershop.util.UserHelper;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {
    private final CompositeDisposable mDisposable = new CompositeDisposable();
    EditText username, password;
    Button signIn, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView toSignUp = findViewById(R.id.toSignUp);
        back = findViewById(R.id.backBtn);
        signIn = findViewById(R.id.btnSignIn);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        toSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });

        back.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        signIn.setOnClickListener(v -> mDisposable.add(FlowerDatabase
                .getInstance(LoginActivity.this).userDao().getByUsernameAndPassword(username.getText().toString(), password.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((user) -> {
                    UserHelper.setAuthUser(user);
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_LONG).show();
                }, throwable -> {
                    Log.e("GetFailed", "getUser: Cannot get the user", throwable);
                    Toast.makeText(LoginActivity.this, "Wrong username or password", Toast.LENGTH_LONG).show();
                })));
    }
}