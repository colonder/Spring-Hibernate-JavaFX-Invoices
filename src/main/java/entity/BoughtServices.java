package entity;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "wykupione_uslugi")
@Immutable
public class BoughtServices implements Serializable
{
    @Embeddable
    public static class Id implements Serializable
    {
        @Column(name = "kontrahent_id")
        private int customerId;

        @Column(name = "usluga_id")
        private int serviceId;

        public Id() {}

        public Id(int serviceId, int customerId)
        {
            this.serviceId = serviceId;
            this.customerId = customerId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Id id = (Id) o;

            if (customerId != id.customerId) return false;
            return serviceId == id.serviceId;
        }

        @Override
        public int hashCode() {
            int result = customerId;
            result = 31 * result + serviceId;
            return result;
        }
    }

    @EmbeddedId
    private Id id = new Id();

    @NotNull
    @Column(name = "ilosc")
    private BigDecimal quantity;

    @ManyToOne
    @JoinColumn(name = "kontrahent_id", insertable = false, updatable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "usluga_id", insertable = false, updatable = false)
    private ServiceEntity serviceEntity;

    public BoughtServices() {}

    public BoughtServices(Customer customer, ServiceEntity serviceEntity, BigDecimal quantity)
    {
        this.customer = customer;
        this.serviceEntity = serviceEntity;
        this.quantity = quantity;

        this.id.customerId = customer.getId();
        this.id.serviceId = serviceEntity.getId();

        customer.getBoughtServices().add(this); // bidirectional navigation
    }

    public Id getId()
    {
        return id;
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
