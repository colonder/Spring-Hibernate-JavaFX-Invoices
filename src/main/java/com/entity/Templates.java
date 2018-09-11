package com.entity;

import javafx.beans.property.SimpleObjectProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "templates")
@Getter
@Setter
@DynamicUpdate
public class Templates {

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Basic
    @Column(name = "QUANTITY")
    private BigDecimal quantity;

    @Transient
    private SimpleObjectProperty<BigDecimal> quantityProp;

    @Transient
    private SimpleObjectProperty<BigDecimal> netValProp;

    @Transient
    private SimpleObjectProperty<BigDecimal> taxValProp;

    @Transient
    private SimpleObjectProperty<BigDecimal> grossValProp;

    public Templates() {
        this.quantityProp = new SimpleObjectProperty<>();
        this.netValProp = new SimpleObjectProperty<>(BigDecimal.ZERO);
        this.taxValProp = new SimpleObjectProperty<>(BigDecimal.ZERO);
        this.grossValProp = new SimpleObjectProperty<>(BigDecimal.ZERO);
    }

    public Templates(Customer customer, Product product) {
        this();
        this.customer = customer;
        this.product = product;
        setListeners();
    }

    public BigDecimal getQuantityProp() {
        return quantityProp.get();
    }

    public void setQuantityProp(BigDecimal quantityProp) {
        this.quantityProp.set(quantityProp);
        setQuantity(quantityProp);
    }

    @PostLoad
    private void setListeners() {
        this.quantityProp.addListener(((observableValue, oldValue, newValue) -> {
            this.netValProp.set(newValue.multiply(product.getUnitNetPrice()).setScale(2, RoundingMode.HALF_UP));
            this.taxValProp.set(getNetValProp().multiply(product.getVatRate().movePointLeft(2))
                    .setScale(2, RoundingMode.HALF_UP));
            this.grossValProp.set(getNetValProp().add(getTaxValProp()).setScale(2, RoundingMode.HALF_UP));
        }));
        setQuantityProp(quantity == null ? BigDecimal.ZERO : quantity);
    }

    public BigDecimal getNetValProp() {
        return netValProp.get();
    }

    public BigDecimal getTaxValProp() {
        return taxValProp.get();
    }

    public BigDecimal getGrossValProp() {
        return grossValProp.get();
    }

    public SimpleObjectProperty<BigDecimal> quantityPropProperty() {
        return quantityProp;
    }

    public SimpleObjectProperty<BigDecimal> netValPropProperty() {
        return netValProp;
    }

    public SimpleObjectProperty<BigDecimal> taxValPropProperty() {
        return taxValProp;
    }

    public SimpleObjectProperty<BigDecimal> grossValPropProperty() {
        return grossValProp;
    }
}
