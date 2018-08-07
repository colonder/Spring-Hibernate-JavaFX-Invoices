package com.specifications;

import com.entity.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductsSpecifications {
    public static Specification<Product> withPhrase(String phrase)
    {
        if (phrase == null)
            return null;
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("productName"), phrase);
    }

    public static Specification<Product> withActive(Boolean isActive)
    {
        if (isActive == null)
            return null;

        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("isActive"), isActive);
    }
}
