package com.entity;

import javafx.beans.property.SimpleStringProperty;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "kontrahenci")
@NoArgsConstructor
public class Customer
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nazwisko")
    private String lastName;

    @Column(name = "imie")
    private String firstName;

    @Column(name = "firma_nazwa")
    private String companyName;

    @NotNull(message = "To pole jest wymagane")
    @Column(name = "nip_pesel", nullable = false)
    private String taxIdentifier;

    @NotNull(message = "To pole jest wymagane")
    @Column(name = "ulica_nr_mieszkania", nullable = false)
    private String address;

    @NotNull(message = "To pole jest wymagane")
    @Column(name = "kod_pocztowy", nullable = false)
    private String postalCode;

    @NotNull(message = "To pole jest wymagane")
    @Column(name = "miejscowosc", nullable = false)
    private String city;

    @NotNull(message = "To pole jest wymagane")
    @Column(name = "sposob_zaplaty", nullable = false)
    private String paymentMethod; //TODO: change to enum with 0 = cash and 1 = bank transfer

    @NotNull(message = "To pole jest wymagane")
    @Column(name = "uwzglednij_numer_faktury", nullable = false)
    private String includeInCount; //TODO: change to enum with 1 and 0

    @NotNull(message = "To pole jest wymagane")
    @Column(name = "alias", nullable = false)
    private String alias;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BoughtServices> boughtServices = new ArrayList<>();

    @Transient
    private CustomerProps customerProps;

    //TODO: also make properties for payment method and include in count fields

    public Customer(String lastName, String firstName, String companyName, String taxIdentifier, String address,
                    String postalCode, String city, String paymentMethod, String includeInCount, String alias)
    {
        this.lastName = lastName;
        this.firstName = firstName;
        this.companyName = companyName;
        this.taxIdentifier = taxIdentifier;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
        this.paymentMethod = paymentMethod;
        this.includeInCount = includeInCount;
        this.alias = alias;
    }

    public CustomerProps getCustomerProps() {
        return customerProps;
    }

    public List<BoughtServices> getBoughtServices() {
        return boughtServices;
    }

    public void setBoughtServices(List<BoughtServices> boughtServices) {
        this.boughtServices = boughtServices;
    }

    public void addBoughtService(BoughtServices service)
    {
        boughtServices.add(service);
        service.setCustomer(this);
    }

    public void removeBoughtService(BoughtServices service)
    {
        boughtServices.remove(service);
        service.setCustomer(null);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTaxIdentifier() {
        return taxIdentifier;
    }

    public void setTaxIdentifier(String taxIdentifier) {
        this.taxIdentifier = taxIdentifier;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getIncludeInCount() {
        return includeInCount;
    }

    public void setIncludeInCount(String includeInCount) {
        this.includeInCount = includeInCount;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public class CustomerProps
    {
        // customer properties
        private SimpleStringProperty lastNameProp;
        private SimpleStringProperty firstNameProp;
        private SimpleStringProperty companyNameProp;
        private SimpleStringProperty taxIdProp;
        private SimpleStringProperty addressProp;
        private SimpleStringProperty postalCodeProp;
        private SimpleStringProperty cityProp;
        private SimpleStringProperty aliasProp;
        private SimpleStringProperty countProp;
        private SimpleStringProperty paymentProp;

        public CustomerProps()
        {
            this.lastNameProp = new SimpleStringProperty(lastName);
            this.firstNameProp = new SimpleStringProperty(firstName);
            this.companyNameProp = new SimpleStringProperty(companyName);
            this.taxIdProp = new SimpleStringProperty(taxIdentifier);
            this.addressProp = new SimpleStringProperty(address);
            this.postalCodeProp = new SimpleStringProperty(postalCode);
            this.cityProp = new SimpleStringProperty(city);
            this.aliasProp = new SimpleStringProperty(alias);
            this.countProp = new SimpleStringProperty(includeInCount);
            this.paymentProp = new SimpleStringProperty(paymentMethod);
        }

        //TODO: presenters calling methods in here to remove, add etc?

        public Customer getCustomer()
        {
            return Customer.this;
        }

        public String getLastNameProp() {
            return lastNameProp.get();
        }

        public SimpleStringProperty lastNamePropProperty() {
            return lastNameProp;
        }

        public void setLastNameProp(String lastNameProp) {
            this.lastNameProp.set(lastNameProp);
        }

        public String getFirstNameProp() {
            return firstNameProp.get();
        }

        public SimpleStringProperty firstNamePropProperty() {
            return firstNameProp;
        }

        public void setFirstNameProp(String firstNameProp) {
            this.firstNameProp.set(firstNameProp);
        }

        public String getCompanyNameProp() {
            return companyNameProp.get();
        }

        public SimpleStringProperty companyNamePropProperty() {
            return companyNameProp;
        }

        public void setCompanyNameProp(String companyNameProp) {
            this.companyNameProp.set(companyNameProp);
        }

        public String getTaxIdProp() {
            return taxIdProp.get();
        }

        public SimpleStringProperty taxIdPropProperty() {
            return taxIdProp;
        }

        public void setTaxIdProp(String taxIdProp) {
            this.taxIdProp.set(taxIdProp);
        }

        public String getAddressProp() {
            return addressProp.get();
        }

        public SimpleStringProperty addressPropProperty() {
            return addressProp;
        }

        public void setAddressProp(String addressProp) {
            this.addressProp.set(addressProp);
        }

        public String getPostalCodeProp() {
            return postalCodeProp.get();
        }

        public SimpleStringProperty postalCodePropProperty() {
            return postalCodeProp;
        }

        public void setPostalCodeProp(String postalCodeProp) {
            this.postalCodeProp.set(postalCodeProp);
        }

        public String getCityProp() {
            return cityProp.get();
        }

        public SimpleStringProperty cityPropProperty() {
            return cityProp;
        }

        public void setCityProp(String cityProp) {
            this.cityProp.set(cityProp);
        }

        public String getAliasProp() {
            return aliasProp.get();
        }

        public SimpleStringProperty aliasPropProperty() {
            return aliasProp;
        }

        public void setAliasProp(String aliasProp) {
            this.aliasProp.set(aliasProp);
        }

        public String getCountProp() {
            return countProp.get();
        }

        public SimpleStringProperty countPropProperty() {
            return countProp;
        }

        public void setCountProp(String countProp) {
            this.countProp.set(countProp);
        }

        public String getPaymentProp() {
            return paymentProp.get();
        }

        public SimpleStringProperty paymentPropProperty() {
            return paymentProp;
        }

        public void setPaymentProp(String paymentProp) {
            this.paymentProp.set(paymentProp);
        }
    }
}
