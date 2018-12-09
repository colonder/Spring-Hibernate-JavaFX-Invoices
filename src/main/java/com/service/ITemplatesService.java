package com.service;

import com.entity.Customer;
import com.entity.Product;
import com.entity.Templates;

import java.util.List;

public interface ITemplatesService extends GenericService<Templates> {
    List<Templates> saveAll(List<Templates> templates);

    List<Templates> findByProductIn(List<Product> products);

    List<Templates> findByCustomerIn(List<Customer> customers);
}
