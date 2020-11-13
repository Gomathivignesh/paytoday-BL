package com.example.paytoday.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AEPSData {

    @JsonProperty(value="bc_f_name")
    private String name;
    @JsonProperty(value="bc_m_name")
    private String fname;
    @JsonProperty(value="bc_l_name")
    private String lname;

    @JsonProperty(value="emailid")
    private String email;
    @JsonProperty(value="phone1")
    private String phone1;
    @JsonProperty(value="phone2")
    private String phone2;
    @JsonProperty(value="bc_dob")
    private String dob;

    @JsonProperty(value="bc_state")
    private String state;
    @JsonProperty(value="bc_district")
    private String district;
    @JsonProperty(value="bc_address")
    private String address;

    @JsonProperty(value="bc_block")
    private String block;
    @JsonProperty(value="bc_city")
    private String city;
    @JsonProperty(value="bc_landmark")
    private String landmark;

    @JsonProperty(value="bc_loc")
    private String loc;
    @JsonProperty(value="bc_mohhalla")
    private String mohhalla;
    @JsonProperty(value="bc_pan")
    private String pan;
    @JsonProperty(value="bc_pincode")
    private String pin;

    @JsonProperty(value="shopname")
    private String shopname;
    @JsonProperty(value="saltkey")
    private String salt;
    @JsonProperty(value="secretkey")
    private String secret;
    @JsonProperty(value="shopType")
    private String shopType;

    @JsonProperty(value="kyc1")
    private String kyc1;
    @JsonProperty(value="kyc2")
    private String kyc2;
    @JsonProperty(value="kyc3")
    private String kyc3;
    @JsonProperty(value="kyc4")
    private String kyc4;

    @JsonProperty(value="qualification")
    private String qualification;
    @JsonProperty(value="population")
    private String population;
    @JsonProperty(value="locationType")
    private String locationType;

    @JsonProperty(value = "bc_id")
    private String id;
    @JsonProperty(value = "ip")
    private String ip;
    @JsonProperty(value = "userid")
    private String userId;
    @JsonProperty(value = "stanno")
    private String txnNo;


}
