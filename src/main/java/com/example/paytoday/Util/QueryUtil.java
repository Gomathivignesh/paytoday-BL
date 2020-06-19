package com.example.paytoday.Util;

public interface QueryUtil {

    String WALLET_QUERY = "SELECT r.email, r.status,r.user_type, w.amount, w.transfer_type, w.img_url, w.reference FROM   retailer r " +
            "JOIN wallet w ON r.id=w.user_id AND w.status = 1 ";
}
