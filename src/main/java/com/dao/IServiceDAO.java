package com.dao;

import com.entity.ServiceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IServiceDAO extends CrudRepository<ServiceEntity, Integer>
{

}
