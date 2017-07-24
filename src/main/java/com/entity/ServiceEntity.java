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

    @Transient
    private ServiceEntityProps serviceEntityProps;

    //TODO: write a trigger in database preventing from deleting service when any customer is still using it(?)

    public ServiceEntity() {
        createProps();
    }

    private void createProps() {
        this.serviceEntityProps = new ServiceEntityProps();
    }

    @PostLoad
    private void postLoad() {
        createProps();
    }

    @PreUpdate
    private void preUpdate() {
        this.serviceName = this.serviceEntityProps.getServiceNameProp();
        this.symbol = this.serviceEntityProps.getSymbolProp();
        this.unit = this.serviceEntityProps.getUnitProp();
        this.netUnitPrice = this.serviceEntityProps.getNetUnitPriceProp();
        this.vatTaxRate = this.serviceEntityProps.getVatProp();
    }

    public int getId() {
        return id;
    }

    public ServiceEntityProps getServiceEntityProps() {
        return serviceEntityProps;
    }

    public String getServiceName() {
        return serviceName;
    }

    public class ServiceEntityProps {
        private SimpleStringProperty serviceNameProp;
        private SimpleStringProperty symbolProp;
        private SimpleStringProperty unitProp;
        private SimpleObjectProperty<BigDecimal> netUnitPriceProp;
        private SimpleIntegerProperty vatProp;

        public ServiceEntityProps() {
            this.serviceNameProp = new SimpleStringProperty(serviceName);
            this.symbolProp = new SimpleStringProperty(symbol);
            this.unitProp = new SimpleStringProperty(unit);
            this.netUnitPriceProp = new SimpleObjectProperty<>(netUnitPrice);
            this.vatProp = new SimpleIntegerProperty(vatTaxRate);
        }

        //region getters and setters
        public ServiceEntity getServiceEntity() {
            return ServiceEntity.this;
        }

        public ServiceEntity setServiceEntity() {
            return ServiceEntity.this;
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
}
