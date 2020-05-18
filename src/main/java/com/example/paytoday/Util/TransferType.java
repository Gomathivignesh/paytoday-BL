package com.example.paytoday.Util;

public enum TransferType {

    DIRECT_PAYEMNT(1),
    NETBANKING(2),
    CHECKBOOK(3);

    TransferType(int i){
        this.id = i;

    }
    private int id;

    public int getId() {
        return id;
    }
}
