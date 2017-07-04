package com.utilities.classes;

import com.entity.Customer;
import com.entity.Customer.CustomerProps;
import com.service.ICustomerService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomersList
{

    private static ICustomerService customerService;

    public static ObservableList<CustomerProps> customerList = FXCollections.observableArrayList();

    @Autowired
    public CustomersList(ICustomerService customerService)
    {
        CustomersList.customerService = customerService; // to be able to inject static field

        for (Customer customer : customerService.findAll())
        {
            customerList.add(customer.new CustomerProps());
        }
    }

    public static void addCustomer(Customer customer)
    {
        customerService.save(customer);
        customerList.add(customer.new CustomerProps());
    }

    public static void removeCustomer(CustomerProps customerProps)
    {
        customerList.remove(customerProps);
        customerService.delete(customerProps.getCustomer().getId());
    }
}