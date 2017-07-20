package com.utilities.classes;

import com.entity.ServiceEntity;
import com.entity.ServiceEntity.ServiceEntityProps;
import com.service.IServicesEntityService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;

public class ServicesList implements Runnable
{
    @Autowired
    private IServicesEntityService servicesEntityService;
    private ObservableList<ServiceEntityProps> servicesList;

    public ServicesList() {
        servicesList = FXCollections.observableArrayList();
    }

    public ObservableList<ServiceEntityProps> getServicesList() {
        return servicesList;
    }

    public void addService(ServiceEntityProps serviceEntity)
    {
        servicesList.add(serviceEntity);
        servicesEntityService.save(serviceEntity.getServiceEntity());
    }

    public void removeService(ServiceEntityProps props)
    {
        servicesEntityService.delete(props.getServiceEntity());
        servicesList.remove(props);
    }

    @Override
    public void run() {
        //FIXME : null pointer exception
        //TODO: maybe it' better to make it Callable and/or FutureTask?
        for (ServiceEntity serviceEntity : servicesEntityService.findAll())
        {
            servicesList.add(serviceEntity.getServiceEntityProps());
        }
    }
}
