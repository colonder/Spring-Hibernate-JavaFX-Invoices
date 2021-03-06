package com.serviceImples;

import com.entity.Customer;
import com.entity.Product;
import com.entity.Templates;
import com.repositories.ITemplatesRepository;
import com.service.ITemplatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemplatesServImp implements ITemplatesService {

    @Autowired
    private ITemplatesRepository templatesRepository;

    @Override
    public Templates save(Templates entity) {
        return templatesRepository.save(entity);
    }

    @Override
    public Templates update(Templates entity) {
        return templatesRepository.save(entity);
    }

    @Override
    public void delete(Templates entity) {
        templatesRepository.delete(entity);
    }

    @Override
    public void deleteInBatch(List<Templates> entities) {
        templatesRepository.deleteInBatch(entities);
    }

    @Override
    public Templates find(int id) {
        return templatesRepository.findById(id).orElse(null);
    }

    @Override
    public List<Templates> findAll() {
        return templatesRepository.findAll();
    }

    @Override
    public List<Templates> saveAll(List<Templates> templates) {
        return templatesRepository.saveAll(templates);
    }

    @Override
    public List<Templates> findByProductIn(List<Product> products) {
        return templatesRepository.findByProductIn(products);
    }

    @Override
    public List<Templates> findByCustomerIn(List<Customer> customers) {
        return templatesRepository.findByCustomerIn(customers);
    }
}
