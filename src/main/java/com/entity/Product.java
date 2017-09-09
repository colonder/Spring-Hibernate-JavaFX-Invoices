package com.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product extends BaseAbstractEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "unit")
    private String unit;

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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getNetPrice() {
        return netPrice;
    }

    public void setNetPrice(BigDecimal netPrice) {
        this.netPrice = netPrice;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public boolean isOnlineSale() {
        return onlineSale;
    }

    public void setOnlineSale(boolean onlineSale) {
        this.onlineSale = onlineSale;
    }

    public boolean isService() {
        return isService;
    }

    public void setService(boolean service) {
        isService = service;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }
}
