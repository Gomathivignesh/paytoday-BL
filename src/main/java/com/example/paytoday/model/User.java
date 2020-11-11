package com.example.paytoday.model;


import com.example.paytoday.Util.RetailerStatus;
import com.example.paytoday.Util.UserRole;
import com.example.paytoday.Util.UserType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name="user")
public class User extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 123456789L;


    @Column(name="username")
    private String username;

    @Column(name="status")
    private int retailerStatus;

    @Column(name="keycloak_id")
    private String keycloak_id;

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


    @Column(name = "parent_id")
    private Long parentId;

    @Transient
    private String firstName;
    @Transient
    private String lastName;
    @Transient
    private String email;
    @Transient
    private String password;
    @Transient
    private String mobile;
    @Transient
    private String parentEmail;
    @Transient
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKeycloak_id() {
        return keycloak_id;
    }

    public void setKeycloak_id(String keycloak_id) {
        this.keycloak_id = keycloak_id;
    }

    public String getParentEmail() {
        return parentEmail;
    }

    public void setParentEmail(String parentEmail) {
        this.parentEmail = parentEmail;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
