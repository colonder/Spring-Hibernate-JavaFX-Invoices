package com.specifications;

import com.entity.Customer;
import com.enums.CustomerType;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;

public class CustomerSpecifications {
    public static Specification<Customer> withCustomerType(CustomerType type)
    {
        if (type == null)
        {
            return null;
        }

        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("customerType"), type);
    }

    public static Specification<Customer> withTags(Object[] tags)
    {
        if (tags == null)
            return null;

        return (root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = root.get("tag").in(tags);
            return criteriaBuilder.and(predicate);
        };
    }
}
