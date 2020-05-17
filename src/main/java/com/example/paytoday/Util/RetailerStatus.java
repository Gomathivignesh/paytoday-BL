package com.example.paytoday.Util;

public enum RetailerStatus {

    ONBOARDED(1),
    ACTIVE(2),
    LOCKED(3),
    EXPIRED(4),
    INACTIVATE(5);

    int value;

    public int getValue() {
        return value;
    }

    RetailerStatus(int i) {
        this.value = i;
    }


}
