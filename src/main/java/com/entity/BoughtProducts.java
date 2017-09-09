package com.entity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "bought_products")
public class BoughtProducts extends BaseAbstractEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name ="symbol")
    private String symbol;

    @Column(name = "unit")
    private String unit;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "tax_rate", nullable = false)
    private BigDecimal taxRate;

    @Column(name = "discount_percents")
    private int discountPercents;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "net_value", nullable = false)
    private BigDecimal netValue;

    @Column(name = "tax_value", nullable = false)
    private BigDecimal taxValue;

    @Column(name = "gross_value", nullable = false)
    private BigDecimal grossValue;

    @Column(name = "sale_date", nullable = false)
    private LocalDate saleDate;

    @ManyToOne
    @JoinColumn(name = "invoice_id", referencedColumnName = "id")
    private Invoice invoice;

    @Transient private SimpleObjectProperty<BigDecimal> priceProp;
    @Transient private SimpleObjectProperty<BigDecimal> taxRateProp;
    @Transient private SimpleIntegerProperty quantityProp;
    @Transient private SimpleObjectProperty<BigDecimal> netValProp;
    @Transient private SimpleObjectProperty<BigDecimal> taxValProp;
    @Transient private SimpleIntegerProperty discountProp;
    @Transient private SimpleObjectProperty<BigDecimal> grossValProp;

    public BoughtProducts(String productName, String symbol, BigDecimal price, BigDecimal taxRate,
                          int discountPercents, int quantity, LocalDate saleDate)
    {
        this.productName = productName;
        this.symbol = symbol;
        this.price = price;
        this.taxRate = taxRate;
        this.discountPercents = discountPercents;
        this.quantity = quantity;
        this.saleDate = saleDate;

        this.priceProp = new SimpleObjectProperty<>(price);
        this.taxRateProp = new SimpleObjectProperty<>(taxRate);
        this.quantityProp = new SimpleIntegerProperty(quantity);
        this.taxRateProp = new SimpleObjectProperty<>(taxRate);
        this.discountProp = new SimpleIntegerProperty(discountPercents);
        this.quantityProp.addListener((observable, oldValue, newValue) -> {
            this.setNetValProp(this.getPriceProp().multiply(new BigDecimal(newValue.intValue())).setScale(2,
                    BigDecimal.ROUND_HALF_DOWN));
            this.setTaxValProp(this.getNetValProp().multiply(this.getTaxRateProp())
                    .setScale(2, BigDecimal.ROUND_HALF_DOWN));
            this.setGrossValProp(this.getNetValProp().add(this.getTaxValProp())
                    .setScale(2, BigDecimal.ROUND_HALF_DOWN));

            if (this.getDiscountProp() > 0)
            {
                this.computeGrossVal();
            }
        });

        this.discountProp.addListener((observable, oldValue, newValue) -> computeGrossVal());
    }

    private void computeGrossVal()
    {
        this.setGrossValProp(this.getGrossValProp().subtract(this.getGrossValProp()
                .multiply(new BigDecimal(this.getDiscountProp())).setScale(2,
                        BigDecimal.ROUND_HALF_DOWN)));
    }

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

    public BigDecimal getPrice() {
        return price;
    }

    private void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    private void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public int getDiscountPercents() {
        return discountPercents;
    }

    private void setDiscountPercents(int discountPercents) {
        this.discountPercents = discountPercents;
    }

    public int getQuantity() {
        return quantity;
    }

    private void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getNetValue() {
        return netValue;
    }

    private void setNetValue(BigDecimal netValue) {
        this.netValue = netValue;
    }

    public BigDecimal getTaxValue() {
        return taxValue;
    }

    private void setTaxValue(BigDecimal taxValue) {
        this.taxValue = taxValue;
    }

    public BigDecimal getGrossValue() {
        return grossValue;
    }

    private void setGrossValue(BigDecimal grossValue) {
        this.grossValue = grossValue;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public BigDecimal getPriceProp() {
        return priceProp.get();
    }

    public void setPriceProp(BigDecimal priceProp) {
        this.priceProp.set(priceProp);
        this.setPrice(priceProp);
    }

    public SimpleObjectProperty<BigDecimal> pricePropProperty() {
        return priceProp;
    }

    public BigDecimal getTaxRateProp() {
        return taxRateProp.get();
    }

    public void setTaxRateProp(BigDecimal taxRateProp) {
        this.taxRateProp.set(taxRateProp);
        this.setTaxRate(taxRateProp);
    }

    public SimpleObjectProperty<BigDecimal> taxRatePropProperty() {
        return taxRateProp;
    }

    public int getQuantityProp() {
        return quantityProp.get();
    }

    public void setQuantityProp(int quantityProp) {
        this.quantityProp.set(quantityProp);
        this.setQuantity(quantityProp);
    }

    public SimpleIntegerProperty quantityPropProperty() {
        return quantityProp;
    }

    public BigDecimal getNetValProp() {
        return netValProp.get();
    }

    public void setNetValProp(BigDecimal netValProp) {
        this.netValProp.set(netValProp);
        this.setNetValue(netValProp);
    }

    public SimpleObjectProperty<BigDecimal> netValPropProperty() {
        return netValProp;
    }

    public BigDecimal getTaxValProp() {
        return taxValProp.get();
    }

    public void setTaxValProp(BigDecimal taxValProp) {
        this.taxValProp.set(taxValProp);
        this.setTaxValue(taxValProp);
    }

    public SimpleObjectProperty<BigDecimal> taxValPropProperty() {
        return taxValProp;
    }

    public int getDiscountProp() {
        return discountProp.get();
    }

    public void setDiscountProp(int discountProp) {
        this.discountProp.set(discountProp);
        this.setDiscountPercents(discountProp);
    }

    public SimpleIntegerProperty discountPropProperty() {
        return discountProp;
    }

    public BigDecimal getGrossValProp() {
        return grossValProp.get();
    }

    public void setGrossValProp(BigDecimal grossValProp) {
        this.grossValProp.set(grossValProp);
        this.setGrossValue(grossValProp);
    }

    public SimpleObjectProperty<BigDecimal> grossValPropProperty() {
        return grossValProp;
    }
}
