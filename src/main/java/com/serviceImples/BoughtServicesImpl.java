package com.serviceImples;

import com.entity.BoughtServices;
import com.entity.Customer;
import com.repositories.IBoughtServicesRepository;
import com.service.IBoughtServicesService;
import org.hibernate.exception.ConstraintViolationException;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BoughtServicesImpl implements IBoughtServicesService {
    @Autowired
    private IBoughtServicesRepository boughtServicesRepository;

    @Transactional
    public BoughtServices save(BoughtServices boughtServices) {
        return boughtServicesRepository.save(boughtServices);
    }

    public List<BoughtServices> findAll() {
        return boughtServicesRepository.findAll();
    }

    @Override
    public List<BoughtServices> findBoughtServicesByCustomer(Customer customer) {
        return boughtServicesRepository.findBoughtServicesByCustomer(customer);
    }

    @Override
    @Transactional
    public void delete(BoughtServices boughtServices) {
        boughtServicesRepository.delete(boughtServices);
    }

    @Transactional
    @Override
    public int update(BigDecimal value, int id) throws PSQLException, ConstraintViolationException {
        return boughtServicesRepository.update(value, id);
    }
}