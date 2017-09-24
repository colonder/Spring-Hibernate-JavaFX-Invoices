package com.repositories;

import com.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, Integer>
{
    List<Product> findAllByIsActiveTrue();
    Product findByProductName(String productName);
}
