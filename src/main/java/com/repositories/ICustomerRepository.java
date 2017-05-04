package com.repositories;

import com.entity.Customer;
import org.springframework.data.repository.CrudRepository;

public interface ICustomerRepository extends CrudRepository<Customer, Integer>
{
    Customer findCustomerByAlias(String alias);
    void deleteCustomerByAlias(String alias);
}