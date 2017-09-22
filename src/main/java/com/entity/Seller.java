package com.entity;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "sellers")
@DynamicUpdate
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "alias")
    private String alias;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "tax_identifier_number")
    private String taxIdentifier;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "city")
    private String city;

    @Column(name = "account_number")
    private String accountNum;

    @Column(name = "bank")
    private String bank;

    @Column(name = "country")
    private String country;

    @Column(name = "telephone")
    private int telephone;

    @Column(name = "fax_number")
    private int fax;

    @Column(name = "creation_date", updatable = false, nullable = false)
    private LocalDate creationDate;

    @Column(name = "last_modified")
    private LocalDate lastModified;

    public Seller(){}

    public int getId() {
        return id;
    }

    public String getAlias() {
        return alias;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getTaxIdentifier() {
        return taxIdentifier;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public String getBank() {
        return bank;
    }

    public String getCountry() {
        return country;
    }

    public int getTelephone() {
        return telephone;
    }

    public int getFax() {
        return fax;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public LocalDate getLastModified() {
        return lastModified;
    }
}
