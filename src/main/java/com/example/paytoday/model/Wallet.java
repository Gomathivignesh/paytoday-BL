package com.example.paytoday.model;

import com.example.paytoday.Util.WalletStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;


@Entity
@Table(name="wallet")
public class Wallet extends BaseEntity implements Serializable {


    @Column(name="amount")
    private BigDecimal amount;

    @Column(name = "transaction_type")
    private String transactionType;

    @Column(name = "transfer_type")
    private Integer transferType;

    @Column(name = "user_id")
    private String user_id;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "status")
    private Integer status;

    @Column(name="reference")
    private String reference;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Integer getTransferType() {
        return transferType;
    }

    public void setTransferType(Integer transferType) {
        this.transferType = transferType;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
