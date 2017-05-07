package com.serviceImples;

import com.entity.ServiceEntity;
import com.repositories.IServiceRepository;
import com.service.IServicesEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServiceImpl implements IServicesEntityService {

    @Autowired
    private IServiceRepository serviceRepository;

    public List<ServiceEntity> findAll() {
        return serviceRepository.findAll();
    }

    public ServiceEntity findOne(int id) {
        return serviceRepository.findOne(id);
    }

    @Transactional
    public ServiceEntity save(ServiceEntity serviceEntity) {
        return serviceRepository.save(serviceEntity);
    }

    @Transactional
    public void delete(int id) {
        serviceRepository.delete(id);
    }
}
