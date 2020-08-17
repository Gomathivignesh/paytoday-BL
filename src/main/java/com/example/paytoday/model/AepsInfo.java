package com.example.paytoday.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name ="aeps_details")
@Data
public class AepsInfo extends BaseEntity {

    private static final long serialVersionUID = 123456789L;

    @Column(name="aeps_id")
    private String aepsId;
    @Column(name="status")
    private String status;
    @Column(name="message")
    private String message;

}
