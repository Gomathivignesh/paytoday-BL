package com.example.paytoday.dao.impl;


import com.example.paytoday.Util.QueryUtil;
import com.example.paytoday.Util.UserType;
import com.example.paytoday.dao.UserDAO;
import com.example.paytoday.model.User;
import com.google.gson.Gson;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class UserEntityDAOImpl extends BaseEntityDAOImpl<User> implements UserDAO {

    public UserEntityDAOImpl() { }


    @Override
    public User getUserbyName(String username) {
        return (User) getSession().createCriteria(User.class)
                .add(Restrictions.eq("username", username))
                .uniqueResult();
    }

    @Override
    public User getUserbyEmail(String email) {
        return (User) getSession().createCriteria(User.class)
                .add(Restrictions.eq("email", email))
                .uniqueResult();
    }

    @Override
    public List<Map<String, String>> getWalletRequest(String approver) {
        List<Map<String,String>> responseMap = new ArrayList<>();

        String sql= approver.equals(UserType.ADMIN.name()) ? QueryUtil.WALLET_QUERY + " and w.approver_id = -1" : QueryUtil.WALLET_QUERY + " and w.approver_id = " +approver;

        List<Object[]> response =  getSession().createSQLQuery(sql).list();

        response.forEach(data-> {
            Map<String,String> dataMap = new HashMap<>();
            dataMap.put("email", data[0].toString());
            dataMap.put("userStatus", data[1].toString());
            dataMap.put("userType", data[2].toString());
            dataMap.put("amount", data[3].toString());
            dataMap.put("TransferType", data[4].toString());
            dataMap.put("imageURL", data[5].toString());
            dataMap.put("ref", data[6].toString()+ '-' +data[7].toString());
            responseMap.add(dataMap);

        });

        System.out.print(new Gson().toJson(responseMap));
        return responseMap;
    }




}
