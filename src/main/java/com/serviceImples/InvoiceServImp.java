package com.serviceImples;

import com.entity.Invoice;
import com.entity.enums.InvoiceStatus;
import com.entity.enums.PaymentMethod;
import com.repositories.IInvoiceRepository;
import com.service.IInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class InvoiceServImp implements IInvoiceService {
    @Autowired
    private IInvoiceRepository invoiceRepository;

    @Override
    public List<Invoice> findAll() {
        return invoiceRepository.findAll();
    }

    @Override
    @Transactional
    public Invoice save(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    @Override
    @Transactional
    public void delete(Invoice invoice) {
        invoiceRepository.delete(invoice);
    }

    @Override
    @Transactional
    public int update(String seller, BigDecimal paidAmount, PaymentMethod method, LocalDate paymentDate,
                      LocalDate paymentDeadline, InvoiceStatus status, LocalDate lastModified, String notes, int id) {
        return invoiceRepository.update(seller, paidAmount, method, paymentDate, paymentDeadline, status, lastModified,
                notes, id);
    }

    @Override
    @Transactional
    public int updateSent(LocalDate sentDate, int id) {
        return invoiceRepository.updateSent(sentDate, id);
    }

    @Override
    public BigDecimal sumByDay(LocalDate date) {
        return invoiceRepository.sumByDay(date);
    }

    @Override
    public BigDecimal sumByMonth(LocalDate date) {
        return invoiceRepository.sumByMonth(date);
    }

    @Override
    public BigDecimal sumByYear(LocalDate date) {
        return invoiceRepository.sumByYear(date);
    }
}
