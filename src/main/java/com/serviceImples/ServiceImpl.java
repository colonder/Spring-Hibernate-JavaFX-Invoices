package com.serviceImples;

import com.entity.ServiceEntity;
import com.repositories.IServiceRepository;
import com.service.IServicesEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
    public void delete(ServiceEntity serviceEntity) {
        serviceRepository.delete(serviceEntity);
    }

    @Override
    public List<ServiceEntity> findByServiceNameContaining(String string) {
        return serviceRepository.findByServiceNameContaining(string);
    }

    @Override
    public int update(String serviceName, String symbol, String unit, BigDecimal netPrice, int vat, int id) {
        return serviceRepository.update(serviceName, symbol, unit, netPrice, vat, id);
    }
}
