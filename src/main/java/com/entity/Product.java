package com.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "net_price", nullable = false)
    private BigDecimal netPrice;

    @Column(name = "tax_rate", nullable = false)
    private BigDecimal taxRate;

    @Column(name = "online_sale", nullable = false)
    private boolean onlineSale;

    @Column(name = "is_service", nullable = false)
    private boolean isService;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "warehouse_item_id", referencedColumnName = "id")
    private Warehouse warehouse;
}
