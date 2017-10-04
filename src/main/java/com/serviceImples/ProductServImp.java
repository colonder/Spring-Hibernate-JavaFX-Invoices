package com.serviceImples;

import com.entity.Product;
import com.repositories.IProductRepository;
import com.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.specifications.ProductsSpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.where;

@Service
public class ProductServImp implements IProductService {
    @Autowired
    private IProductRepository productRepository;

    @Override
    public List<Product> findAll(String phrase, Boolean isService, String[] tags, Boolean isActive) {
        return productRepository.findAll(where(withPhrase(phrase))
                .and(withProductType(isService))
                .and(withTags(tags))
                .and(withActive(isActive)));
    }

    @Override
    public Product findByProductName(String name) {
        return productRepository.findByProductName(name);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void delete(Product product) {
        productRepository.delete(product);
    }

    @Override
    public List<Product> findAllByIsActiveTrue() {
        return productRepository.findAllByIsActiveTrue();
    }
}
