package com.repositories;

import com.entity.Customer;
import com.entity.enums.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ICustomerRepository extends JpaRepository<Customer, Integer> {
    List<Customer> findAll();

    @Modifying
    @Query("UPDATE Customer c SET c.lastName=?1, c.firstName =?2, c.companyName =?3, c.taxIdentifier =?4, c.address =?5," +
            "c.postalCode =?6, c.city =?7, c.paymentMethod =?8, c.includeInCount =?9, c.alias =?10 WHERE c.id =?11")
    int update(String lastName, String firstName, String companyName, String taxIdentifier, String address,
               String postalCode, String city, PaymentMethod paymentMethod, boolean includeInCount, String alias, int id);
}