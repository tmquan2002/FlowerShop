package com.example.flowershop.activity.admin;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;

public class NoAnimationLinearLayoutManager extends LinearLayoutManager {
    public NoAnimationLinearLayoutManager(Context context) {
        super(context);
    }

    @Override
    public boolean supportsPredictiveItemAnimations() {
        return false;
    }
}
