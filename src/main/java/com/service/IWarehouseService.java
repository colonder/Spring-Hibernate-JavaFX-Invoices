package com.service;

import com.entity.Warehouse;

import java.util.List;

public interface IWarehouseService
{
    List<Warehouse> findAll();
    Warehouse save(Warehouse warehouse);
    void delete(Warehouse warehouse);
}
