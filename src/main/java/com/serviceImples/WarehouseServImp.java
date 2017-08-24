package com.serviceImples;

import com.entity.Warehouse;
import com.repositories.IWarehouseRepository;
import com.service.IWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WarehouseServImp implements IWarehouseService {
    @Autowired
    private IWarehouseRepository warehouseRepository;

    @Override
    public List<Warehouse> findAll() {
        return warehouseRepository.findAll();
    }

    @Override
    public Warehouse save(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    @Override
    public void delete(Warehouse warehouse) {
        warehouseRepository.delete(warehouse);
    }

    @Override
    public int updateAmount(int sold, int available, int id) {
        return warehouseRepository.updateAmount(sold, available, id);
    }

    @Override
    public int update(String productCode, LocalDate lastSaleDate, int id) {
        return warehouseRepository.update(productCode, lastSaleDate, id);
    }
}
