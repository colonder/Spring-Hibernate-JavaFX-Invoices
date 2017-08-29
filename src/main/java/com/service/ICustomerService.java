package com.service;

import com.entity.Customer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ICustomerService {
    List<Customer> findAll();
    Customer save(Customer customer);
    void delete(Customer customer);
    int update(String alias, String lastName, String firstName, long personalId, String taxIdentifier, String email,
               String address, String postalCode, String city, int telephone, int cellPhone, int fax, String tag,
               String defaultPaymentMethod, String country, String type, int companySpecialNumber,
               String defaultCurrency, BigDecimal defaultDiscount,
               LocalDate defaultPaymentDateDays, int id);
    int updateNewPurchase(LocalDate lastPurchaseDate, int id);
}
