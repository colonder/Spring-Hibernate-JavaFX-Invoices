package com.service;

import com.entity.Customer;

import java.util.List;

public interface ICustomerService
{
    List<Customer> findAll();
    Customer findOne(int id);
    Customer findByAlias(String alias);
    void delete(int id);
    Customer save(Customer customer);
    void delete(Customer customer);
}
