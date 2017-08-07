package com.repositories;

import com.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface IWarehouseRepository extends JpaRepository<Warehouse, Integer>
{
    @Modifying
    @Query("UPDATE Warehouse w SET w.sold =?1, w.available =?2  WHERE id =?3")
    int updateAmount(int sold, int available, int id);

    @Modifying
    @Query("UPDATE Warehouse w SET w.productCode =?1, w.lastSellDate =?3 WHERE w.id =?4")
    int update(String productCode, Date lastSellDate, int id);
}
