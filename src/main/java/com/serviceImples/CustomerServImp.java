package com.serviceImples;

import com.entity.Customer;
import com.entity.enums.ClientType;
import com.entity.enums.PaymentMethod;
import com.repositories.ICustomerRepository;
import com.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
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
    @Transactional
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    @Transactional
    public void delete(Customer customer) {
        customerRepository.delete(customer);
    }

    @Override
    @Transactional
    public int update(String alias, String lastName, String firstName, int personalId, String taxIdentifier,
                      String email, String address, String postalCode, String city, int telephone, int cellPhone,
                      int fax, String tag, PaymentMethod defaultPaymentMethod, String country, ClientType type,
                      int companySpecialNumber, String defaultCurrency,
                      BigDecimal defaultDiscount, Date defaultPaymentDateDays, int id) {
        return customerRepository.update(alias, lastName, firstName, personalId, taxIdentifier, email, address,
                postalCode, city, telephone, cellPhone, fax, tag, defaultPaymentMethod, country, type,
                companySpecialNumber, defaultCurrency, defaultDiscount,
                defaultPaymentDateDays, id);
    }

    @Override
    @Transactional
    public int updateNewPurchase(Date lastPurchaseDate, int id) {
        return customerRepository.updateNewPurchase(lastPurchaseDate, id);
    }
}
