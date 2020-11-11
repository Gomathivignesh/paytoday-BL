package com.example.paytoday.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BBPSResData {

    private String message;
    private String agentId;
    private String status;
    private List<Object> billerCategory;
    private List<Object> billerByCategory;
    private String customerName;
    private String TransactionId;
    private String billNo;
    private String billAmount;



}
