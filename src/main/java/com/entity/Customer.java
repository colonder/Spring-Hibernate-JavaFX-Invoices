package com.entity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "kontrahenci")
@NoArgsConstructor
@Access(value = AccessType.PROPERTY)
public class Customer
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private SimpleIntegerProperty id;

    @Column(name = "nazwisko")  // in case that an invoice is issued to a company then first and last name can be null
    private SimpleStringProperty lastName;    // the same is for the case when an invoice is issued to a person that has no official
                                // company name - then it can be null
    @Column(name = "imie")
    private SimpleStringProperty firstName;

    @Column(name = "firma_nazwa")
    private SimpleStringProperty companyName;

    @NotNull(message = "To pole jest wymagane")
    @Column(name = "nip_pesel", nullable = false)
    private SimpleStringProperty taxIdentifier;

    @NotNull(message = "To pole jest wymagane")
    @Column(name = "ulica_nr_mieszkania", nullable = false)
    private SimpleStringProperty address;

    @NotNull(message = "To pole jest wymagane")
    @Column(name = "kod_pocztowy", nullable = false)
    private SimpleStringProperty postalCode;

    @NotNull(message = "To pole jest wymagane")
    @Column(name = "miejscowosc", nullable = false)
    private SimpleStringProperty city;

    @NotNull(message = "To pole jest wymagane")
    @Column(name = "sposob_zaplaty", nullable = false)
    private SimpleStringProperty paymentMethod; //TODO: change to enum with 0 = cash and 1 = bank transfer

    @NotNull(message = "To pole jest wymagane")
    @Column(name = "uwzglednij_numer_faktury", nullable = false)
    private SimpleStringProperty includeInCount; //TODO: change to enum with 1 and 0

    @NotNull(message = "To pole jest wymagane")
    @Column(name = "alias", nullable = false)
    private SimpleStringProperty alias;

    @OneToMany(mappedBy = "customer")
    private Set<BoughtServices> boughtServices = new HashSet<>();

    public Set<BoughtServices> getBoughtServices() {
        return boughtServices;
    }

    public void setBoughtServices(Set<BoughtServices> boughtServices) {
        this.boughtServices = boughtServices;
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getCompanyName() {
        return companyName.get();
    }

    public SimpleStringProperty companyNameProperty() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }

    public String getTaxIdentifier() {
        return taxIdentifier.get();
    }

    public SimpleStringProperty taxIdentifierProperty() {
        return taxIdentifier;
    }

    public void setTaxIdentifier(String taxIdentifier) {
        this.taxIdentifier.set(taxIdentifier);
    }

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getPostalCode() {
        return postalCode.get();
    }

    public SimpleStringProperty postalCodeProperty() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode.set(postalCode);
    }

    public String getCity() {
        return city.get();
    }

    public SimpleStringProperty cityProperty() {
        return city;
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public String getPaymentMethod() {
        return paymentMethod.get();
    }

    public SimpleStringProperty paymentMethodProperty() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod.set(paymentMethod);
    }

    public String getIncludeInCount() {
        return includeInCount.get();
    }

    public SimpleStringProperty includeInCountProperty() {
        return includeInCount;
    }

    public void setIncludeInCount(String includeInCount) {
        this.includeInCount.set(includeInCount);
    }

    public String getAlias() {
        return alias.get();
    }

    public SimpleStringProperty aliasProperty() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias.set(alias);
    }
}
