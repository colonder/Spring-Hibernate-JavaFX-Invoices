package com.repositories;

import com.entity.Invoice;
import com.entity.enums.InvoiceStatus;
import com.entity.enums.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IInvoiceRepository extends JpaRepository<Invoice, Integer> {
    List<Invoice> findAll();
    @Modifying
    @Query("UPDATE Invoice i SET i.seller =?1, i.paidAmount =?2, i.paymentMethod=?3, i.paymentDate=?4, " +
            "i.paymentDeadline =?5, i.status =?6, i.lastModified =?7, i.notes =?8 WHERE i.id =?9")
    int update(String seller, BigDecimal paidAmount, PaymentMethod method, LocalDate paymentDate, LocalDate paymentDeadline,
               InvoiceStatus status, LocalDate lastModified, String notes, int id);

    @Modifying
    @Query("UPDATE Invoice i SET i.sentDate =?1 where i.id =?2")
    int updateSent(LocalDate sentDate, int id);

    @Query("SELECT SUM(i.charge) FROM Invoice i WHERE EXTRACT(DAY FROM issued_date) =?1")
    BigDecimal sumByDay(LocalDate date);

    @Query("SELECT SUM(i.charge) FROM Invoice i WHERE EXTRACT(MONTH FROM issued_date) =?1")
    BigDecimal sumByMonth(LocalDate date);

    @Query("SELECT SUM(i.charge) FROM Invoice i WHERE EXTRACT(YEAR FROM issued_date) =?1")
    BigDecimal sumByYear(LocalDate date);
}