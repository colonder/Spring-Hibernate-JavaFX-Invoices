package com.repositories;

import com.entity.ServiceEntity;
import org.springframework.data.repository.CrudRepository;

public interface IServiceRepository extends CrudRepository<ServiceEntity, Integer>
{

}
