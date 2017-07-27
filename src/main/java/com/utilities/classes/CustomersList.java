package com.utilities.classes;

import com.entity.Customer;
import com.service.ICustomerService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomersList implements Runnable {
    public static ObservableList<Customer> customerList;
    private static ICustomerService customerService;

    @Autowired
    public CustomersList(ICustomerService customerService) {
        CustomersList.customerService = customerService; // to be able to inject static field
        customerList = FXCollections.observableArrayList();
        Thread thread = new Thread(this);
        thread.start();
    }

    public static void addCustomer(Customer customer) {
        customerService.save(customer);
        customerList.add(customer);
    }

    public static void removeCustomer(Customer customer) {
        customerList.remove(customer);
        customerService.delete(customer);
    }

    @Override
    public void run() {

        customerList.addAll(customerService.findAll());
    }
}