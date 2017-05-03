package com.dao;

import com.entity.BoughtServices;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBoughtServicesDAO extends CrudRepository<BoughtServices, Integer>
{
    @Query("select b from BoughtServices b where b.customer.alias=?1")
    List<BoughtServices> findByCustomerAlias(String alias);
}
