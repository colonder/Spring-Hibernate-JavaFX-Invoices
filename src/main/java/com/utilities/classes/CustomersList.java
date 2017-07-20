package com.utilities.classes;

import com.entity.Customer;
import com.entity.Customer.CustomerProps;
import com.service.ICustomerService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomersList implements Runnable{
    public static ObservableList<CustomerProps> customerList;
    private static ICustomerService customerService;

    @Autowired
    public CustomersList(ICustomerService customerService) {
        CustomersList.customerService = customerService; // to be able to inject static field
        customerList = FXCollections.observableArrayList();
        Thread thread = new Thread(this);
        thread.start();
    }

    public static void addCustomer(CustomerProps customerProps) {
        customerService.save(customerProps.getCustomer());
        customerList.add(customerProps);
    }

    public static void removeCustomer(CustomerProps customerProps) {
        customerList.remove(customerProps);
        customerService.delete(customerProps.getCustomer());
    }

    @Override
    public void run() {
        for (Customer customer : customerService.findAll()) {
            customerList.add(customer.getCustomerProps());
        }
    }
}