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
    private Integer transactionType;

    @Column(name = "transfer_type")
    private String transferType;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "status")
    private Integer status;

    @Column(name="reference")
    private String reference;

    @Column(name="approver_id")
    private String approverId;

    public String getApproverId() {
        return approverId;
    }

    public void setApproverId(String approverId) {
        this.approverId = approverId;
    }

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

    public Integer getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Integer transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
