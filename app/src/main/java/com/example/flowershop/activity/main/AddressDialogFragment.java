package com.example.flowershop.activity.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.flowershop.R;

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
        View view = inflater.inflate(R.layout.dialog_address, container, false);

        Button confirm = view.findViewById(R.id.btnConfirm);
        Button cancel = view.findViewById(R.id.btnCancel);

        confirm.setOnClickListener(v -> {
            //Get data from dialog
            EditText address = view.findViewById(R.id.editDialogAddress);
            EditText phone = view.findViewById(R.id.editDialogPhone);

            if(address.getText().toString().matches("")||phone.getText().toString().matches("")){
                Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
            }
            // Send the data back to the hosting fragment
            Fragment targetFragment = getTargetFragment();
            if (targetFragment instanceof DialogListener) {
                ((DialogListener) targetFragment).onDataReceived(address.getText().toString(), phone.getText().toString());
            }

            dismiss();
        });

        cancel.setOnClickListener(v -> dismiss());

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public interface DialogListener {
        void onDataReceived(String address, String phone);
    }
}