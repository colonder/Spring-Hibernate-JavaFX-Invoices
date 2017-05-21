package com.entity;

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

    @ManyToOne
    @JoinColumn(name = "kontrahent_id", insertable = false, updatable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "usluga_id", insertable = false, updatable = false)
    private ServiceEntity serviceEntity;

    public BoughtServices(Customer customer, ServiceEntity serviceEntity, BigDecimal quantity)
    {
        this.customer = customer;
        this.serviceEntity = serviceEntity;
        this.quantity = quantity;

        this.internalId.customerId = customer.getId();
        this.internalId.serviceId = serviceEntity.getId();
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

}
