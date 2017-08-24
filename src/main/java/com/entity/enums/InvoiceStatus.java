package com.entity.enums;

public class InvoiceStatus {
    public static final String[] STATUS;

    static
    {
        STATUS = new String[]{"issued", "paid", "partially_paid", "rejected", "unpaid", "paid_after_deadline",
                "unpaid_expired"};
    }
}
