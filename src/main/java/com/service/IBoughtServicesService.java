package com.service;

import com.entity.BoughtServices;
import com.entity.ServiceEntity;

public interface IBoughtServicesService
{
    Iterable<BoughtServices> findAll();
    BoughtServices findOne(int id);
    BoughtServices save(ServiceEntity serviceEntity);
    void delete(int id);
}