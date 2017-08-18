package com.service;

import com.entity.Invoice;
import com.entity.enums.InvoiceStatus;
import com.entity.enums.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IInvoiceService {
    List<Invoice> findAll();
    Invoice save(Invoice invoice);
    void delete(Invoice invoice);
    int update(String seller, BigDecimal paidAmount, PaymentMethod method, LocalDate paymentDate, LocalDate paymentDeadline,
               InvoiceStatus status, LocalDate lastModified, String notes, int id);
    int updateSent(LocalDate sentDate, int id);
    BigDecimal sumByPeriod(LocalDate startDate, LocalDate endDate);
}
