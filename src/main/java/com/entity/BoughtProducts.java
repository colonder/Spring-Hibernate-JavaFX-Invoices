package com.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "bought_products")
public class BoughtProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "net_value", nullable = false)
    private BigDecimal netValue;

    @Column(name = "tax_value", nullable = false)
    private BigDecimal taxValue;

    @Column(name = "gross_value", nullable = false)
    private BigDecimal grossValue;

    @Column(name = "sell_date", nullable = false)
    private LocalDate sellDate;

    @ManyToOne
    @JoinColumn(name = "invoice_id", referencedColumnName = "id")
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;
}
