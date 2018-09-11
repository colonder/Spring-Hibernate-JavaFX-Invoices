package com.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "customer")
@Getter
@Setter
public class Customer
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "alias")
    private String alias;

    @Column(name = "firm_name")
    private String firmName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "tax_id", nullable = false)
    private String taxId;

    @Column(name = "address")
    private String address;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "city")
    private String city;

    @Column(name = "payment")
    private String payment;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "customer")
    private List<Templates> templates;
}
