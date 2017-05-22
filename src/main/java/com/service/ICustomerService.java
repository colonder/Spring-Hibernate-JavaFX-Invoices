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
    List<Customer> findAllByFirstNameContaining(String string);
    List<Customer> findAllByLastNameContaining(String string);
    List<Customer> findAllByCompanyNameContaining(String string);
    List<Customer> findAllByTaxIdentifierContaining(String string);
    List<Customer> findAllByAddressContaining(String string);
    List<Customer> findAllByPostalCodeContaining(String string);
    List<Customer> findAllByCityContaining(String string);
    List<Customer> findAllByPaymentMethod(String string);
    List<Customer> findAllByIncludeInCount(String string);
    List<Customer> findAllByAliasContaining(String string);
}
