package com.service;

import com.contant_arrays.InvoiceStatus;
import com.contant_arrays.InvoiceType;
import com.contant_arrays.PaymentMethod;
import com.entity.Invoice;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IInvoiceService {
    List<Invoice> findAll(InvoiceType documentType, LocalDate startDate, LocalDate endDate, InvoiceStatus status,
                          PaymentMethod paymentMethod);
    Invoice save(Invoice invoice);
    void delete(Invoice invoice);
    int update(String seller, BigDecimal paidAmount, String method, LocalDate paidDate, LocalDate paymentDate,
               InvoiceStatus status, LocalDate lastModified, String notes, int id);
    int updateSent(LocalDate sentDate, int id);
    BigDecimal sumByPeriod(LocalDate startDate, LocalDate endDate);
    int countAllByPaidDateBetween(LocalDate startDate, LocalDate endDate);
}
