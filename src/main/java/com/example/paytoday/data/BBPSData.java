package com.example.paytoday.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BBPSData {

    @JsonProperty(value="securityKey")
    private String securityKey;
    @JsonProperty(value="requestby")
    private String requestBy;
    @JsonProperty(value="name")
    private String name;

    @JsonProperty(value="contactperson")
    private String contact;
    @JsonProperty(value="mobileNumber")
    private String mobile;
    @JsonProperty(value="agentshopname")
    private String shopName;


    @JsonProperty(value="businesstype")
    private String business;
    @JsonProperty(value="address1")
    private String address1;
    @JsonProperty(value="address2")
    private String address2;

    @JsonProperty(value="state")
    private String state;
    @JsonProperty(value="city")
    private String city;
    @JsonProperty(value="pincode")
    private String pincode;

    @JsonProperty(value="latitude")
    private String latitude;
    @JsonProperty(value="longitude")
    private String longitude;
    @JsonProperty(value="email")
    private String email;





}
