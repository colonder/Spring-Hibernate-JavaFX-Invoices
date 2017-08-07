package com.repositories;

import com.entity.BoughtProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Date;

public interface IBoughtProductsRepository extends JpaRepository<BoughtProducts, Integer> {
    @Modifying
    @Query("UPDATE BoughtProducts b SET b.quantity =?1, b.netValue =?2, b.taxValue =?3, b.grossValue =?4, b.sellDate =?5 WHERE b.id =?6")
    int update(BigDecimal quantity, BigDecimal netValue, BigDecimal taxValue, BigDecimal grossValue, Date sellDate,
               int id);
}
