package com.entity;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "wykupione_uslugi")
@Immutable
@NoArgsConstructor
public class BoughtServices implements Serializable
{
    @Embeddable
    public static class InternalId implements Serializable
    {
        @Column(name = "kontrahent_id")
        private int customerId;

        @Column(name = "usluga_id")
        private int serviceId;

        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private int id;

        public InternalId() {}

        public InternalId(int serviceId, int customerId)
        {
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

    public BoughtServices(Customer customer, ServiceEntity serviceEntity, BigDecimal quantity)
    {
        this.customer = customer;
        this.serviceEntity = serviceEntity;
        this.quantity = quantity;

        this.internalId.customerId = customer.getId();
        this.internalId.serviceId = serviceEntity.getId();
    }

    public BoughtServicesProps getBoughtServicesProps() {
        return boughtServicesProps;
    }

    public InternalId getInternalId() {
        return internalId;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ServiceEntity getServiceEntity() {
        return serviceEntity;
    }

    public void setServiceEntity(ServiceEntity serviceEntity) {
        this.serviceEntity = serviceEntity;
    }

    public class BoughtServicesProps
    {
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

        public BoughtServicesProps()
        {
            this.serviceNameProp = new SimpleStringProperty(serviceEntity.getServiceName());
            this.symbolProp = new SimpleStringProperty(serviceEntity.getSymbol());
            this.unitProp = new SimpleStringProperty(serviceEntity.getUnit());
            this.netUnitPriceProp = new SimpleObjectProperty<>(serviceEntity.getNetUnitPrice());
            this.vatProp = new SimpleIntegerProperty(serviceEntity.getVatTaxRate());

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

        private void performCalculations()
        {
            taxVal.set(getValWithoutTax().multiply(BigDecimal.valueOf(getVatProp())).setScale(2, BigDecimal.ROUND_HALF_DOWN));
            totalVal.set(getValWithoutTax().multiply(getTaxVal()).setScale(2, BigDecimal.ROUND_HALF_DOWN));
        }

        public BoughtServices getBoughtService()
        {
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

        public SimpleObjectProperty<BigDecimal> quantityPropProperty() {
            return quantityProp;
        }

        public void setQuantityProp(BigDecimal quantityProp) {
            this.quantityProp.set(quantityProp);
            BoughtServices.this.setQuantity(quantityProp);
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
}
