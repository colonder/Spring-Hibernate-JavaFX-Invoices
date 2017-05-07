package com.repositories;

import com.entity.BoughtServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IBoughtServicesRepository extends JpaRepository<BoughtServices, Integer>
{
    @Query("select b from BoughtServices b where b.customer.alias=?1")
    List<BoughtServices> findAllByCustomerAlias(String alias);
}