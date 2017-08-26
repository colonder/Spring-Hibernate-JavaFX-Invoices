package com.repositories;

import com.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer, Integer> {
    List<Customer> findAll();

    @Modifying
    @Query("UPDATE Customer c SET c.alias =?1, c.lastName =?2, c.firstName =?3, c.personalId =?4, c.taxIdentifier =?5," +
            " c.email =?6, c.email =?7, c.address =?8, c.postalCode =?9, c.city =?10, c.telephone =?11, c.cellPhone =?12," +
            " c.fax =?13, c.tag =?14, c.defaultPaymentMethod =?15, c.country =?16, c.customerType =?17, " +
            "c.companySpecialNumber =?18, c.defaultCurrency =?20, c.defaultDiscount =?21, c.defaultPaymentDateDays =?22" +
            " WHERE c.id =?23")
    int update(String alias, String lastName, String firstName, int personalId, String taxIdentifier, String email,
               String address, String postalCode, String city, int telephone, int cellPhone, int fax, String tag,
               String defaultPaymentMethod, String country, String type, int companySpecialNumber,
               String defaultCurrency, BigDecimal defaultDiscount,
               LocalDate defaultPaymentDateDays, int id);

    @Modifying
    @Query("UPDATE Customer c SET c.lastPurchaseDate =?1 WHERE c.id =?2")
    int updateNewPurchase(LocalDate lastPurchaseDate, int id);
}