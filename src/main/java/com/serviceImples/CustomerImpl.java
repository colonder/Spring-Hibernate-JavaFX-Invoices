package com.serviceImples;

import com.entity.Customer;
import com.repositories.ICustomerRepository;
import com.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerImpl implements ICustomerService
{
    @Autowired
    private ICustomerRepository customerRepository;

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer findOne(int id) {
        return customerRepository.findOne(id);
    }

    @Override
    public Customer findByAlias(String alias) {
        return customerRepository.findByAlias(alias);
    }

    @Transactional
    public void delete(int id) {
        customerRepository.delete(id);
    }

    @Transactional
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }
}
