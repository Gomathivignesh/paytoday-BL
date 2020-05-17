package com.example.paytoday.Util;

public enum UserType {

    RETAILER(1),
    ADMIN(2);

    private int value;

    UserType(int i) {
        this.value = i;
    }

    public int getValue() {
        return value;
    }
}
