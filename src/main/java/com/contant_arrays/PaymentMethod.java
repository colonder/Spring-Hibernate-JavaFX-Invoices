package com.contant_arrays;

public class PaymentMethod {

    public static final String[] METHOD;

    static
    {
        METHOD = new String[]{null, "cash", "bank_transfer", "credit_card", "check", "cash_on_delivery", "paypal"};
    }

}