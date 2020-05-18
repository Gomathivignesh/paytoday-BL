package com.example.paytoday.dao;

import com.example.paytoday.Util.RetailerStatus;
import com.example.paytoday.model.Retailer;
import com.example.paytoday.model.Wallet;


import java.util.List;

public interface RetailerDAO extends BaseDAO<Retailer> {

    public List<Retailer> getRetailerByStatus(RetailerStatus status);

    Retailer getUserbyEmail(String email);

    Retailer getUserforLogin(Retailer retailer);


}
