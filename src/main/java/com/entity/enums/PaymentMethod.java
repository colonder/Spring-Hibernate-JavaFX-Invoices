package com.entity.enums;

public class PaymentMethod {

    public static final String[] METHOD;

    static
    {
        METHOD = new String[]{"cash", "bank_transfer", "credit_card", "check", "cash_on_delivery", "paypal"};
    }

}