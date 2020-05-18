package com.example.paytoday.dao.impl;

import com.example.paytoday.Util.RetailerStatus;
import com.example.paytoday.dao.RetailerDAO;
import com.example.paytoday.model.Retailer;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Component;

import java.util.List;


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
}
