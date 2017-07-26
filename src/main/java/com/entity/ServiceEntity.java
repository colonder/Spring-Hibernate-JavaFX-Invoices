package com.entity;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "uslugi")
public class ServiceEntity {
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

    public ServiceEntity() {
        createProps();
    }

    private void createProps() {
        this.serviceNameProp = new SimpleStringProperty(serviceName);
        this.symbolProp = new SimpleStringProperty(symbol);
        this.unitProp = new SimpleStringProperty(unit);
        this.netUnitPriceProp = new SimpleObjectProperty<>(netUnitPrice);
        this.vatProp = new SimpleIntegerProperty(vatTaxRate);
    }

    @PostLoad
    private void postLoad() {
        createProps();
    }

    @PreUpdate
    private void preUpdate() {
        this.serviceName = this.getServiceNameProp();
        this.symbol = this.getSymbolProp();
        this.unit = this.getUnitProp();
        this.netUnitPrice = this.getNetUnitPriceProp();
        this.vatTaxRate = this.getVatProp();
    }

    public int getId() {
        return id;
    }
    public String getServiceName() {
        return serviceName;
    }

    @Transient
    private SimpleStringProperty serviceNameProp;
    @Transient
    private SimpleStringProperty symbolProp;
    @Transient
    private SimpleStringProperty unitProp;
    @Transient
    private SimpleObjectProperty<BigDecimal> netUnitPriceProp;
    @Transient
    private SimpleIntegerProperty vatProp;

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

    public BigDecimal getNetUnitPriceProp() {
        return netUnitPriceProp.get();
    }

    public SimpleObjectProperty<BigDecimal> netUnitPricePropProperty() {
        return netUnitPriceProp;
    }

    public void setNetUnitPriceProp(BigDecimal netUnitPriceProp) {
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
}
