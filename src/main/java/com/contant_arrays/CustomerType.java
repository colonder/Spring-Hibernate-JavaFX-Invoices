package com.contant_arrays;

import java.util.HashMap;

public enum CustomerType
{
    COMPANY, PERSON;

    public static final HashMap<String, CustomerType> customerMap;

    static{
        customerMap = new HashMap<>();
        customerMap.put("All", null);
        customerMap.put("Company", COMPANY);
        customerMap.put("Person", PERSON);
    }
}
