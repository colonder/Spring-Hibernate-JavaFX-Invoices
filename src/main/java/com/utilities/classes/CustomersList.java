package com.utilities.classes;

import com.entity.Customer;
import com.entity.Customer.CustomerProps;
import com.service.ICustomerService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomersList {
    public static ObservableList<CustomerProps> customerList = FXCollections.observableArrayList();
    private static ICustomerService customerService;

    @Autowired
    public CustomersList(ICustomerService customerService) {
        CustomersList.customerService = customerService; // to be able to inject static field

        for (Customer customer : customerService.findAll()) {
            customerList.add(customer.new CustomerProps());
        }
    }

    public static void addCustomer(CustomerProps customer) {
        customerService.save(customer.getCustomer());
        customerList.add(customer);
    }

    public static void removeCustomer(CustomerProps customerProps) {
        customerList.remove(customerProps);
        customerService.delete(customerProps.getCustomer());
    }
}