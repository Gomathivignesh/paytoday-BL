package com.example.paytoday.Util;

public enum UserRole {

    USER(1),
    ADMIN(2);

    private int value;

    UserRole(int i) {
        this.value = i;
    }

    public int getValue() {
        return value;
    }
}
