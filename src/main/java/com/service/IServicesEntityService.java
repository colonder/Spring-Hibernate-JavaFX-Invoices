package com.service;

import com.entity.ServiceEntity;

public interface IServicesEntityService
{
    Iterable<ServiceEntity> findAll();
    ServiceEntity findOne(int id);
    ServiceEntity save(ServiceEntity serviceEntity);
    void delete(int id);
}
