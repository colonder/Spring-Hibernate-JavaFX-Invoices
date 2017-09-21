package com.enums;

import com.google.common.collect.HashBiMap;

public enum InvoiceType {

    ORDINARY, PROFORMA, CORRECTIVE, EXPENSE;

    public static final HashBiMap<String, InvoiceType> typeMap;

    static
    {
        typeMap = HashBiMap.create();
        typeMap.put("All", null);
        typeMap.put("Ordinary", ORDINARY);
        typeMap.put("Pro forma", PROFORMA);
        typeMap.put("Corrective", CORRECTIVE);
        typeMap.put("Expense", EXPENSE);
    }
}
