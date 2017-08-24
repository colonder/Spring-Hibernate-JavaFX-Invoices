package com.service;

import com.entity.Warehouse;

import java.time.LocalDate;
import java.util.List;

public interface IWarehouseService
{
    List<Warehouse> findAll();
    Warehouse save(Warehouse warehouse);
    void delete(Warehouse warehouse);
    int updateAmount(int sold, int available, int id);
    int update(String productCode, LocalDate lastSaleDate, int id);
}
