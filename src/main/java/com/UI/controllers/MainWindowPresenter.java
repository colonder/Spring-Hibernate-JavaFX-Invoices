package com.UI.controllers;

import com.entity.BoughtServices;
import com.entity.BoughtServices.BoughtServicesProps;
import com.entity.Customer;
import com.entity.Customer.CustomerProps;
import com.entity.ServiceEntity;
import com.service.IBoughtServicesService;
import com.service.ICustomerService;
import com.utilities.dialogs.ChoiceServiceDialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.BigDecimalStringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class MainWindowPresenter
{
    @FXML private TableView<BoughtServicesProps> boughtServicesTableView;

    @FXML private TableColumn orderColumn;
    @FXML private TableColumn<BoughtServicesProps, String>  serviceNameColumn;
    @FXML private TableColumn<BoughtServicesProps, String>  symbolColumn;
    @FXML private TableColumn<BoughtServicesProps, String>  unitColumn;
    @FXML private TableColumn<BoughtServicesProps, BigDecimal> quantityColumn;
    @FXML private TableColumn<BoughtServicesProps, BigDecimal>  unitPriceColumn;
    @FXML private TableColumn<BoughtServicesProps, BigDecimal> valWithoutTaxColumn;
    @FXML private TableColumn<BoughtServicesProps, Integer>  taxRateColumn;
    @FXML private TableColumn<BoughtServicesProps, BigDecimal> taxValColumn;
    @FXML private TableColumn<BoughtServicesProps, BigDecimal> valWithTaxColumn;

    @FXML private TableView<CustomerProps> customersTableView;

    @FXML private TableColumn<CustomerProps, String> customersCol;

    @FXML private Label contractorNameLabel;
    @FXML private Label companyNameLabel;
    @FXML private Label addressLabel;
    @FXML private Label cityLabel;
    @FXML private Label taxIDLabel;
    @FXML private Label paymentLabel;
    @FXML private Label sumWordsLabel;
    @FXML private Label sumLabel;

    @FXML private Button serviceAddButton;
    @FXML private Button serviceDeleteButton;

    @Autowired
    private ChoiceServiceDialog choiceServiceDialog;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IBoughtServicesService boughtServicesService;

    private ObservableList<CustomerProps> customerList = FXCollections.observableArrayList();

    @FXML
    public void initialize()
    {
        for(Customer customer : customerService.findAll())
        {
            customerList.add(customer.new CustomerProps());
        }

        configureCustomersTable();
        configureButtons();
        configureServicesTable();
        showCustomerDetails(customersTableView.getSelectionModel().getSelectedItem());
    }

    private void configureButtons()
    {
        serviceAddButton.setOnAction(e -> {
            for(ServiceEntity service : choiceServiceDialog.showDialog())
            {
                BoughtServices bs = new BoughtServices(customersTableView.getSelectionModel().getSelectedItem()
                        .getCustomer(), service, BigDecimal.ZERO);
                boughtServicesService.save(bs);
                customersTableView.getSelectionModel().getSelectedItem().addBoughtServiceProp(bs.new BoughtServicesProps());
            }
        });

        serviceDeleteButton.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Usunięcie usługi");
            alert.setHeaderText("Na pewno usunąć?");
            ButtonType okBtn = new ButtonType("Tak", ButtonBar.ButtonData.OK_DONE);
            ButtonType noBtn = new ButtonType("Nie", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(okBtn, noBtn);

            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent())
            {
                for(BoughtServicesProps serviceToDelete : boughtServicesTableView.getSelectionModel().getSelectedItems())
                {
                    customersTableView.getSelectionModel().getSelectedItem().deleteBoughtServiceProp(serviceToDelete);
                    boughtServicesService.delete(serviceToDelete.getBoughtService());
                }
            }
        });
    }

    private void configureCustomersTable()
    {
        customersTableView.setItems(customerList);
        customersCol.setCellValueFactory(new PropertyValueFactory<>("aliasProp"));
        customersTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal) -> {
            showCustomerDetails(newVal);
            populateBoughtServicesData(customersTableView.getSelectionModel().getSelectedItem());
        });

        customersTableView.getSelectionModel().select(0);
    }

    private void showCustomerDetails(CustomerProps customer)
    {
        try {
            contractorNameLabel.setText(customer.getFirstNameProp() + " " + customer.getLastNameProp());
            companyNameLabel.setText(customer.getCompanyNameProp());
            addressLabel.setText(customer.getAddressProp());
            cityLabel.setText(customer.getPostalCodeProp() + " " + customer.getCityProp());
            taxIDLabel.setText(customer.getTaxIdProp());
            paymentLabel.setText(customer.getPaymentProp().toString());
        }

        catch (NullPointerException e)
        {
            contractorNameLabel.setText("");
            companyNameLabel.setText("");
            addressLabel.setText("");
            cityLabel.setText("");
            taxIDLabel.setText("");
            paymentLabel.setText("");
        }
    }

    private void configureServicesTable()
    {
        serviceNameColumn.setCellValueFactory(new PropertyValueFactory<>("serviceNameProp"));
        symbolColumn.setCellValueFactory(new PropertyValueFactory<>("symbolProp"));
        unitColumn.setCellValueFactory(new PropertyValueFactory<>("unitProp"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantityProp"));
        quantityColumn.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalStringConverter()));
        quantityColumn.setOnEditCommit(event -> { //TODO: change decimal separator to comma instead of period
            event.getRowValue().setQuantityProp(event.getNewValue());
            boughtServicesService.update(event.getNewValue(), event.getRowValue().getBoughtService().
                    getInternalId().getId());
        });
        unitPriceColumn.setCellValueFactory(new PropertyValueFactory<>("netUnitPriceProp"));
        taxRateColumn.setCellValueFactory(new PropertyValueFactory<>("vatProp"));
        valWithoutTaxColumn.setCellValueFactory(new PropertyValueFactory<>("valWithoutTax"));
        taxValColumn.setCellValueFactory(new PropertyValueFactory<>("taxVal"));
        valWithTaxColumn.setCellValueFactory(new PropertyValueFactory<>("totalVal"));
        boughtServicesTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void populateBoughtServicesData(CustomerProps customer)
    {
        boughtServicesTableView.setItems(customer.boughtServicesProps());
    }
}