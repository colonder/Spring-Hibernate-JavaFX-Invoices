package com.enums;

import java.util.HashMap;

public enum InvoiceType {

    ORDINARY, PROFORMA, CORRECTIVE, EXPENSE;

    public static final HashMap<String, InvoiceType> typeMap;

    static
    {
        typeMap = new HashMap<>();
        typeMap.put("All", null);
        typeMap.put("Ordinary", ORDINARY);
        typeMap.put("Pro forma", PROFORMA);
        typeMap.put("Corrective", CORRECTIVE);
        typeMap.put("Expense", EXPENSE);
    }
}
