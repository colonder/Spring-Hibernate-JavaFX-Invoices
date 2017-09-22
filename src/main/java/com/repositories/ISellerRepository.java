package com.repositories;

import com.entity.Seller;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISellerRepository extends CrudRepository<Seller, Integer> {

}
