package com.example.paytoday.data;

import java.math.BigDecimal;
import java.util.List;

public class UserData {

    private String username;
    private String UserType;
    private String UserStatus;
    private BigDecimal WalletBalance;
    private String accessToken;
    private String refreshToken;
    private String role;

    List<UserData> retailerData;


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

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
