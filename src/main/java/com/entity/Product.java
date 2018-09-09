package com.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Product
{
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "unit", nullable = false)
    private String unit;

    @Column(name = "unit_net_price", nullable = false)
    private BigDecimal unitNetPrice;

    @Column(name = "vat_rate", nullable = false)
    private BigDecimal vatRate;

    @Column(name = "per_month", nullable = false)
    private Boolean perMonth;

    @OneToMany(mappedBy = "product")
    private List<Templates> templates;
}
