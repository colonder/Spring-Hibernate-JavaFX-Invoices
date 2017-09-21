package com.repositories;

import com.entity.Invoice;
import com.enums.InvoiceStatus;
import com.enums.InvoiceType;
import com.enums.PaymentMethod;
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

    // TODO: add more method parameters

    @Modifying
    @Query("UPDATE Invoice i SET i.type=?1, i.netValue=?2, i.vatValue=?3, i.discountValue=?4, i.grossValue=?5, " +
            "i.paidAmount =?6, i.paymentMethod=?7, i.paidDate=?8, i.paymentDate =?9, i.status =?10, " +
            "i.lastModified =?11, i.notes =?10 WHERE i.id =?13")
    int update(InvoiceType type, BigDecimal netVal, BigDecimal vatVal, BigDecimal discountVal, BigDecimal grossVal,
               BigDecimal paidAmount, PaymentMethod method, LocalDate paidDate, LocalDate paymentDate,
               InvoiceStatus status, LocalDate lastModified, String notes, int id);

    @Modifying
    @Query("UPDATE Invoice i SET i.sentDate =?1 where i.id =?2")
    int updateSent(LocalDate sentDate, int id);

    @Query("SELECT SUM(i.grossValue) FROM Invoice i WHERE i.paidDate BETWEEN :startDate AND :endDate")
    BigDecimal sumByPeriod(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    int countAllByPaidDateBetween(LocalDate startDate, LocalDate endDate);
}