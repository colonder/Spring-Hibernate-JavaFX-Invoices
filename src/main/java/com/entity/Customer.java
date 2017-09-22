package com.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customer")
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

    @Column(name = "personal_id")
    private long personalId;

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

    @Column(name = "telephone")
    private int telephone;

    @Column(name = "cell_phone")
    private int cellPhone;

    @Column(name = "fax_number")
    private int fax;

    @Column(name = "tag")
    private String tag;

    @Column(name = "default_payment_method")
    private String defaultPaymentMethod;

    @Column(name = "creation_date", updatable = false, nullable = false)
    private LocalDate creationDate;

    @Column(name = "last_modified")
    private LocalDate lastModified;

    @Column(name = "last_purchase_date")
    private LocalDate lastPurchaseDate;

    @Column(name = "country")
    private String country;

    @Column(name = "customer_type")
    private String customerType;

    @Column(name = "company_special_number") // regon
    private int companySpecialNumber;

    @Column(name = "default_discount")
    private BigDecimal defaultDiscount;

    @Column(name = "default_payment_date_days")
    private int defaultPaymentDateDays;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private Set<Invoice> invoiceHashSet = new HashSet<>();

    public Customer() {}

    public int getId() {
        return id;
    }

    public String getAlias() {
        return alias;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public long getPersonalId() {
        return personalId;
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

    public int getTelephone() {
        return telephone;
    }

    public int getCellPhone() {
        return cellPhone;
    }

    public int getFax() {
        return fax;
    }

    public String getTag() {
        return tag;
    }

    public String getDefaultPaymentMethod() {
        return defaultPaymentMethod;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public LocalDate getLastModified() {
        return lastModified;
    }

    public LocalDate getLastPurchaseDate() {
        return lastPurchaseDate;
    }

    public String getCountry() {
        return country;
    }

    public String getCustomerType() {
        return customerType;
    }

    public int getCompanySpecialNumber() {
        return companySpecialNumber;
    }

    public BigDecimal getDefaultDiscount() {
        return defaultDiscount;
    }

    public int getDefaultPaymentDateDays() {
        return defaultPaymentDateDays;
    }

    public Set<Invoice> getInvoiceHashSet() {
        return invoiceHashSet;
    }
}
