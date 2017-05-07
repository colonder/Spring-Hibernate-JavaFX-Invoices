package com.UI.controllers;

import com.entity.BoughtServices;
import com.entity.ServiceEntity;
import com.service.IBoughtServicesService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class mainWindowController
{
    @FXML private TableView<ServiceEntity> boughtServicesTableView;

    @FXML private TableColumn orderColumn;
    @FXML private TableColumn<ServiceEntity, String>  serviceNameColumn;
    @FXML private TableColumn<ServiceEntity, String>  symbolColumn;
    @FXML private TableColumn<ServiceEntity, String>  unitColumn;
    @FXML private TableColumn<BoughtServices, BigDecimal> quantityColumn;
    @FXML private TableColumn<ServiceEntity, BigDecimal>  unitPriceColumn;
    @FXML private TableColumn valWithoutTax;
    @FXML private TableColumn<ServiceEntity, Integer>  taxRateColumn;
    @FXML private TableColumn taxValColumn;
    @FXML private TableColumn taxWithValColumn;

    @FXML private Label contractorNameLabel;
    @FXML private Label companyNameLabel;
    @FXML private Label addressLabel;
    @FXML private Label cityLabel;
    @FXML private Label taxIDLabel;

    @Autowired
    private IBoughtServicesService boughtServicesService;

    @FXML
    public void initialize()
    {
        configureContractorData();
        configureServicesTable();

        // actual Hibernate query, just for testing purposes right now
        for (BoughtServices services : boughtServicesService.findAllByCustomerAlias("zebrad"))
        {
            System.out.println(services.getServiceEntity());
            boughtServicesTableView.getItems().add(services.getServiceEntity());
        }
    }

    private void configureContractorData()
    {

    }

    private void configureServicesTable()
    {
        //lp
        serviceNameColumn.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        symbolColumn.setCellValueFactory(new PropertyValueFactory<>("symbol"));
        unitColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        unitPriceColumn.setCellValueFactory(new PropertyValueFactory<>("netUnitPrice"));
        //bez podatku
        taxRateColumn.setCellValueFactory(new PropertyValueFactory<>("vatTaxRate"));
        //podatek
        //z podatkiem
    }
}