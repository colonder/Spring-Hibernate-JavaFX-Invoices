package com.service;

import com.entity.ServiceEntity;

import java.util.List;

public interface IServicesEntityService {
    List<ServiceEntity> findAll();

    ServiceEntity findOne(int id);

    ServiceEntity save(ServiceEntity serviceEntity);

    void delete(int id);

    List<ServiceEntity> findByServiceNameContaining(String string);
}
