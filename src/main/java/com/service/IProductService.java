package com.service;

import com.entity.Product;

import java.util.List;

public interface IProductService
{
    List<Product> findAll(String phrase, Boolean isService, String[] tags, Boolean isActive);
    Product save(Product product);
    void delete(Product product);
    List<Product> findAllByIsActiveTrue();
    Product findByProductName(String productName);
}
