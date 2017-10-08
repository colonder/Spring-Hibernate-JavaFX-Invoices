package com.serviceImples;

import com.entity.Seller;
import com.repositories.ISellerRepository;
import com.service.ISellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class SellerServImp implements ISellerService {

    @Autowired
    ISellerRepository sellerRepository;

    @Override
    public List<Seller> findAll() {
        return sellerRepository.findAll();
    }

    @Override
    @Transactional
    public Seller save(Seller seller) {
        return sellerRepository.save(seller);
    }

    @Override
    @Transactional
    public void delete(Seller seller) {
        sellerRepository.delete(seller);
    }
}
