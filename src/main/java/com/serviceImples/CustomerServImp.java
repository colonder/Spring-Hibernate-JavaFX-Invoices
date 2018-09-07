package com.serviceImples;

import com.entity.Customer;
import com.repositories.ICustomerRepository;
import com.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServImp implements ICustomerService {
    @Autowired
    private ICustomerRepository customerRepository;


    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer update(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void delete(Customer customer) {
        customerRepository.delete(customer);
    }

    @Override
    public void deleteInBatch(List<Customer> entities) {
        customerRepository.deleteInBatch(entities);
    }

    @Override
    public Customer find(Long id) {
        return customerRepository.findById(id).get();
    }
}
