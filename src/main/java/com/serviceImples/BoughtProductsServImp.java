package com.serviceImples;

import com.entity.BoughtProducts;
import com.repositories.IBoughtProductsRepository;
import com.service.IBoughtProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class BoughtProductsServImp implements IBoughtProductsService {
    @Autowired
    private IBoughtProductsRepository boughtProductsRepository;

    @Override
    public List<BoughtProducts> findAll() {
        return boughtProductsRepository.findAll();
    }

    @Override
    @Transactional
    public BoughtProducts save(BoughtProducts product) {
        return boughtProductsRepository.save(product);
    }

    @Override
    @Transactional
    public void delete(BoughtProducts product) {
        boughtProductsRepository.delete(product);
    }

    @Override
    @Transactional
    public int update(BigDecimal quantity, BigDecimal netValue, BigDecimal taxValue, BigDecimal grossValue, Date saleDate, int id) {
        return boughtProductsRepository.update(quantity, netValue, taxValue, grossValue, saleDate, id);
    }
}
