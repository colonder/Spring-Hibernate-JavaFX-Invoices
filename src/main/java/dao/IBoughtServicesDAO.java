package dao;

import com.invoice.entity.BoughtServices;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IBoughtServicesDAO extends CrudRepository<BoughtServices, Integer>
{
    @Query("select b from BoughtServices b where b.customer.alias=?1")
    List<BoughtServices> findByCustomerAlias(String alias);
}
