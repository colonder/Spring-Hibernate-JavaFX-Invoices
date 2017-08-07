package com.service;

import com.entity.Product;

import java.math.BigDecimal;
import java.util.List;

public interface IProductService
{
    List<Product> findAll();
    Product save(Product customer);
    void delete(Product customer);
    int update(String productName, BigDecimal netPrice, BigDecimal taxRate, boolean onlineSell, boolean isService,
               boolean isActive, int id);
}
