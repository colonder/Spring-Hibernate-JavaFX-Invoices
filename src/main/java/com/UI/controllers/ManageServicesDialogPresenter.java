package com.UI.controllers;

import com.service.IServicesEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ManageServicesDialogPresenter {

    @Autowired
    IServicesEntityService servicesEntityService;


}
