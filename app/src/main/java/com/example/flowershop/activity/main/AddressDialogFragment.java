package com.example.flowershop.activity.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.flowershop.R;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class AddressDialogFragment extends DialogFragment {

    public AddressDialogFragment() {
        // Required empty public constructor
    }

    public static AddressDialogFragment newInstance() {
        return new AddressDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.address_dialog, container, false);

        Button confirm = view.findViewById(R.id.btnConfirm);
        Button cancel = view.findViewById(R.id.btnCancel);

        confirm.setOnClickListener(v -> {
            //Get data from dialog
            EditText address = view.findViewById(R.id.editDialogAddress);
            EditText phone = view.findViewById(R.id.editDialogPhone);

            // Send the data back to the hosting fragment
            Fragment targetFragment = getTargetFragment();
            if (targetFragment instanceof DialogListener) {
                ((DialogListener) targetFragment).onDataReceived(address.getText().toString(), phone.getText().toString());
            }

            dismiss();
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }

    public interface DialogListener {
        void onDataReceived(String address, String phone);
    }
}