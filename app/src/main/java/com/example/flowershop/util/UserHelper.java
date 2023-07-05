package com.example.flowershop.util;

import com.example.flowershop.model.User;

public class UserHelper {
    public static User authUser;

    public static void setAuthUser(User user) {
        authUser = user;
    }

    public static User getAuthUser() {
        return authUser;
    }

    public static void logout() {
        authUser = null;
    }
}
