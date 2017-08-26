package com.service;

import com.entity.Invoice;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IInvoiceService {
    List<Invoice> findAll(String documentType, LocalDate startDate, LocalDate endDate, String status,
                          String paymentMethod);
    Invoice save(Invoice invoice);
    void delete(Invoice invoice);
    int update(String seller, BigDecimal paidAmount, String method, LocalDate paidDate, LocalDate paymentDate,
               String status, LocalDate lastModified, String notes, int id);
    int updateSent(LocalDate sentDate, int id);
    BigDecimal sumByPeriod(LocalDate startDate, LocalDate endDate);
    int countAllByPaidDateBetween(LocalDate startDate, LocalDate endDate);
}
