package com.serviceImples;

import com.entity.ServiceEntity;
import com.repositories.IServiceRepository;
import com.service.IServicesEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServiceImpl implements IServicesEntityService {

    @Autowired
    private IServiceRepository serviceRepository;

    @Override
    public Iterable<ServiceEntity> findAll() {
        return serviceRepository.findAll();
    }

    @Override
    public ServiceEntity findOne(int id) {
        return serviceRepository.findOne(id);
    }

    @Override
    @Transactional
    public ServiceEntity save(ServiceEntity serviceEntity) {
        return serviceRepository.save(serviceEntity);
    }

    @Override
    @Transactional
    public void delete(int id) {
        serviceRepository.delete(id);
    }
}
