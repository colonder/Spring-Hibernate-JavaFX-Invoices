package com.entity;

import com.enums.CustomerType;
import com.enums.PaymentMethod;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "city")
    private String city;

    @Column(name = "telephone")
    private Integer telephone;

    @Column(name = "cell_phone")
    private Integer cellPhone;

    @Column(name = "fax_number")
    private Integer fax;

    @Column(name = "tag")
    private String tag;

    @Enumerated(EnumType.STRING)
    @Column(name = "default_payment_method")
    private PaymentMethod defaultPaymentMethod;

    @Column(name = "creation_date", updatable = false, nullable = false)
    private LocalDate creationDate;

    @Column(name = "last_modified")
    private LocalDate lastModified;

    @Column(name = "last_purchase_date")
    private LocalDate lastPurchaseDate;

    @Column(name = "country")
    private String country;

    @Enumerated(EnumType.STRING)
    @Column(name = "customer_type", nullable = false)
    private CustomerType customerType;

    @Column(name = "company_special_number") // regon
    private Integer companySpecialNumber;

    @Column(name = "default_discount")
    private BigDecimal defaultDiscount;

    @Column(name = "default_payment_date_days")
    private Integer defaultPaymentDateDays;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private Set<Invoice> invoiceHashSet;

    public Customer() {
        this.creationDate = LocalDate.now();
        this.lastModified = LocalDate.now();
        invoiceHashSet = new HashSet<>();
    }

    public void setAll(String alias, String companyName, String lastName, String firstName, String taxIdentifier,
                    String email, String address, String postalCode, String city, Integer telephone, Integer cellPhone,
                    Integer fax, String tag, PaymentMethod defaultPaymentMethod, LocalDate lastModified, String country,
                    CustomerType customerType, Integer companySpecialNumber, BigDecimal defaultDiscount,
                    Integer defaultPaymentDateDays) {
        this.alias = alias;
        this.companyName = companyName;
        this.lastName = lastName;
        this.firstName = firstName;
        this.taxIdentifier = taxIdentifier;
        this.email = email;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
        this.telephone = telephone;
        this.cellPhone = cellPhone;
        this.fax = fax;
        this.tag = tag;
        this.defaultPaymentMethod = defaultPaymentMethod;
        this.lastModified = lastModified;
        this.country = country;
        this.customerType = customerType;
        this.companySpecialNumber = companySpecialNumber;
        this.defaultDiscount = defaultDiscount;
        this.defaultPaymentDateDays = defaultPaymentDateDays;
    }

    //region getters and setters

    public int getId() {
        return id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public int getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(int cellPhone) {
        this.cellPhone = cellPhone;
    }

    public int getFax() {
        return fax;
    }

    public void setFax(int fax) {
        this.fax = fax;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public PaymentMethod getDefaultPaymentMethod() {
        return defaultPaymentMethod;
    }

    public void setDefaultPaymentMethod(PaymentMethod defaultPaymentMethod) {
        this.defaultPaymentMethod = defaultPaymentMethod;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDate lastModified) {
        this.lastModified = lastModified;
    }

    public LocalDate getLastPurchaseDate() {
        return lastPurchaseDate;
    }

    public void setLastPurchaseDate(LocalDate lastPurchaseDate) {
        this.lastPurchaseDate = lastPurchaseDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public int getCompanySpecialNumber() {
        return companySpecialNumber;
    }

    public void setCompanySpecialNumber(int companySpecialNumber) {
        this.companySpecialNumber = companySpecialNumber;
    }

    public BigDecimal getDefaultDiscount() {
        return defaultDiscount;
    }

    public void setDefaultDiscount(BigDecimal defaultDiscount) {
        this.defaultDiscount = defaultDiscount;
    }

    public Integer getDefaultPaymentDateDays() {
        return defaultPaymentDateDays;
    }

    public void setDefaultPaymentDateDays(Integer defaultPaymentDateDays) {
        this.defaultPaymentDateDays = defaultPaymentDateDays;
    }

    public Set<Invoice> getInvoiceHashSet() {
        return invoiceHashSet;
    }

    public void setInvoiceHashSet(Set<Invoice> invoiceHashSet) {
        this.invoiceHashSet = invoiceHashSet;
    }

    //endregion
}
