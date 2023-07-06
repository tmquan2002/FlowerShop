package com.example.flowershop.activity.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.flowershop.R;
import com.example.flowershop.activity.account.LoginActivity;
import com.example.flowershop.activity.account.SignupActivity;
import com.example.flowershop.util.UserHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFragment(new HomeFragment());
        Objects.requireNonNull(getSupportActionBar()).setTitle("Flower Shop");
        bottomNavigationView = findViewById(R.id.bottom_nav);
        if (UserHelper.getAuthUser() == null) {
            bottomNavigationView.getMenu().clear();
            bottomNavigationView.inflateMenu(R.menu.bottom_nav);
        } else {
            bottomNavigationView.getMenu().clear();
            bottomNavigationView.inflateMenu(R.menu.bottom_nav_login);
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                Objects.requireNonNull(getSupportActionBar()).setTitle("Flower Shop");
                loadFragment(new HomeFragment());
            } else if (item.getItemId() == R.id.cart) {
                Objects.requireNonNull(getSupportActionBar()).setTitle("Your Cart");
                loadFragment(new CartFragment());
            } else if (item.getItemId() == R.id.order) {
                Objects.requireNonNull(getSupportActionBar()).setTitle("Your Order");
                loadFragment(new OrderFragment());
            } else if (item.getItemId() == R.id.logout) {
                loadFragment(new HomeFragment());
                UserHelper.logout();
                bottomNavigationView.getMenu().clear();
                bottomNavigationView.inflateMenu(R.menu.bottom_nav);
            } else if (item.getItemId() == R.id.login) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            } else if (item.getItemId() == R.id.signup) {
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
            return true;
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.navBody, fragment).commit();
    }
}