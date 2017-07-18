package com.serviceImples;

import com.entity.Customer;
import com.entity.PaymentMethod;
import com.repositories.ICustomerRepository;
import com.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerImpl implements ICustomerService {
    @Autowired
    private ICustomerRepository customerRepository;

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer findOne(int id) {
        return customerRepository.findOne(id);
    }

    @Transactional
    public void delete(int id) {
        customerRepository.delete(id);
    }

    @Transactional
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Transactional
    public void delete(Customer customer) {
        customerRepository.delete(customer);
    }

    @Override
    @Transactional
    public int update(String lastName, String firstName, String companyName, String taxIdentifier, String address,
                      String postalCode, String city, PaymentMethod paymentMethod, boolean includeInCount, String alias,
                      int id) {
        return customerRepository.update(lastName, firstName, companyName, taxIdentifier, address, postalCode, city,
                paymentMethod, includeInCount, alias, id);
    }
}
