package controller;

import dao.IServiceDAO;
import entity.ServiceEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ServicesController implements IUtilities<ServiceEntity>
{
    @Autowired
    private IServiceDAO serviceDAO;
    
    public void manageServices()
    {
        List<ServiceEntity> allServices = convertIterableToCollection(serviceDAO.findAll());
    }
    
    public void showFormForAdd()
    {
        //create model attribute to bind form data
        ServiceEntity theService = new ServiceEntity();
    }

    public void showFormForUpdate(int serviceId)
    {
        // get the customer from service
        ServiceEntity theService = serviceDAO.findOne(serviceId);
    }

    public void deleteCustomer(int serviceId)
    {
        //delete the customer
        serviceDAO.delete(serviceId);
    }

    public void saveService(ServiceEntity theService) {
        //save the customer using our service
        serviceDAO.save(theService);
    }

    @Override
    public List<ServiceEntity> convertIterableToCollection(Iterable<ServiceEntity> iterable) {
        List<ServiceEntity> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }
}
