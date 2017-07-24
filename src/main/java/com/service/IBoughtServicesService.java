package com.service;

import com.entity.BoughtServices;
import com.entity.Customer;
import org.hibernate.exception.ConstraintViolationException;
import org.postgresql.util.PSQLException;

import java.math.BigDecimal;
import java.util.List;

public interface IBoughtServicesService {
    BoughtServices save(BoughtServices boughtServices);

    List<BoughtServices> findBoughtServicesByCustomer(Customer customer);

    void delete(BoughtServices boughtServices);

    int update(BigDecimal value, int id) throws PSQLException, ConstraintViolationException;
}