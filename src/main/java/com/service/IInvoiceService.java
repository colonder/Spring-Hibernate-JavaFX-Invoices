package com.service;

import com.entity.Invoice;
import com.entity.enums.InvoiceStatus;
import com.entity.enums.PaymentMethod;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface IInvoiceService {
    List<Invoice> findAll();
    Invoice save(Invoice customer);
    void delete(Invoice customer);
    int update(String seller, BigDecimal paidAmount, PaymentMethod method, Date paymentDate, Date paymentDeadline,
               InvoiceStatus status, Date lastModified, String notes, int id);
    int updateSent(Date sentDate, int id);
}
