package com.service;

import com.entity.Product;

import java.util.List;

public interface IProductService
{
    List<Product> findAllByIsActiveTrue();
    Product findByProductName(String name);
    Product save(Product product);
    void delete(Product product);
}
