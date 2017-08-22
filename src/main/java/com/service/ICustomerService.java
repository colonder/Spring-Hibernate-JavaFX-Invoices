package com.service;

import com.entity.Customer;
import com.entity.enums.ClientType;
import com.entity.enums.PaymentMethod;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ICustomerService {
    List<Customer> findAll();
    Customer save(Customer customer);
    void delete(Customer customer);
    int update(String alias, String lastName, String firstName, int personalId, String taxIdentifier, String email,
               String address, String postalCode, String city, int telephone, int cellPhone, int fax, String tag,
               PaymentMethod defaultPaymentMethod, String country, ClientType type, int companySpecialNumber,
               String defaultCurrency, BigDecimal defaultDiscount,
               Date defaultPaymentDateDays, int id);
    int updateNewPurchase(Date lastPurchaseDate, int id);
}
