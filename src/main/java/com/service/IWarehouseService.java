package com.service;

import com.entity.Warehouse;

import java.util.Date;
import java.util.List;

public interface IWarehouseService
{
    List<Warehouse> findAll();
    Warehouse save(Warehouse customer);
    void delete(Warehouse customer);
    int updateAmount(int sold, int available, int id);
    int update(String productCode, Date lastSellDate, int id);
}
