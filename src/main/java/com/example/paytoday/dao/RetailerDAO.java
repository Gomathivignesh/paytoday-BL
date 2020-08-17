package com.example.paytoday.dao;

import com.example.paytoday.Util.RetailerStatus;
import com.example.paytoday.Util.UserType;
import com.example.paytoday.model.Retailer;
import com.example.paytoday.model.Wallet;


import java.util.List;
import java.util.Map;

public interface RetailerDAO extends BaseDAO<Retailer> {

    List<Retailer> getRetailerByStatus(RetailerStatus status);

    Retailer getUserbyEmail(String email);

    Retailer getUserforLogin(Retailer retailer);

    List<Map<String,String>> getWalletRequest(String approver);

    List<Retailer> getRetailerByDistributor(Long distributorId);




}
