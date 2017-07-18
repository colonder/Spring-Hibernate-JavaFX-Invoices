package com.UI.controllers;

import com.entity.ServiceEntity.ServiceEntityProps;
import com.service.IServicesEntityService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ManageServicesDialogPresenter {

    @Autowired
    IServicesEntityService servicesEntityService;
    @FXML ComboBox<String> filterComboBox;
    @FXML TextField filterTextFld;
    @FXML Button filterBtn;
    @FXML Button newServiceBtn;
    @FXML Button editServiceBtn;
    @FXML Button deleteServiceBtn;
    @FXML TableView<ServiceEntityProps> serviceTableView;
    @FXML TableColumn<ServiceEntityProps, String> serviceNameCol;
    @FXML TableColumn<ServiceEntityProps, String> symbolCol;
    @FXML TableColumn<ServiceEntityProps, String> unitCol;
    @FXML TableColumn<ServiceEntityProps, BigDecimal> unitPriceCol;
    @FXML TableColumn<ServiceEntityProps, Integer> vatCol;
}
