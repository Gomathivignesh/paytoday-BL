package com.example.paytoday.Util;

public enum WalletStatus {
    INITIATED(1),
    APPROVED(2),
    REJECTED(3);

    private int value;

    WalletStatus(int i) {
        this.value = i;
    }

    public int getValue() {
        return value;
    }
}
