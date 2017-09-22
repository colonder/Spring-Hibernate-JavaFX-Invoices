package com.service;

import com.entity.Product;

import java.util.List;

public interface IProductService
{
    List<Product> findAllByIsActiveTrue();
    Product save(Product product);
    void delete(Product product);
}
