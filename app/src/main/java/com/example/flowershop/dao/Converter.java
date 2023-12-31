package com.example.flowershop.dao;

import androidx.room.TypeConverter;

import com.example.flowershop.model.OrderStatus;
import com.example.flowershop.model.UserRole;

import java.util.Date;

public class Converter {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static String fromOrderStatus(OrderStatus value) {
        return value == null ? null : value.name();
    }

    @TypeConverter
    public static OrderStatus toOrderStatus(String value) {
        return value == null ? null : OrderStatus.valueOf(value);
    }

    @TypeConverter
    public static String fromUserRole(UserRole value) {
        return value == null ? null : value.name();
    }

    @TypeConverter
    public static UserRole toUserRole(String value) {
        return value == null ? null : UserRole.valueOf(value);
    }
}
