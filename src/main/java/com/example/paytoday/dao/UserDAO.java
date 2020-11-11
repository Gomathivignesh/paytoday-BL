package com.example.paytoday.dao;


import com.example.paytoday.Util.RetailerStatus;
import com.example.paytoday.model.User;

import java.util.List;
import java.util.Map;

public interface UserDAO extends BaseDAO<User> {

//    User getUserforLogin(User user);

    User getUserbyName(String username);

    User getUserbyEmail(String email);

    // need to verify
//    List<User> getRetailerByStatus(RetailerStatus status);
//
    List<Map<String,String>> getWalletRequest(String approver);
//
//    List<User> getRetailerByDistributor(Long distributorId);



}
