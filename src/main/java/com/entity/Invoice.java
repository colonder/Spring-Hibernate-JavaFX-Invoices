package com.entity;

import com.entity.enums.InvoiceStatus;
import com.entity.enums.InvoiceType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "issued_invoices")
public class Invoice
{
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "invoice_type")
    private InvoiceType type;

    @Column(name = "seller")
    private String seller;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "charge")
    private BigDecimal charge;

    @Column(name = "paid_amount")
    private BigDecimal paidAmount;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    // TODO: check in code for expiration date
    @Column(name = "payment_expiration_date")
    private LocalDate paymentDeadline;

    @Column(name = "currency")
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private InvoiceStatus status;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "last_modified")
    private LocalDate lastModified;

    @Column(name = "sent_date")
    private LocalDate sentDate;

    @Column(name = "notes")
    private String notes;

    @OneToMany(mappedBy = "invoice")
    private Set<BoughtProducts> boughtProductsById;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;
}
