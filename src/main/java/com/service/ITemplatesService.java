package com.service;

import com.entity.Customer;
import com.entity.Templates;

import java.util.List;

public interface ITemplatesService extends GenericService<Templates> {
    List<Templates> findByCustomer(Customer customer);
    List<Templates> saveAll(List<Templates> templates);
}
