package com.serviceImples;

import com.entity.Customer;
import com.enums.CustomerType;
import com.repositories.ICustomerRepository;
import com.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.entity.CustomerSpecifications.withCustomerType;
import static org.springframework.data.jpa.domain.Specifications.where;

@Service
public class CustomerServImp implements ICustomerService {
    @Autowired
    private ICustomerRepository customerRepository;


    @Override
    public List<Customer> findAll(CustomerType type) {
        return customerRepository.findAll(where(withCustomerType(type)));
    }

    @Override
    @Transactional
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    @Transactional
    public void delete(Customer customer) {
        customerRepository.delete(customer);
    }
}
