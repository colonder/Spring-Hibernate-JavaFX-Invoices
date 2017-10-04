package com.specifications;

import com.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;

public class ProductsSpecifications {
    public static Specification<Product> withPhrase(String phrase)
    {
        if (phrase == null)
            return null;
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("productName"), phrase);
    }

    public static Specification<Product> withProductType(Boolean isService)
    {
        if (isService == null)
            return null;

        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("isService"), isService);
    }

    public static Specification<Product> withTags(Object[] tags)
    {
        if (tags == null)
            return null;

        return (root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = root.get("tag").in(tags);
            return criteriaBuilder.and(predicate);
        };
    }

    public static Specification<Product> withActive(Boolean isActive)
    {
        if (isActive == null)
            return null;

        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("isActive"), isActive);
    }
}
