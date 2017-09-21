package com.enums;

import com.google.common.collect.HashBiMap;

public enum PaymentMethod {

    CASH, BANK_TRANSFER, CREDIT_CARD, CHECK, CASH_ON_DELIVERY, PAYPAL;

    public static final HashBiMap<String, PaymentMethod> paymentMap;

    static
    {
        paymentMap = HashBiMap.create();
        paymentMap.put("All", null);
        paymentMap.put("Cash", CASH);
        paymentMap.put("Bank transfer", BANK_TRANSFER);
        paymentMap.put("Credit card", CREDIT_CARD);
        paymentMap.put("Check", CHECK);
        paymentMap.put("Cash on delivery", CASH_ON_DELIVERY);
        paymentMap.put("Paypal", PAYPAL);
    }
}