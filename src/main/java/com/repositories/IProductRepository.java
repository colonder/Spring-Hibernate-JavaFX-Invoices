package com.repositories;

import com.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, Integer>
{
    List<Product> findAllByIsActiveTrue();

    @Modifying
    @Query("UPDATE Product p SET p.productName =?1, p.netPrice =?2, p.taxRate =?3, p.onlineSale =?4, p.isService =?5," +
            "p.isActive =?6 WHERE p.id =?7")
    int update(String productName, BigDecimal netPrice, BigDecimal taxRate, boolean onlineSale, boolean isService,
               boolean isActive, int id);
}
