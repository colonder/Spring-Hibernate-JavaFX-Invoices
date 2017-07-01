package com.repositories;

import com.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICustomerRepository extends JpaRepository<Customer, Integer>
{
    List<Customer> findAll();
    Customer findByAlias(String alias);
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