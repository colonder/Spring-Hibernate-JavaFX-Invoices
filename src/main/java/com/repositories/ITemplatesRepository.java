package com.repositories;

import com.entity.Customer;
import com.entity.Templates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITemplatesRepository extends JpaRepository<Templates, Long> {
    List<Templates> findByCustomer(Customer customer);
}
