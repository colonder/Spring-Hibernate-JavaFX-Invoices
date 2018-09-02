package com.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "templates")
@Getter
@Setter
public class Templates {

    @Id
    @Column(name = "ID")
    private int id;

    @Basic
    @Column(name = "QUANTITY")
    private BigDecimal quantity;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
}
