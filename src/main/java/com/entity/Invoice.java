package com.entity;

import com.enums.InvoiceStatus;
import com.enums.InvoiceType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "issued_invoices")
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

    @Column(name = "seller")
    private String seller;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "net_value")
    private BigDecimal netValue;

    @Column(name = "tax_value")
    private BigDecimal taxValue;

    @Column(name = "discount_value")
    private BigDecimal discountValue;

    @Column(name = "gross_value")
    private BigDecimal grossValue;

    @Column(name = "paid_amount")
    private BigDecimal paidAmount;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "paid_date")
    private LocalDate paidDate;

    // TODO: check in code for expiration date
    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(name = "currency")
    private String currency;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "sale_date")
    private LocalDate saleDate;

    @Column(name = "last_modified")
    private LocalDate lastModified;

    @Column(name = "sent_date")
    private LocalDate sentDate;

    @Column(name = "notes")
    private String notes;

    @OneToMany(mappedBy = "invoice", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<BoughtProducts> boughtProductsSet;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    public Invoice()
    {
        // TODO: initialize invoice number from set policy
        this.issueDate = LocalDate.now();
        this.netValue = BigDecimal.ZERO;
        this.taxValue = BigDecimal.ZERO;
        this.discountValue = BigDecimal.ZERO;
        this.grossValue = BigDecimal.ZERO;
        this.paidAmount = BigDecimal.ZERO;
        this.creationDate = LocalDate.now();
        this.saleDate = LocalDate.now();
        this.lastModified = LocalDate.now();
        boughtProductsSet = new HashSet<>();
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

    public String getSeller() {
        return seller;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public BigDecimal getNetValue() {
        return netValue;
    }

    public BigDecimal getTaxValue() {
        return taxValue;
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

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public LocalDate getPaidDate() {
        return paidDate;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
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

    public String getNotes() {
        return notes;
    }

    public Set<BoughtProducts> getBoughtProductsSet() {
        return boughtProductsSet;
    }

    public Customer getCustomer() {
        return customer;
    }
}
