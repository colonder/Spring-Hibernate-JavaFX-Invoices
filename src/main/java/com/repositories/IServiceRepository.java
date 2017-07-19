package com.repositories;

import com.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface IServiceRepository extends JpaRepository<ServiceEntity, Integer> {
    List<ServiceEntity> findByServiceNameContaining(String string);
    @Modifying
    @Query("UPDATE ServiceEntity s SET s.serviceName=?1, s.symbol=?2, s.unit=?3, s.netUnitPrice=?4, s.vatTaxRate=?5, " +
            "s.vatTaxRate=?6 WHERE s.id =?7")
    int update(String serviceName, String symbol, String unit, BigDecimal netPrice, int vat, int id);
}
