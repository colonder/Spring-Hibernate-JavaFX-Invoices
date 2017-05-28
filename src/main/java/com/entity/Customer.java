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

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoughtServices> boughtServices = new ArrayList<>();

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
}
