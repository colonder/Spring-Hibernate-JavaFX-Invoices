package com.service;

import com.entity.BoughtServices;
import com.entity.Customer;

import java.math.BigDecimal;
import java.util.List;

public interface IBoughtServicesService
{
    BoughtServices save(BoughtServices boughtServices);
    void delete(int id);
    List<BoughtServices> findAll();
    List<BoughtServices> findBoughtServicesByCustomer(Customer customer);
    void delete(BoughtServices boughtServices);
    int update(BigDecimal value, int id);
}