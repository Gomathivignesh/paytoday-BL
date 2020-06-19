package com.example.paytoday.dao;

import com.example.paytoday.model.Wallet;

import java.math.BigDecimal;

public interface WalletDAO extends BaseDAO<Wallet> {

    BigDecimal getWalletAmount(Long userId);
}
