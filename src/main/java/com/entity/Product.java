package com.entity;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
@DynamicUpdate
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

    @Column(name = "unit", nullable = false)
    private String unit;

    @Column(name = "gross_price", nullable = false)
    private BigDecimal grossPrice;

    @Column(name = "vat_rate", nullable = false)
    private BigDecimal vatRate;

    public void setAll(String productName, String symbol, String unit, BigDecimal netPrice, BigDecimal vatRate) {
        this.productName = productName;
        this.symbol = symbol;
        this.unit = unit;
        this.grossPrice = netPrice;
        this.vatRate = vatRate;
    }

    //region getters and setters
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

    public BigDecimal getGrossPrice() {
        return grossPrice;
    }

    public void setGrossPrice(BigDecimal grossPrice) {
        this.grossPrice = grossPrice;
    }

    public BigDecimal getVatRate() {
        return vatRate;
    }

    public void setVatRate(BigDecimal vatRate) {
        this.vatRate = vatRate;
    }
    //endregion
}
