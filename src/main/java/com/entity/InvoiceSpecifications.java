package com.entity;

import com.contant_arrays.InvoiceStatus;
import com.contant_arrays.InvoiceType;
import com.contant_arrays.PaymentMethod;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class InvoiceSpecifications {
    public static Specification<Invoice> withDocumentType(InvoiceType type)
    {
        if (type == null)
            return null;

        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("type"), type);
    }

    public static Specification<Invoice> betweenPeriod(LocalDate startDate, LocalDate endDate)
    {
        if (startDate == null || endDate == null)
            return null;

        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.between(root.get("paidDate"), startDate,
                endDate);
    }

    public static Specification<Invoice> withStatus(InvoiceStatus status)
    {
        if (status == null)
            return null;

        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<Invoice> withPaymentMethod(PaymentMethod method)
    {
        if (method == null)
            return null;

        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("paymentMethod"), method);
    }
}
