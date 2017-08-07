package com.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "warehouse")
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    // id a product is some liquid, then it needs to be BigDecimal
    @Column(name = "sold", nullable = false)
    private int sold;

    @Column(name = "available", nullable = false)
    private int available;

    @Column(name = "product_code", nullable = true)
    private String productCode;

    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    @Column(name = "last_sell_date", nullable = true)
    private Date lastSellDate;
}
