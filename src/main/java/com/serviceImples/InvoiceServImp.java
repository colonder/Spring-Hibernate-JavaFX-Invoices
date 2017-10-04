package com.serviceImples;

import com.entity.Invoice;
import com.enums.InvoiceStatus;
import com.enums.InvoiceType;
import com.enums.PaymentMethod;
import com.repositories.IInvoiceRepository;
import com.service.IInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.specifications.InvoiceSpecifications.*;
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
    public BigDecimal sumByPeriod(LocalDate startDate, LocalDate endDate) {
        return invoiceRepository.sumByPeriod(startDate, endDate);
    }

    @Override
    public int countAllByPaidDateBetween(LocalDate startDate, LocalDate endDate) {
        return invoiceRepository.countAllByPaidDateBetween(startDate, endDate);
    }

}
