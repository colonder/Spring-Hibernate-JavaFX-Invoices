package com.entity;

import com.entity.enums.InvoiceStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "issued_invoices")
public class Invoice
{
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "invoice_signature")
    private String invoiceSignature;

    @Column(name = "seller")
    private String seller;

    @Column(name = "issue_date")
    private Date issueDate;

    @Column(name = "charge")
    private BigDecimal charge;

    @Column(name = "paid_amount")
    private BigDecimal paidAmount;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "payment_date")
    private Date paymentDate;

    @Column(name = "payment_deadline")
    private Date paymentDeadline;

    @Column(name = "currency")
    private String currency;

    @Column(name = "status")
    private InvoiceStatus status;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "last_modified")
    private Date lastModified;

    @Column(name = "send_date")
    private Date sendDate;

    @Column(name = "notes")
    private String notes;

    @OneToMany(mappedBy = "invoice")
    private Set<BoughtProducts> boughtProductsById;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;
}
