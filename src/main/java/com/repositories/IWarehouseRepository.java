package com.repositories;

import com.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IWarehouseRepository extends JpaRepository<Warehouse, Integer>
{
    List<Warehouse> findAll();
}
