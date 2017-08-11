package com.entity;

import com.entity.enums.ClientType;
import com.entity.enums.PaymentMethod;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customer")
public class Customer {
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

    @Column(name = "personal_id")
    private int personalId;

    @Column(name = "tax_identifier_number")
    private String taxIdentifier;

    @Column(name = "email")
    private String email;

    @Column(name = "street_house_no")
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

    @Enumerated(EnumType.STRING)
    @Column(name = "default_payment_method")
    private PaymentMethod defaultPaymentMethod;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "last_purchase_date")
    private LocalDate lastPurchaseDate;

    @Column(name = "country")
    private String country;

    @Enumerated(EnumType.STRING)
    @Column(name = "client_type")
    private ClientType clientType;

    @Column(name = "company_special_number") // regon
    private int companySpecialNumber;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "default_currency")
    private String defaultCurrency;

    @Column(name = "bank_account_number")
    private int bankAccountNumber;

    @Column(name = "default_discount")
    private BigDecimal defaultDiscount;

    @Column(name = "default_payment_date_days")
    private int defaultPaymentDateDays;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private Set<Invoice> boughtProductsSet = new HashSet<>();

    public Customer() {}
}
