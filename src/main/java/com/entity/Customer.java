package com.entity;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "kontrahenci")
public class Customer {
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
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @NotNull(message = "To pole jest wymagane")
    @Column(name = "uwzglednij_numer_faktury", nullable = false)
    private boolean includeInCount;

    @NotNull(message = "To pole jest wymagane")
    @Column(name = "alias", nullable = false)
    private String alias;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<BoughtServices> boughtServicesSet = new HashSet<>();

    public Customer() {
        createProps();
    }

    // auto creates properties class for this customer after loading
    @PostLoad
    private void postLoad() {
        createProps();
    }

    private void createProps() {
        this.boughtServices = FXCollections.observableArrayList(boughtServicesSet);
        this.lastNameProp = new SimpleStringProperty(lastName);
        this.firstNameProp = new SimpleStringProperty(firstName);
        this.companyNameProp = new SimpleStringProperty(companyName);
        this.taxIdProp = new SimpleStringProperty(taxIdentifier);
        this.addressProp = new SimpleStringProperty(address);
        this.postalCodeProp = new SimpleStringProperty(postalCode);
        this.cityProp = new SimpleStringProperty(city);
        this.aliasProp = new SimpleStringProperty(alias);
        this.countProp = new SimpleBooleanProperty(includeInCount);
        this.paymentProp = new SimpleObjectProperty<>(paymentMethod);
    }

    // auto update fields just before UPDATE query execution
    @PreUpdate
    private void preUpdate() {
        this.lastName = this.getLastNameProp();
        this.firstName = this.getFirstNameProp();
        this.companyName = this.getCompanyNameProp();
        this.taxIdentifier = this.getTaxIdProp();
        this.address = this.getAddressProp();
        this.postalCode = this.getPostalCodeProp();
        this.city = this.getCityProp();
        this.paymentMethod = this.getPaymentProp();
        this.includeInCount = this.getCountProp();
        this.alias = this.getAliasProp();
    }

    @Transient private SimpleStringProperty lastNameProp;
    @Transient private SimpleStringProperty firstNameProp;
    @Transient private SimpleStringProperty companyNameProp;
    @Transient private SimpleStringProperty taxIdProp;
    @Transient private SimpleStringProperty addressProp;
    @Transient private SimpleStringProperty postalCodeProp;
    @Transient private SimpleStringProperty cityProp;
    @Transient private SimpleStringProperty aliasProp;
    @Transient private SimpleBooleanProperty countProp;
    @Transient private SimpleObjectProperty<PaymentMethod> paymentProp;
    @Transient private ObservableList<BoughtServices> boughtServices;

    public int getId() {
        return id;
    }

    //region getters and setters
    public String getLastNameProp() {
        return lastNameProp.get();
    }

    public void setLastNameProp(String lastNameProp) {
        this.lastNameProp.set(lastNameProp);
    }

    public SimpleStringProperty lastNamePropProperty() {
        return lastNameProp;
    }

    public String getFirstNameProp() {
        return firstNameProp.get();
    }

    public void setFirstNameProp(String firstNameProp) {
        this.firstNameProp.set(firstNameProp);
    }

    public SimpleStringProperty firstNamePropProperty() {
        return firstNameProp;
    }

    public String getCompanyNameProp() {
        return companyNameProp.get();
    }

    public void setCompanyNameProp(String companyNameProp) {
        this.companyNameProp.set(companyNameProp);
    }

    public SimpleStringProperty companyNamePropProperty() {
        return companyNameProp;
    }

    public String getTaxIdProp() {
        return taxIdProp.get();
    }

    public void setTaxIdProp(String taxIdProp) {
        this.taxIdProp.set(taxIdProp);
    }

    public SimpleStringProperty taxIdPropProperty() {
        return taxIdProp;
    }

    public String getAddressProp() {
        return addressProp.get();
    }

    public void setAddressProp(String addressProp) {
        this.addressProp.set(addressProp);
    }

    public SimpleStringProperty addressPropProperty() {
        return addressProp;
    }

    public String getPostalCodeProp() {
        return postalCodeProp.get();
    }

    public void setPostalCodeProp(String postalCodeProp) {
        this.postalCodeProp.set(postalCodeProp);
    }

    public SimpleStringProperty postalCodePropProperty() {
        return postalCodeProp;
    }

    public String getCityProp() {
        return cityProp.get();
    }

    public void setCityProp(String cityProp) {
        this.cityProp.set(cityProp);
    }

    public SimpleStringProperty cityPropProperty() {
        return cityProp;
    }

    public String getAliasProp() {
        return aliasProp.get();
    }

    public void setAliasProp(String aliasProp) {
        this.aliasProp.set(aliasProp);
    }

    public SimpleStringProperty aliasPropProperty() {
        return aliasProp;
    }

    public boolean getCountProp() {
        return countProp.get();
    }

    public void setCountProp(boolean countProp) {
        this.countProp.set(countProp);
    }

    public SimpleBooleanProperty countPropProperty() {
        return countProp;
    }

    public PaymentMethod getPaymentProp() {
        return paymentProp.get();
    }

    public void setPaymentProp(PaymentMethod paymentProp) {
        this.paymentProp.set(paymentProp);
    }

    public SimpleObjectProperty<PaymentMethod> paymentPropProperty() {
        return paymentProp;
    }

    public ObservableList<BoughtServices> getBoughtServices() {
        return boughtServices;
    }

    public void addBoughtServices(BoughtServices service) {
        this.boughtServices.add(service);
        this.boughtServicesSet.add(service);
    }

    public void removeBoughtSerbices(BoughtServices service) {
        this.boughtServices.remove(service);
    }
    //endregion
}
