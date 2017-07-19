package com.service;

import com.entity.ServiceEntity;

import java.math.BigDecimal;
import java.util.List;

public interface IServicesEntityService {
    List<ServiceEntity> findAll();
    ServiceEntity findOne(int id);
    ServiceEntity save(ServiceEntity serviceEntity);
    void delete(ServiceEntity serviceEntity);
    List<ServiceEntity> findByServiceNameContaining(String string);
    int update(String serviceName, String symbol, String unit, BigDecimal netPrice, int vat, int id);
}
