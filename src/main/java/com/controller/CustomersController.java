package com.controller;

import com.entity.Customer;
import com.repositories.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class CustomersController implements IUtilities<Customer>
{
    @Autowired
    private ICustomerRepository customerDAO;

    public void manageCustomers()
    {
        List<Customer> allCustomers = convertIterableToCollection(customerDAO.findAll());
    }

    public void showFormForAdd()
    {
        // create model attribute to bind form data
        Customer theCustomer = new Customer();
    }

    public void showFormForUpdate(int customerId)
    {
        // get the customer from service
        Customer theCustomer = customerDAO.findOne(customerId);
    }

    public void deleteCustomer(int customerId)
    {
        //delete the customer
        customerDAO.delete(customerId);
    }

    public void saveCustomer(Customer customer) {
        //save the customer using our service
        customerDAO.save(customer);
    }

    @Override
    public List<Customer> convertIterableToCollection(Iterable<Customer> iterable) {
        List<Customer> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }
}
