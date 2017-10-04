package com.service;

import com.entity.Customer;
import com.enums.CustomerType;

import java.util.List;

public interface ICustomerService {
    List<Customer> findAll();
    List<Customer> findAll(CustomerType type, String[] tags);
    Customer save(Customer customer);
    void delete(Customer customer);
}
