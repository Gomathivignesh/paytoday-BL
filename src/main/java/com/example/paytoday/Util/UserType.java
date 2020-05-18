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


    public static UserType getValueOf(int i){
        for (UserType data : UserType.values()) {
            if (data.getValue() == i )
                return data;
        }
        throw new IllegalArgumentException("data not found.");
    }

}
