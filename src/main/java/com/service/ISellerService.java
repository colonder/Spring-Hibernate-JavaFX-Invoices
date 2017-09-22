package com.service;

import com.entity.Seller;

import java.util.List;

public interface ISellerService {
    List<Seller> findAll();
    Seller save(Seller seller);
    void delete(Seller seller);
}
