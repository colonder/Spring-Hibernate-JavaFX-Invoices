package com.repositories;

import com.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;

@Repository
public interface IInvoiceRepository extends JpaRepository<Invoice, Integer>, JpaSpecificationExecutor<Invoice> {

    @Query("SELECT SUM(i.grossValue) FROM Invoice i WHERE i.paidDate BETWEEN :startDate AND :endDate")
    BigDecimal sumByPeriod(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    int countAllByPaidDateBetween(LocalDate startDate, LocalDate endDate);
}