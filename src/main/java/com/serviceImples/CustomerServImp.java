package com.serviceImples;

import com.entity.Customer;
import com.entity.enums.PaymentMethod;
import com.service.ICustomerService;

import java.util.List;

public class CustomerServImp implements ICustomerService {
    @Override
    public List<Customer> findAll() {
        return null;
    }

    @Override
    public Customer save(Customer customer) {
        return null;
    }

    @Override
    public void delete(Customer customer) {

    }

    @Override
    public int update(String lastName, String firstName, String companyName, String taxIdentifier, String address, String postalCode, String city, PaymentMethod paymentMethod, boolean includeInCount, String alias, int id) {
        return 0;
    }
}
