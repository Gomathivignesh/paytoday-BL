package com.example.paytoday.dao;

import com.example.paytoday.model.Wallet;

import java.math.BigDecimal;
import java.util.List;

public interface WalletDAO extends BaseDAO<Wallet> {

    List<Wallet> getWalletAmount(List<Long> userId);

    Wallet getWalletRequestByRef(String ref);
}
