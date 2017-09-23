package com.entity;

import com.enums.InvoiceStatus;
import com.enums.InvoiceType;
import com.enums.PaymentMethod;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "issued_invoices")
@DynamicUpdate
public class Invoice extends BaseAbstractEntity
{
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Column(name = "invoice_type")
    @Enumerated(EnumType.STRING)
    private InvoiceType type;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "net_value")
    private BigDecimal netValue;

    @Column(name = "vat_value")
    private BigDecimal vatValue;

    @Column(name = "discount_value")
    private BigDecimal discountValue;

    @Column(name = "gross_value")
    private BigDecimal grossValue;

    @Column(name = "paid_amount")
    private BigDecimal paidAmount;

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(name = "paid_date")
    private LocalDate paidDate;

    // TODO: check in code for expiration date
    @Column(name = "payment_date_days")
    private Integer paymentDateDays;

    @Column(name = "currency")
    private String currency;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;

    @Column(name = "creation_date", updatable = false, nullable = false)
    private LocalDate creationDate;

    @Column(name = "sale_date")
    private LocalDate saleDate;

    @Column(name = "location")
    private String location;

    @Column(name = "last_modified")
    private LocalDate lastModified;

    @Column(name = "sent_date")
    private LocalDate sentDate;

    @Column(name = "notes")
    private String remarks;

    @OneToMany(mappedBy = "invoice", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<BoughtProduct> boughtProductSet;

    // TODO: add cascade types
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", referencedColumnName = "id")
    private Seller seller;

    public Invoice()
    {
        // TODO: initialize invoice number, currency from settings
        this.issueDate = LocalDate.now();
        this.creationDate = LocalDate.now();
        this.saleDate = LocalDate.now();
        this.lastModified = LocalDate.now();
        this.netValue = BigDecimal.ZERO;
        this.vatValue = BigDecimal.ZERO;
        this.discountValue = BigDecimal.ZERO;
        this.grossValue = BigDecimal.ZERO;
        this.paidAmount = BigDecimal.ZERO;
        this.boughtProductSet = new HashSet<>();
        this.status = InvoiceStatus.ISSUED;
    }

    public Invoice(InvoiceType type)
    {
        this();
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public InvoiceType getType() {
        return type;
    }

    public Seller getSeller() {
        return seller;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public BigDecimal getNetValue() {
        return netValue;
    }

    public BigDecimal getVatValue() {
        return vatValue;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public BigDecimal getGrossValue() {
        return grossValue;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public LocalDate getPaidDate() {
        return paidDate;
    }

    public Integer getPaymentDateDays() {
        return paymentDateDays;
    }

    public String getCurrency() {
        return currency;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public LocalDate getLastModified() {
        return lastModified;
    }

    public LocalDate getSentDate() {
        return sentDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public Set<BoughtProduct> getBoughtProductSet() {
        return boughtProductSet;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getLocation() {
        return location;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }
}
