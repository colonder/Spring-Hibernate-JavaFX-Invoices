package com.service;

import com.entity.Customer;

import java.util.List;

public interface ICustomerService {
    List<Customer> findAll();
    Customer save(Customer customer);
    void delete(Customer customer);
}
