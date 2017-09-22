package com.service;

import com.entity.BoughtProduct;

import java.util.List;

public interface IBoughtProductsService
{
    List<BoughtProduct> findAll();
    BoughtProduct save(BoughtProduct product);
    void delete(BoughtProduct product);
}
