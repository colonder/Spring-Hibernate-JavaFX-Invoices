package com.repositories;

import com.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IServiceRepository extends JpaRepository<ServiceEntity, Integer> {}
