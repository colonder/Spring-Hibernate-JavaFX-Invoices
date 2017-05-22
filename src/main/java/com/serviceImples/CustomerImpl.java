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

    @Override
    public List<Customer> findAllByFirstNameContaining(String string) {
        return customerRepository.findAllByFirstNameContaining(string);
    }

    @Override
    public List<Customer> findAllByLastNameContaining(String string) {
        return customerRepository.findAllByLastNameContaining(string);
    }

    @Override
    public List<Customer> findAllByCompanyNameContaining(String string) {
        return customerRepository.findAllByCompanyNameContaining(string);
    }

    @Override
    public List<Customer> findAllByTaxIdentifierContaining(String string) {
        return customerRepository.findAllByTaxIdentifierContaining(string);
    }

    @Override
    public List<Customer> findAllByAddressContaining(String string) {
        return customerRepository.findAllByAddressContaining(string);
    }

    @Override
    public List<Customer> findAllByPostalCodeContaining(String string) {
        return customerRepository.findAllByPostalCodeContaining(string);
    }

    @Override
    public List<Customer> findAllByCityContaining(String string) {
        return customerRepository.findAllByCityContaining(string);
    }

    @Override
    public List<Customer> findAllByPaymentMethod(String string) {
        return customerRepository.findAllByPaymentMethod(string);
    }

    @Override
    public List<Customer> findAllByIncludeInCount(String string) {
        return customerRepository.findAllByIncludeInCount(string);
    }

    @Override
    public List<Customer> findAllByAliasContaining(String string) {
        return customerRepository.findAllByAliasContaining(string);
    }
}
