package dao;

import entity.Customer;
import org.springframework.data.repository.CrudRepository;

public interface ICustomerDAO extends CrudRepository<Customer, Integer>
{
    Customer findCustomerByAlias(String alias);
    void deleteCustomerByAlias(String alias);
}