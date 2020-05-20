package com.example.paytoday.model;


import com.example.paytoday.Util.RetailerStatus;
import com.example.paytoday.Util.UserRole;
import com.example.paytoday.Util.UserType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

@Entity
@Table(name="retailer")
public class Retailer extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 123456789L;


     @Column(name="name")
     private String name;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="status")
    private int retailerStatus;

    @Column(name="allow_dmt")
    private Boolean isAllowDMT;

    @Column(name="allow_recharge", nullable= false)
    private Boolean isAllowRecharge;

    @Column(name="allow_pan", nullable= false)
    private Boolean isAllowPan;

    @Column(name="allow_aeps", nullable= false)
    private Boolean isAllowAEPS;

    @Column(name="allow_bbps", nullable= false)
    private Boolean isAllowBBPS;


    @Column(name="shopname")
    private String shopname;

    @Column(name="address")
    private String address;

    @Column(name="address1")
    private String address1;


    @Column(name="city")
    private String city;

    @Column(name="district")
    private String district;

    @Column(name="state")
    private String state;

    @Column(name="country")
    private String country;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "role")
    private int userRole;

    @Column(name = "user_type")
    private int userType;

    @Column(name = "agent_id")
    private Long agentId;

    @Transient
    private String agentEmail;

    public String getAgentEmail() {
        return agentEmail;
    }

    public void setAgentEmail(String agentEmail) {
        this.agentEmail = agentEmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRetailerStatus() {
        return retailerStatus;
    }

    public void setRetailerStatus(int retailerStatus) {
        this.retailerStatus = retailerStatus;
    }

    public Boolean getAllowDMT() {
        return isAllowDMT;
    }

    public void setAllowDMT(Boolean allowDMT) {
        isAllowDMT = allowDMT;
    }

    public Boolean getAllowRecharge() {
        return isAllowRecharge;
    }

    public void setAllowRecharge(Boolean allowRecharge) {
        isAllowRecharge = allowRecharge;
    }

    public Boolean getAllowPan() {
        return isAllowPan;
    }

    public void setAllowPan(Boolean allowPan) {
        isAllowPan = allowPan;
    }

    public Boolean getAllowAEPS() {
        return isAllowAEPS;
    }

    public void setAllowAEPS(Boolean allowAEPS) {
        isAllowAEPS = allowAEPS;
    }

    public Boolean getAllowBBPS() {
        return isAllowBBPS;
    }

    public void setAllowBBPS(Boolean allowBBPS) {
        isAllowBBPS = allowBBPS;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getUserRole() {
        return userRole;
    }

    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }
}
