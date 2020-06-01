package com.example.paytoday.dao.impl;

import com.example.paytoday.Util.RetailerStatus;
import com.example.paytoday.Util.UserType;
import com.example.paytoday.dao.RetailerDAO;
import com.example.paytoday.model.Retailer;
import com.google.gson.Gson;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class RetailerDAOImpl extends BaseEntityDAOImpl<Retailer> implements RetailerDAO {


    public RetailerDAOImpl() {
    }

    @Override
    public List<Retailer> getRetailerByStatus(RetailerStatus status) {

        return getSession().createCriteria(Retailer.class)
                .add(Restrictions.eq("retailerStatus",status.getValue()))
                .setProjection(Projections.projectionList()
                        .add(Projections.property("email"), "email")
                        .add(Projections.property("name"), "name")
                        .add(Projections.property("retailerStatus"), "retailerStatus")
                        .add(Projections.property("userType"), "userType"))
                .setResultTransformer(Transformers.aliasToBean(Retailer.class))
                .list();

    }

    @Override
    public Retailer getUserbyEmail(String email) {
        return (Retailer) getSession().createCriteria(Retailer.class)
                .add(Restrictions.eq("email", email))
                .uniqueResult();
    }

    @Override
    public Retailer getUserforLogin(Retailer retailer) {
        return (Retailer) getSession().createCriteria(Retailer.class)
                .add(Restrictions.eq("email", retailer.getEmail()))
                .add(Restrictions.eq("password",retailer.getPassword()))
                .uniqueResult();
    }

    @Override
    public Map<String, String> getWalletRequest() {
        Map<String,String> responseMap = new HashMap<>();


        List<Object[]> response =  getSession().createSQLQuery("SELECT r.email, \n" +
                "       r.status, \n" +
                "       r.user_type, \n" +
                "       w.amount, \n" +
                "       w.transfer_type, \n" +
                "       w.img_url, w.reference \n" +
                "FROM   retailer r \n" +
                "JOIN   wallet w \n" +
                "ON     r.id=w.user_id \n" +
                "AND    w.status = 1").list();

        response.forEach(data-> {
            responseMap.put("email", data[0].toString());
            responseMap.put("userStatus", data[1].toString());
            responseMap.put("userType", data[2].toString());
            responseMap.put("amount", data[3].toString());
            responseMap.put("TransferType", data[4].toString());
            responseMap.put("imageURL", data[5].toString());
            responseMap.put("ref", data[6].toString());

        });

        System.out.print(new Gson().toJson(responseMap));
        return responseMap;
    }
}
