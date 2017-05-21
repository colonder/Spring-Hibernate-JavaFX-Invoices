package com.repositories;

import com.entity.BoughtServices;
import com.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IBoughtServicesRepository extends JpaRepository<BoughtServices, Integer>
{
    List<BoughtServices> findBoughtServicesByCustomer(Customer customer);
}