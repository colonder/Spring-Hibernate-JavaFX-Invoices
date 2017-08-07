package com.repositories;

import com.entity.Customer;
import com.entity.enums.ClientType;
import com.entity.enums.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ICustomerRepository extends JpaRepository<Customer, Integer> {
    List<Customer> findAll();

    @Modifying
    @Query("UPDATE Customer c SET c.alias =?1, c.lastName =?2, c.firstname =?3, c.personalId =?4, c.taxIdentifier =?5," +
            " c.email =?6, c.email =?7, c.address =?8, c.postalCode =?9, c.city =?10, c.telephone =?11, c.cellPhone =?12," +
            " c.fax =?13, c.tag =?14, c.defaultPaymentMethod =?15, c.country =?16, c.clientType =?17, " +
            "c.companySpecialNumber =?18, c.bankName =?19, c.defaultCurrency =?20, c.bankAccountNumber =?21, " +
            "c.defaultDiscount =?22, c.defaultPaymentDateDays =?23 WHERE c.id =?24")
    int update(String alias, String lastName, String firstName, int personalId, String taxIdentifier, String email,
               String address, String postalCode, String city, int telephone, int cellPhone, int fax, String tag,
               PaymentMethod defaultPaymentMethod, String country, ClientType type, int companySpecialNumber,
               String bankName, String defaultCurrency, int bankAccountNumber, BigDecimal defaultDiscount,
               Date defaultPaymentDateDays, int id);

    @Modifying
    @Query("UPDATE Customer c SET c.lastPurchaseDate =?1 WHERE c.id =?2")
    int updateNewPurchase(Date lastPurchaseDate, int id);
}