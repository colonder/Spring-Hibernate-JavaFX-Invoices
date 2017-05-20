package com.repositories;

import com.entity.BoughtServices;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IBoughtServicesRepository extends JpaRepository<BoughtServices, Integer>
{
    List<BoughtServices> findBoughtServicesByCustomer_Id(int id);
}