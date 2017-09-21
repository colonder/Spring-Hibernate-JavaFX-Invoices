package com.repositories;

import com.entity.Invoice;
import com.enums.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;

@Repository
public interface IInvoiceRepository extends JpaRepository<Invoice, Integer>, JpaSpecificationExecutor<Invoice> {

    @Modifying
    @Query("UPDATE Invoice i SET i.seller =?1, i.paidAmount =?2, i.paymentMethod=?3, i.paidDate=?4, " +
            "i.paymentDate =?5, i.status =?6, i.lastModified =?7, i.notes =?8 WHERE i.id =?9")
    int update(String seller, BigDecimal paidAmount, String method, LocalDate paidDate, LocalDate paymentDate,
               InvoiceStatus status, LocalDate lastModified, String notes, int id);

    @Modifying
    @Query("UPDATE Invoice i SET i.sentDate =?1 where i.id =?2")
    int updateSent(LocalDate sentDate, int id);

    @Query("SELECT SUM(i.grossValue) FROM Invoice i WHERE i.paidDate BETWEEN :startDate AND :endDate")
    BigDecimal sumByPeriod(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    int countAllByPaidDateBetween(LocalDate startDate, LocalDate endDate);
}