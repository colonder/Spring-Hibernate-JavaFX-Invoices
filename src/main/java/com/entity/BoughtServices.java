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
    @EmbeddedId
    private InternalId internalId = new InternalId();

    @NotNull
    @Column(name = "ilosc")
    private BigDecimal quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kontrahent_id", insertable = false, updatable = false)
    private Customer customer;

    @ManyToOne//(fetch = FetchType.LAZY) //TODO: comment out for now
    @JoinColumn(name = "usluga_id", insertable = false, updatable = false)
    private ServiceEntity serviceEntity;

    @Transient
    private BoughtServicesProps boughtServicesProps;

    @Embeddable
    public static class InternalId implements Serializable {
        @Column(name = "kontrahent_id")
        private int customerId;

        @Column(name = "usluga_id")
        private int serviceId;

        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private int id;

        public InternalId() {}

        public InternalId(int serviceId, int customerId) {
            this.serviceId = serviceId;
            this.customerId = customerId;
        }

        public int getId() {
            return id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            InternalId that = (InternalId) o;
            if (customerId != that.customerId) return false;
            if (serviceId != that.serviceId) return false;
            return id == that.id;
        }

        @Override
        public int hashCode() {
            int result = customerId;
            result = 31 * result + serviceId;
            result = 31 * result + id;
            return result;
        }
    }

    public BoughtServices() {}

    public BoughtServices(Customer customer, ServiceEntity serviceEntity, BigDecimal quantity) {
        this.customer = customer;
        this.serviceEntity = serviceEntity;
        this.quantity = quantity;
        this.internalId.customerId = customer.getId();
        this.internalId.serviceId = serviceEntity.getId();
    }

    private void createProps()
    {
        this.boughtServicesProps = new BoughtServicesProps();
    }

    @PostLoad
    private void postLoad()
    {
        createProps();
    }

    @PreUpdate
    private void preUpdate()
    {
        this.quantity = this.boughtServicesProps.getQuantityProp();
    }

    //region getters and setters
    public BoughtServicesProps getBoughtServicesProps() {
        return boughtServicesProps;
    }
    public InternalId getInternalId() {
        return internalId;
    }
    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    //endregion

    public class BoughtServicesProps {
        // bought service properties
        private SimpleObjectProperty<BigDecimal> quantityProp;
        private ReadOnlyObjectWrapper<BigDecimal> valWithoutTax;
        private ReadOnlyObjectWrapper<BigDecimal> taxVal;
        private ReadOnlyObjectWrapper<BigDecimal> totalVal;

        // service properties
        private SimpleStringProperty serviceNameProp;
        private SimpleStringProperty symbolProp;
        private SimpleStringProperty unitProp;
        private SimpleObjectProperty<BigDecimal> netUnitPriceProp;
        private SimpleIntegerProperty vatProp;

        public BoughtServicesProps() {
            this.serviceNameProp = serviceEntity.getServiceEntityProps().serviceNamePropProperty();
            this.symbolProp = serviceEntity.getServiceEntityProps().symbolPropProperty();
            this.unitProp = serviceEntity.getServiceEntityProps().unitPropProperty();
            this.netUnitPriceProp = serviceEntity.getServiceEntityProps().netUnitPricePropProperty();
            this.vatProp = serviceEntity.getServiceEntityProps().vatPropProperty();
            this.quantityProp = new SimpleObjectProperty<>(quantity);
            this.valWithoutTax = new ReadOnlyObjectWrapper<>(quantity.multiply(getNetUnitPriceProp())
                    .setScale(2, BigDecimal.ROUND_HALF_DOWN));
            this.taxVal = new ReadOnlyObjectWrapper<>();
            this.totalVal = new ReadOnlyObjectWrapper<>();
            performCalculations();

            this.quantityProp.addListener((ObservableValue<? extends BigDecimal> observable, BigDecimal oldValue, BigDecimal newValue) -> {
                valWithoutTax.set(newValue.multiply(getNetUnitPriceProp()).setScale(2, BigDecimal.ROUND_HALF_DOWN));
                performCalculations();
            });

            this.netUnitPriceProp.addListener((ObservableValue<? extends BigDecimal> observable, BigDecimal oldValue, BigDecimal newValue) -> {
                valWithoutTax.set(newValue.multiply(getQuantityProp()).setScale(2, BigDecimal.ROUND_HALF_DOWN));
                performCalculations();
            });
        }

        private void performCalculations() {
            taxVal.set(getValWithoutTax().multiply(BigDecimal.valueOf(getVatProp())).multiply(BigDecimal.valueOf(0.01)).setScale(2, BigDecimal.ROUND_HALF_DOWN));
            totalVal.set(getValWithoutTax().add(getTaxVal()).setScale(2, BigDecimal.ROUND_HALF_DOWN));
        }

        //region getters and setters
        public BoughtServices getBoughtService() {
            return BoughtServices.this;
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
}
