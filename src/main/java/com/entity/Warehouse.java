package com.entity;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "warehouse")
@DynamicUpdate
public class Warehouse extends BaseAbstractEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    // id a product is some liquid, then it needs to be BigDecimal
    @Column(name = "sold", nullable = false)
    private int sold;

    @Column(name = "available", nullable = false)
    private int available;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "creation_date", nullable = false, updatable = false)
    private LocalDate creationDate;

    @Column(name = "last_modified", nullable = false)
    private LocalDate lastModified;

    @Column(name = "last_sale_date")
    private LocalDate lastSaleDate;

    public Warehouse()
    {
        this.creationDate = LocalDate.now();
        this.available = 0;
        this.sold = 0;
    }

    public int getSold() {
        return sold;
    }

    public int getAvailable() {
        return available;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public LocalDate getLastSaleDate() {
        return lastSaleDate;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public void setLastModified(LocalDate lastModified) {
        this.lastModified = lastModified;
    }
}
