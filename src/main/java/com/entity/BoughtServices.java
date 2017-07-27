package com.entity;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "wykupione_uslugi")
@Immutable
public class BoughtServices implements Serializable {

    @NotNull
    @Column(name = "ilosc")
    private BigDecimal quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kontrahent_id", referencedColumnName = "id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usluga_id", referencedColumnName = "id")
    private ServiceEntity serviceEntity;

    @Id
    @Column(name = "id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public BoughtServices() {
    }

    public BoughtServices(Customer customer, ServiceEntity serviceEntity, BigDecimal quantity) {
        this.customer = customer;
        this.serviceEntity = serviceEntity;
        this.quantity = quantity;
        createProps();
    }

    private void createProps() {
        this.serviceNameProp = serviceEntity.serviceNamePropProperty();
        this.symbolProp = serviceEntity.symbolPropProperty();
        this.unitProp = serviceEntity.unitPropProperty();
        this.netUnitPriceProp = serviceEntity.netUnitPricePropProperty();
        this.vatProp = serviceEntity.vatPropProperty();
        this.quantityProp = new SimpleObjectProperty<>(quantity);
        this.valWithoutTax = new ReadOnlyObjectWrapper<>(quantity.multiply(getNetUnitPriceProp())
                .setScale(2, BigDecimal.ROUND_HALF_DOWN));
        this.taxVal = new ReadOnlyObjectWrapper<>();
        this.totalVal = new ReadOnlyObjectWrapper<>();
        performCalculations();

        this.quantityProp.addListener((ObservableValue<? extends BigDecimal> observable, BigDecimal oldValue,
                                       BigDecimal newValue) -> {
            valWithoutTax.set(newValue.multiply(getNetUnitPriceProp()).setScale(2, BigDecimal.ROUND_HALF_DOWN));
            performCalculations();
        });

        this.netUnitPriceProp.addListener((ObservableValue<? extends BigDecimal> observable, BigDecimal oldValue,
                                           BigDecimal newValue) -> {
            valWithoutTax.set(newValue.multiply(getQuantityProp()).setScale(2, BigDecimal.ROUND_HALF_DOWN));
            performCalculations();
        });
     }

    private void performCalculations() {
        taxVal.set(getValWithoutTax().multiply(BigDecimal.valueOf(getVatProp())).multiply(BigDecimal.valueOf(0.01))
                .setScale(2, BigDecimal.ROUND_HALF_DOWN));
        totalVal.set(getValWithoutTax().add(getTaxVal()).setScale(2, BigDecimal.ROUND_HALF_DOWN));
    }

    @PostLoad
    private void postLoad() {
        createProps();
    }

    @PreUpdate
    private void preUpdate() {
        this.quantity = this.getQuantityProp();
    }

    // bought service properties
    @Transient private SimpleObjectProperty<BigDecimal> quantityProp;
    @Transient private ReadOnlyObjectWrapper<BigDecimal> valWithoutTax;
    @Transient private ReadOnlyObjectWrapper<BigDecimal> taxVal;
    @Transient private ReadOnlyObjectWrapper<BigDecimal> totalVal;

    // service properties
    @Transient private SimpleStringProperty serviceNameProp;
    @Transient private SimpleStringProperty symbolProp;
    @Transient private SimpleStringProperty unitProp;
    @Transient private SimpleObjectProperty<BigDecimal> netUnitPriceProp;
    @Transient private SimpleIntegerProperty vatProp;

    //region getters and setters
    public int getId() {
        return id;
    }

    public BigDecimal getValWithoutTax() {
        return valWithoutTax.get();
    }

    public ReadOnlyObjectWrapper<BigDecimal> valWithoutTaxProperty() {
        return valWithoutTax;
    }

    public BigDecimal getTaxVal() {
        return taxVal.get();
    }

    public ReadOnlyObjectWrapper<BigDecimal> taxValProperty() {
        return taxVal;
    }

    public BigDecimal getTotalVal() {
        return totalVal.get();
    }

    public ReadOnlyObjectWrapper<BigDecimal> totalValProperty() {
        return totalVal;
    }

    public BigDecimal getQuantityProp() {
        return quantityProp.get();
    }

    public void setQuantityProp(BigDecimal quantityProp) {
        this.quantityProp.set(quantityProp);
    }

    public SimpleObjectProperty<BigDecimal> quantityPropProperty() {
        return quantityProp;
    }

    public String getServiceNameProp() {
        return serviceNameProp.get();
    }

    public void setServiceNameProp(String serviceNameProp) {
        this.serviceNameProp.set(serviceNameProp);
    }

    public SimpleStringProperty serviceNamePropProperty() {
        return serviceNameProp;
    }

    public String getSymbolProp() {
        return symbolProp.get();
    }

    public void setSymbolProp(String symbolProp) {
        this.symbolProp.set(symbolProp);
    }

    public SimpleStringProperty symbolPropProperty() {
        return symbolProp;
    }

    public String getUnitProp() {
        return unitProp.get();
    }

    public void setUnitProp(String unitProp) {
        this.unitProp.set(unitProp);
    }

    public SimpleStringProperty unitPropProperty() {
        return unitProp;
    }

    public BigDecimal getNetUnitPriceProp() {
        return netUnitPriceProp.get();
    }

    public void setNetUnitPriceProp(BigDecimal netUnitPriceProp) {
        this.netUnitPriceProp.set(netUnitPriceProp);
    }

    public SimpleObjectProperty<BigDecimal> netUnitPricePropProperty() {
        return netUnitPriceProp;
    }

    public int getVatProp() {
        return vatProp.get();
    }

    public void setVatProp(int vatProp) {
        this.vatProp.set(vatProp);
    }

    public SimpleIntegerProperty vatPropProperty() {
        return vatProp;
    }
    //endregion
}
