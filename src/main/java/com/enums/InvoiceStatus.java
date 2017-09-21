package com.enums;

import com.google.common.collect.HashBiMap;

// TODO: internationalize enum, resource bundle
public enum InvoiceStatus {
    ISSUED, PAID, PARTIALLY_PAID, REJECTED, UNPAID, PAID_AFTER_DEADLINE, UNPAID_EXPIRED;
    
    public static final HashBiMap<String, InvoiceStatus> statusMap;
    
    static
    {
        statusMap = HashBiMap.create();
        statusMap.put("All", null);
        statusMap.put("Issued", ISSUED);
        statusMap.put("Paid", PAID);
        statusMap.put("Partially paid", PARTIALLY_PAID);
        statusMap.put("Rejected", REJECTED);
        statusMap.put("Unpaid", UNPAID);
        statusMap.put("Paid after deadline", PAID_AFTER_DEADLINE);
        statusMap.put("Unpaid expired", UNPAID_EXPIRED);
    }
}
