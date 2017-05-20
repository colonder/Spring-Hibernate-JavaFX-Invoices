package com.serviceImples;

import com.entity.BoughtServices;
import com.repositories.IBoughtServicesRepository;
import com.service.IBoughtServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoughtServicesImpl implements IBoughtServicesService
{
    @Autowired
    private IBoughtServicesRepository boughtServicesRepository;

    @Transactional
    public BoughtServices save(BoughtServices boughtServices) {
        return boughtServicesRepository.save(boughtServices);
    }

    @Transactional
    public void delete(int id) {
        boughtServicesRepository.delete(id);
    }

    public List<BoughtServices> findBoughtServicesByCustomer_Id(int id)
    {
        return boughtServicesRepository.findBoughtServicesByCustomer_Id(id);
    }

    public List<BoughtServices> findAll() {
        return boughtServicesRepository.findAll();
    }
}