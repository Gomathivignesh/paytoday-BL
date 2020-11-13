package com.example.paytoday.dao.impl;

import com.example.paytoday.dao.WalletDAO;
import com.example.paytoday.model.Wallet;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;


@Component
public class WalletDAOImpl extends BaseEntityDAOImpl<Wallet> implements WalletDAO {


    @Override
    public List<Wallet> getWalletAmount(List<Long> userIds) {

        return getSession().createCriteria(Wallet.class)
                .add(Restrictions.in("userId", userIds))
                .add(Restrictions.eq("status", 1))

                .setProjection(Projections.projectionList()
                        .add(Projections.property("createdDate"), "createdDate")
                        .add(Projections.property("amount"), "amount")
                        .add(Projections.property("transactionType"), "transactionType")
                        .add(Projections.property("transferType"), "transferType")
                        .add(Projections.property("imgUrl"), "imgUrl")
                        .add(Projections.property("reference"), "reference"))
                .setResultTransformer(Transformers.aliasToBean(Wallet.class))
                .list();
    }

    @Override
    public Wallet getWalletRequestByRef(String ref){

        return (Wallet) getSession().createCriteria(Wallet.class)
                .add(Restrictions.in("reference", ref))
                .uniqueResult();

    }
}
