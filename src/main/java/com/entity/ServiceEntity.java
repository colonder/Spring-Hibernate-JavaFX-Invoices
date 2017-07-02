package com.entity;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "uslugi")
@NoArgsConstructor
public class ServiceEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull(message = "To pole jest wymagane")
    @Column(name = "nazwa", nullable = false)
    private String serviceName;

    @NotNull(message = "To pole jest wymagane")
    @Column(name = "symbol_PKWIU_PKOB", nullable = false)
    private String symbol;

    @NotNull(message = "To pole jest wymagane")
    @Column(name = "jednostka", nullable = false)
    private String unit;

    @NotNull(message = "To pole jest wymagane")
    @Column(name = "cena_jednostkowa_netto", nullable = false)
    private BigDecimal netUnitPrice;

    @NotNull(message = "To pole jest wymagane")
    @Column(name = "stawka_vat", nullable = false)
    private int vatTaxRate;

    //TODO: write a trigger in database preventing from deleting service when any customer is still using it(?)

    public ServiceEntity(String serviceName, String symbol, String unit, BigDecimal netUnitPrice, int vatTaxRate) {
        this.serviceName = serviceName;
        this.symbol = symbol;
        this.unit = unit;
        this.netUnitPrice = netUnitPrice;
        this.vatTaxRate = vatTaxRate;
    }

    //region getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
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

    public BigDecimal getNetUnitPrice() {
        return netUnitPrice;
    }

    public void setNetUnitPrice(BigDecimal netUnitPrice) {
        this.netUnitPrice = netUnitPrice;
    }

    public int getVatTaxRate() {
        return vatTaxRate;
    }

    public void setVatTaxRate(int vatTaxRate) {
        this.vatTaxRate = vatTaxRate;
    }
    //endregion
}
