package com.service;

import com.entity.Invoice;
import com.enums.InvoiceStatus;
import com.enums.InvoiceType;
import com.enums.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IInvoiceService {
    List<Invoice> findAll(InvoiceType documentType, LocalDate startDate, LocalDate endDate, InvoiceStatus status,
                          PaymentMethod paymentMethod);
    Invoice save(Invoice invoice);
    void delete(Invoice invoice);
    int update(InvoiceType type, BigDecimal netVal, BigDecimal vatVal, BigDecimal discountVal, BigDecimal grossVal,
               BigDecimal paidAmount, PaymentMethod method, LocalDate paidDate, LocalDate paymentDate,
               InvoiceStatus status, LocalDate lastModified, String notes, int id);
    int updateSent(LocalDate sentDate, int id);
    BigDecimal sumByPeriod(LocalDate startDate, LocalDate endDate);
    int countAllByPaidDateBetween(LocalDate startDate, LocalDate endDate);
}
