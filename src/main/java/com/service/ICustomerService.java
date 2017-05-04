package com.service;

import com.entity.Customer;

public interface ICustomerService
{
    Iterable<Customer> findAll();
    Customer findOne(int id);
    void delete(int id);
    Customer save(Customer customer);
}
