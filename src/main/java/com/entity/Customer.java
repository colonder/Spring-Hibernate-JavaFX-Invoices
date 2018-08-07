package com.entity;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "customer")
@DynamicUpdate
public class Customer extends BaseAbstractEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "alias")
    private String alias;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "tax_identifier_number", nullable = false)
    private String taxIdentifier;

    @Column(name = "address")
    private String address;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "city")
    private String city;

    @Column(name = "default_payment_method")
    private Integer defaultPaymentMethod;

    @Column(name = "company_special_number") // regon
    private Integer companySpecialNumber;

    public void setAll(String alias, String companyName, String lastName, String firstName, String taxIdentifier,
                       String address, String postalCode, String city, Integer defaultPaymentMethod,
                       Integer companySpecialNumber) {
        this.alias = alias;
        this.companyName = companyName;
        this.lastName = lastName;
        this.firstName = firstName;
        this.taxIdentifier = taxIdentifier;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
        this.defaultPaymentMethod = defaultPaymentMethod;
        this.companySpecialNumber = companySpecialNumber;
    }

    //region getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public Integer getCompanySpecialNumber() {
        return companySpecialNumber;
    }

    public void setCompanySpecialNumber(Integer companySpecialNumber) {
        this.companySpecialNumber = companySpecialNumber;
    }

    public Integer getDefaultPaymentMethod() {
        return defaultPaymentMethod;
    }

    public void setDefaultPaymentMethod(Integer defaultPaymentMethod) {
        this.defaultPaymentMethod = defaultPaymentMethod;
    }


    //endregion
}
