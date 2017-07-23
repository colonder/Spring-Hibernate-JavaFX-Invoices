package com.service;

import com.entity.Customer;
import com.entity.PaymentMethod;

import java.util.List;

public interface ICustomerService {
    List<Customer> findAll();
    Customer save(Customer customer);
    void delete(Customer customer);
    int update(String lastName, String firstName, String companyName, String taxIdentifier, String address,
               String postalCode, String city, PaymentMethod paymentMethod, boolean includeInCount, String alias, int id);
}
