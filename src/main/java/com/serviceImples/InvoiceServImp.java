package com.serviceImples;

import com.entity.Invoice;
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

//    @Override
//    public List<Invoice> findAllByTypeEquals(InvoiceType type) {
//        return invoiceRepository.findAllByTypeEquals(type);
//    }

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
                      LocalDate paymentDate, String status, LocalDate lastModified, String notes, int id) {
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

    @Override
    public List<Invoice> findAllByTypeIsLikeAndIssueDateIsBetweenAndStatusIsLikeAndPaymentMethodIsLike(
            String type, LocalDate startDate, LocalDate endDate, String status, String paymentMethod) {
        return invoiceRepository.findAllByTypeIsLikeAndIssueDateIsBetweenAndStatusIsLikeAndPaymentMethodIsLike(
                type, startDate, endDate, status, paymentMethod);
    }
}
