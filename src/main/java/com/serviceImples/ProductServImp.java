package com.serviceImples;

import com.entity.Product;
import com.repositories.IProductRepository;
import com.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServImp implements IProductService {
    @Autowired
    private IProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    @Transactional
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product update(Product entity) {
        return productRepository.save(entity);
    }

    @Override
    @Transactional
    public void delete(Product product) {
        productRepository.delete(product);
    }

    @Override
    public void deleteInBatch(List<Product> entities) {

    }

    @Override
    public Product find(Long id) {
        return productRepository.findById(id).get();
    }

//    @Override
//    public Product findByProductName(String productName) {
//        return productRepository.findByProductName(productName);
//    }
}
