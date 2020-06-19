package com.example.paytoday.dao.impl;

import com.example.paytoday.dao.WalletDAO;
import com.example.paytoday.model.Retailer;
import com.example.paytoday.model.Wallet;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
public class WalletDAOImpl extends BaseEntityDAOImpl<Wallet> implements WalletDAO {


    @Override
    public BigDecimal getWalletAmount(Long userId) {

        return (BigDecimal) getSession().createCriteria(Wallet.class)
                .add(Restrictions.eq("userId", userId.toString()))
                .setProjection(Projections.projectionList()
                        .add(Projections.property("amount")))
                .uniqueResult();
    }
}
