package com.serviceImples;

import com.entity.Customer;
import com.repositories.ICustomerRepository;
import com.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerImpl implements ICustomerService
{
    @Autowired
    private ICustomerRepository customerRepository;

    @Override
    public Iterable<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer findOne(int id) {
        return customerRepository.findOne(id);
    }

    @Override
    @Transactional
    public void delete(int id) {
        customerRepository.delete(id);
    }

    @Override
    @Transactional
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }
}
