package com.service;

import com.entity.BoughtServices;

import java.util.List;

public interface IBoughtServicesService
{
    BoughtServices save(BoughtServices boughtServices);
    void delete(int id);
    List<BoughtServices> findBoughtServicesByCustomer_Id(int id);
    List<BoughtServices> findAll();
}