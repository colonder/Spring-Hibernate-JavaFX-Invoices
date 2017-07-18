package com.repositories;

import com.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IServiceRepository extends JpaRepository<ServiceEntity, Integer> {
    List<ServiceEntity> findByServiceNameContaining(String string);
}
