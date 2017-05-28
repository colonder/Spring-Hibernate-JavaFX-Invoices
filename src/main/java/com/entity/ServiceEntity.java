package com.entity;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "uslugi")
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

    @Transient
    private SimpleStringProperty serviceNameProp;
    @Transient
    private SimpleStringProperty symbolProp;
    @Transient
    private SimpleStringProperty unitProp;
    @Transient
    private SimpleDoubleProperty netUnitPriceProp;
    @Transient
    private SimpleIntegerProperty vatProp;

    public ServiceEntity()
    {
        serviceNameProp = new SimpleStringProperty(serviceName);
        symbolProp = new SimpleStringProperty(symbol);
        unitProp = new SimpleStringProperty(unit);
        netUnitPriceProp = new SimpleDoubleProperty(netUnitPrice.doubleValue());
        vatProp = new SimpleIntegerProperty(vatTaxRate);
    }

    public ServiceEntity(String serviceName, String symbol, String unit, BigDecimal netUnitPrice, int vatTaxRate) {
        this.serviceName = serviceName;
        this.symbol = symbol;
        this.unit = unit;
        this.netUnitPrice = netUnitPrice;
        this.vatTaxRate = vatTaxRate;
    }

    public String getServiceNameProp() {
        return serviceNameProp.get();
    }

    public SimpleStringProperty serviceNamePropProperty() {
        return serviceNameProp;
    }

    public void setServiceNameProp(String serviceNameProp) {
        this.serviceNameProp.set(serviceNameProp);
    }

    public String getSymbolProp() {
        return symbolProp.get();
    }

    public SimpleStringProperty symbolPropProperty() {
        return symbolProp;
    }

    public void setSymbolProp(String symbolProp) {
        this.symbolProp.set(symbolProp);
    }

    public String getUnitProp() {
        return unitProp.get();
    }

    public SimpleStringProperty unitPropProperty() {
        return unitProp;
    }

    public void setUnitProp(String unitProp) {
        this.unitProp.set(unitProp);
    }

    public double getNetUnitPriceProp() {
        return netUnitPriceProp.get();
    }

    public SimpleDoubleProperty netUnitPricePropProperty() {
        return netUnitPriceProp;
    }

    public void setNetUnitPriceProp(double netUnitPriceProp) {
        this.netUnitPriceProp.set(netUnitPriceProp);
    }

    public int getVatProp() {
        return vatProp.get();
    }

    public SimpleIntegerProperty vatPropProperty() {
        return vatProp;
    }

    public void setVatProp(int vatProp) {
        this.vatProp.set(vatProp);
    }

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
}
