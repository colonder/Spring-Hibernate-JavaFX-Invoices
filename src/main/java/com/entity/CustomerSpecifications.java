package com.entity;

import com.enums.CustomerType;
import org.springframework.data.jpa.domain.Specification;

public class CustomerSpecifications {
    public static Specification<Customer> withCustomerType(CustomerType type)
    {
        if (type == null)
        {
            return null;
        }

        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("customerType"), type);
    }
}
