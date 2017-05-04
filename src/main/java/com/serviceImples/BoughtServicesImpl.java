package com.serviceImples;

import com.entity.BoughtServices;
import com.entity.ServiceEntity;
import com.repositories.IBoughtServicesRepository;
import com.service.IBoughtServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoughtServicesImpl implements IBoughtServicesService
{
    @Autowired
    private IBoughtServicesRepository boughtServicesRepository;

    public Iterable<BoughtServices> findAll() {
        return boughtServicesRepository.findAll();
    }

    public BoughtServices findOne(int id) {
        return boughtServicesRepository.findOne(id);
    }

    @Override
    @Transactional
    public BoughtServices save(ServiceEntity serviceEntity) {
        return save(serviceEntity);
    }

    @Override
    @Transactional
    public void delete(int id) {
        boughtServicesRepository.delete(id);
    }
}