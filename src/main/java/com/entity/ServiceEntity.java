package com.entity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "uslugi")
@NoArgsConstructor
@Access(value = AccessType.PROPERTY)
public class ServiceEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private SimpleIntegerProperty id;

    @NotNull(message = "To pole jest wymagane")
    @Column(name = "nazwa", nullable = false)
    private SimpleStringProperty serviceName;

    @NotNull(message = "To pole jest wymagane")
    @Column(name = "symbol_PKWIU_PKOB", nullable = false)
    private SimpleStringProperty symbol;

    @NotNull(message = "To pole jest wymagane")
    @Column(name = "jednostka", nullable = false)
    private SimpleStringProperty unit;

    @NotNull(message = "To pole jest wymagane")
    @Column(name = "cena_jednostkowa_netto", nullable = false)
    private SimpleObjectProperty<BigDecimal> netUnitPrice;

    @NotNull(message = "To pole jest wymagane")
    @Column(name = "stawka_vat", nullable = false)
    private SimpleIntegerProperty vatTaxRate;

    //TODO: write a trigger in database preventing from deleting service when any customer is still using it(?)

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getServiceName() {
        return serviceName.get();
    }

    public SimpleStringProperty serviceNameProperty() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName.set(serviceName);
    }

    public String getSymbol() {
        return symbol.get();
    }

    public SimpleStringProperty symbolProperty() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol.set(symbol);
    }

    public String getUnit() {
        return unit.get();
    }

    public SimpleStringProperty unitProperty() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit.set(unit);
    }

    public BigDecimal getNetUnitPrice() {
        return netUnitPrice.get();
    }

    public SimpleObjectProperty<BigDecimal> netUnitPriceProperty() {
        return netUnitPrice;
    }

    public void setNetUnitPrice(BigDecimal netUnitPrice) {
        this.netUnitPrice.set(netUnitPrice);
    }

    public int getVatTaxRate() {
        return vatTaxRate.get();
    }

    public SimpleIntegerProperty vatTaxRateProperty() {
        return vatTaxRate;
    }

    public void setVatTaxRate(int vatTaxRate) {
        this.vatTaxRate.set(vatTaxRate);
    }
}
