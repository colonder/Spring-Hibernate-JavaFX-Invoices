package com.repositories;

import com.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ICustomerRepository extends JpaRepository<Customer, Integer>
{
    @Query("SELECT DISTINCT c FROM Customer c JOIN FETCH c.boughtServices s")
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