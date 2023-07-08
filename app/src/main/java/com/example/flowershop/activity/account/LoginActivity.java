package com.example.flowershop.activity.account;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.flowershop.FlowerDatabase;
import com.example.flowershop.R;
import com.example.flowershop.activity.main.MainActivity;
import com.example.flowershop.util.Constant;
import com.example.flowershop.util.UserHelper;

import java.util.Objects;

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

        Objects.requireNonNull(getSupportActionBar()).hide();
        TextView toSignUp = findViewById(R.id.toSignUp);
        back = findViewById(R.id.backBtn);
        signIn = findViewById(R.id.btnSignIn);

        username = findViewById(R.id.loginUsername);
        password = findViewById(R.id.loginPassword);

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
                    notifyNotEmptyCart(user.getId());
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }, throwable -> {
                    Log.e("GetFailed", "getUser: Cannot get the user", throwable);
                    Toast.makeText(LoginActivity.this, "Wrong username or password", Toast.LENGTH_LONG).show();
                })));

        requestNotificationsPermission();
    }

    @Override
    public void onStop() {
        super.onStop();

        // clear all the subscriptions
        mDisposable.clear();
    }

    private void notifyNotEmptyCart(int userId) {
        FlowerDatabase.getInstance(this)
                .cartDao()
                .getByUserId(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((carts) -> {
                    int totalQuantity = carts.stream().mapToInt(cart -> cart.getCart().getAmount()).sum();
                    if (totalQuantity > 0) {
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, Constant.NOTIF_CHANNEL_ID)
                                .setSmallIcon(R.drawable.flower_foreground)
                                .setContentTitle(getString(R.string.notif_cart_not_empty_title))
                                .setContentText(getResources().getQuantityString(R.plurals.notif_cart_not_empty_message, totalQuantity, totalQuantity))
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        notificationManager.notify(Constant.NOTIF_CART_ID, builder.build());
                    }
                }, throwable -> Log.e("GetFailed", "getCart: Cannot get the cart", throwable));
    }

    private void requestNotificationsPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
        }
    }
}