package com.utilities.classes;

import com.entity.ServiceEntity;
import com.entity.ServiceEntity.ServiceEntityProps;
import com.service.IServicesEntityService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;

@Component
public class ServicesList implements Callable<ObservableList<ServiceEntityProps>>
{
    @Autowired
    private IServicesEntityService servicesEntityService;
    private ObservableList<ServiceEntityProps> servicesList;

    public ServicesList() { servicesList = FXCollections.observableArrayList();}

    @Override
    public ObservableList<ServiceEntityProps> call() throws Exception
    {
        //FIXME: why service is null?
        for (ServiceEntity serviceEntity : servicesEntityService.findAll())
        {
            servicesList.add(serviceEntity.getServiceEntityProps());
        }
        return servicesList;
    }
}
