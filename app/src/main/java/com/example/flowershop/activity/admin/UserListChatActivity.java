package com.example.flowershop.activity.admin;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flowershop.R;
import com.example.flowershop.adapter.UserAdapter;
import com.example.flowershop.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserListChatActivity extends AppCompatActivity {

    List<User> userList = Collections.emptyList();
    RecyclerView rvUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list_chat);

        rvUser = findViewById(R.id.rvUser);

        UserAdapter adapter = new UserAdapter(userList, this);
        rvUser.setAdapter(adapter);
        rvUser.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_bar, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search).setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(@NonNull MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(@NonNull MenuItem item) {
                UserAdapter adapter = new UserAdapter(userList, UserListChatActivity.this);
                rvUser.setAdapter(adapter);
                rvUser.setLayoutManager(new LinearLayoutManager(UserListChatActivity.this));
                return true;
            }
        });
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query == null || query.trim().isEmpty()) {
                    UserAdapter adapter = new UserAdapter(userList, UserListChatActivity.this);
                    rvUser.setAdapter(adapter);
                    rvUser.setLayoutManager(new LinearLayoutManager(UserListChatActivity.this));
                } else {
                    List<User> filteredList = new ArrayList<>();
                    for (User user : userList) {
                        if (user.getUsername().toLowerCase().contains(query.toLowerCase())) {
                            filteredList.add(user);
                        }
                    }
                    UserAdapter adapter = new UserAdapter(userList, UserListChatActivity.this);
                    rvUser.setAdapter(adapter);
                    rvUser.setLayoutManager(new LinearLayoutManager(UserListChatActivity.this));
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

        });
        return super.onCreateOptionsMenu(menu);
    }
}