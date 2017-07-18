package com.repositories;

import com.entity.BoughtServices;
import com.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface IBoughtServicesRepository extends JpaRepository<BoughtServices, Integer> {
    List<BoughtServices> findBoughtServicesByCustomer(Customer customer);

    @Modifying
    @Query("UPDATE BoughtServices b SET b.quantity =?1 WHERE b.internalId.id =?2")
    int update(BigDecimal value, int id);
}