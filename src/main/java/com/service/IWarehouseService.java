package com.service;

import com.entity.Warehouse;

import java.util.Date;
import java.util.List;

public interface IWarehouseService
{
    List<Warehouse> findAll();
    Warehouse save(Warehouse warehouse);
    void delete(Warehouse warehouse);
    int updateAmount(int sold, int available, int id);
    int update(String productCode, Date lastSaleDate, int id);
}
