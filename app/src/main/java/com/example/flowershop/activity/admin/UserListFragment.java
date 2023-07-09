package com.example.flowershop.activity.admin;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.ComponentActivity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.flowershop.R;
import com.example.flowershop.adapter.UserAdapter;
import com.example.flowershop.database.FirebaseDb;
import com.example.flowershop.model.User;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class UserListFragment extends Fragment {
    UserAdapter adapter;
    DatabaseReference base;
    private RecyclerView rv;
    private Context context;

    public UserListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);
        rv = view.findViewById(R.id.admin_userList);
        base = FirebaseDatabase.getInstance().getReference();

        adapter = new UserAdapter(getActivity(), context);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(context));
        //Not a good way to fix exception but um
//        rv.setLayoutManager(new WrapContentLinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        return view;
    }
}