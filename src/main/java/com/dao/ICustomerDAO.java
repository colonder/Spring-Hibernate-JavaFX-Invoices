package com.dao;

import com.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICustomerDAO extends CrudRepository<Customer, Integer>
{
    Customer findCustomerByAlias(String alias);
    void deleteCustomerByAlias(String alias);
}