package com.example.paytoday.data;

import java.math.BigDecimal;
import java.util.List;

public class UserData {

    private String username;
    private String UserType;
    private String UserStatus;
    private BigDecimal WalletBalance;

    List<UserData> retailerData;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    public String getUserStatus() {
        return UserStatus;
    }

    public void setUserStatus(String userStatus) {
        UserStatus = userStatus;
    }

    public BigDecimal getWalletBalance() {
        return WalletBalance;
    }

    public void setWalletBalance(BigDecimal walletBalance) {
        WalletBalance = walletBalance;
    }

    public List<UserData> getRetailerData() {
        return retailerData;
    }

    public void setRetailerData(List<UserData> retailerData) {
        this.retailerData = retailerData;
    }
}
