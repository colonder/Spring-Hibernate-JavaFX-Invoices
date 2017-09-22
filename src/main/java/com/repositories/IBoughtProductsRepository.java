package com.repositories;

import com.entity.BoughtProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBoughtProductsRepository extends JpaRepository<BoughtProduct, Integer> {

}
