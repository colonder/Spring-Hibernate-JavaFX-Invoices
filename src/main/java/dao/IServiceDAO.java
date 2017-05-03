package dao;

import entity.ServiceEntity;
import org.springframework.data.repository.CrudRepository;

public interface IServiceDAO extends CrudRepository<ServiceEntity, Integer>
{

}
