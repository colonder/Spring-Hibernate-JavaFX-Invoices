package com.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "templates")
@Getter
@Setter
@NoArgsConstructor
public class Templates {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public Templates(Customer customer, Product product) {
        this.customer = customer;
        this.product = product;
        this.quantity = BigDecimal.ZERO;
    }
}
