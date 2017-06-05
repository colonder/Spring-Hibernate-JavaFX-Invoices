package com.entity;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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

    @ManyToOne//(fetch = FetchType.LAZY) TODO: had to comment this out for now
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
        private SimpleDoubleProperty quantityProp;
        private ReadOnlyDoubleWrapper valWithoutTax;
        private ReadOnlyDoubleWrapper taxVal;
        private ReadOnlyDoubleWrapper totalVal;

        // service properties
        private SimpleStringProperty serviceNameProp;
        private SimpleStringProperty symbolProp;
        private SimpleStringProperty unitProp;
        private SimpleDoubleProperty netUnitPriceProp;
        private SimpleIntegerProperty vatProp;

        public BoughtServicesProps()
        {
            serviceNameProp = new SimpleStringProperty(serviceEntity.getServiceName());
            symbolProp = new SimpleStringProperty(serviceEntity.getSymbol());
            unitProp = new SimpleStringProperty(serviceEntity.getUnit());
            netUnitPriceProp = new SimpleDoubleProperty(serviceEntity.getNetUnitPrice().doubleValue());
            vatProp = new SimpleIntegerProperty(serviceEntity.getVatTaxRate());

            quantityProp = new SimpleDoubleProperty(quantity.doubleValue());
            valWithoutTax = new ReadOnlyDoubleWrapper();
            taxVal = new ReadOnlyDoubleWrapper();
            totalVal = new ReadOnlyDoubleWrapper();

            valWithoutTax.bind(quantityProp.multiply(netUnitPriceProp));
            taxVal.bind(Bindings.multiply(valWithoutTax, vatProp).multiply(0.01));
            totalVal.bind(valWithoutTax.add(taxVal));
        }

        public BoughtServices getBoughtService()
        {
            return BoughtServices.this;
        }

        public double getQuantityProp() {
            return quantityProp.get();
        }

        public SimpleDoubleProperty quantityPropProperty() {
            return quantityProp;
        }

        public void setQuantityProp(double quantityProp) {
            this.quantityProp.set(quantityProp);
        }

        public double getValWithoutTax() {
            return valWithoutTax.get();
        }

        public ReadOnlyDoubleWrapper valWithoutTaxProperty() {
            return valWithoutTax;
        }

        public void setValWithoutTax(double valWithoutTax) {
            this.valWithoutTax.set(valWithoutTax);
        }

        public double getTaxVal() {
            return taxVal.get();
        }

        public ReadOnlyDoubleWrapper taxValProperty() {
            return taxVal;
        }

        public void setTaxVal(double taxVal) {
            this.taxVal.set(taxVal);
        }

        public double getTotalVal() {
            return totalVal.get();
        }

        public ReadOnlyDoubleWrapper totalValProperty() {
            return totalVal;
        }

        public void setTotalVal(double totalVal) {
            this.totalVal.set(totalVal);
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
    }
}
