package com.serviceImples;

import com.contant_arrays.InvoiceStatus;
import com.contant_arrays.InvoiceType;
import com.contant_arrays.PaymentMethod;
import com.entity.Invoice;
import com.repositories.IInvoiceRepository;
import com.service.IInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.entity.InvoiceSpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.where;

@Service
public class InvoiceServImp implements IInvoiceService {
    @Autowired
    private IInvoiceRepository invoiceRepository;

    @Override
    public List<Invoice> findAll(InvoiceType documentType, LocalDate startDate, LocalDate endDate, InvoiceStatus status,
                                 PaymentMethod paymentMethod) {
        return invoiceRepository.findAll(where(withDocumentType(documentType))
                .and(betweenPeriod(startDate, endDate))
                .and(withStatus(status))
                .and(withPaymentMethod(paymentMethod)));
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
    public int update(String seller, BigDecimal paidAmount, String method, LocalDate paidDate,
                      LocalDate paymentDate, InvoiceStatus status, LocalDate lastModified, String notes, int id) {
        return invoiceRepository.update(seller, paidAmount, method, paidDate, paymentDate, status, lastModified,
                notes, id);
    }

    @Override
    @Transactional
    public int updateSent(LocalDate sentDate, int id) {
        return invoiceRepository.updateSent(sentDate, id);
    }

    @Override
    public BigDecimal sumByPeriod(LocalDate startDate, LocalDate endDate) {
        return invoiceRepository.sumByPeriod(startDate, endDate);
    }

    @Override
    public int countAllByPaidDateBetween(LocalDate startDate, LocalDate endDate) {
        return invoiceRepository.countAllByPaidDateBetween(startDate, endDate);
    }

}
