package com.UI.controllers;

import com.entity.BoughtServices;
import com.entity.Customer;
import com.intermediary.PersonalData;
import com.service.IBoughtServicesService;
import com.service.ICustomerService;
import com.utilities.ChoiceServiceDialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.NumberStringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class MainWindowPresenter
{
    @FXML private TableView<PersonalData> boughtServicesTableView;

    @FXML private TableColumn orderColumn;
    @FXML private TableColumn<PersonalData, String>  serviceNameColumn;
    @FXML private TableColumn<PersonalData, String>  symbolColumn;
    @FXML private TableColumn<PersonalData, String>  unitColumn;
    @FXML private TableColumn<PersonalData, Number> quantityColumn;
    @FXML private TableColumn<PersonalData, Number>  unitPriceColumn;
    @FXML private TableColumn<PersonalData, Number> valWithoutTaxColumn;
    @FXML private TableColumn<PersonalData, Number>  taxRateColumn;
    @FXML private TableColumn<PersonalData, Number> taxValColumn;
    @FXML private TableColumn<PersonalData, Number> valWithTaxColumn;

    @FXML private TableView<Customer> customersTableView;

    @FXML private TableColumn<Customer, String> customersCol;

    @FXML private Label contractorNameLabel;
    @FXML private Label companyNameLabel;
    @FXML private Label addressLabel;
    @FXML private Label cityLabel;
    @FXML private Label taxIDLabel;

    @FXML private Button serviceAddButton;
    @FXML private Button serviceDeleteButton;

    @Autowired
    private ChoiceServiceDialog choiceServiceDialog;

    @Autowired
    private PersonalData personalData;

    @Autowired
    private ICustomerService customerService;

    private ObservableList<Customer> customerList = FXCollections.observableArrayList();

    @FXML
    public void initialize()
    {
        configureCustomersTable();
        showCustomerDetails(customersTableView.getSelectionModel().getSelectedItem());
        //boughtServicesTableView.setItems(servicesList);
        boughtServicesTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        configureButtons();
        configureServicesTable();
    }

    private void configureButtons()
    {
        serviceAddButton.setOnAction(e -> {
            choiceServiceDialog.showDialog(customersTableView.getSelectionModel().getSelectedItem());
            populateBoughtServicesData(customersTableView.getSelectionModel().getSelectedItem());
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
                /*for(BoughtServices serviceToDelete : boughtServicesTableView.getSelectionModel().getSelectedItems())
                {
                    boughtServicesService.delete(serviceToDelete);
                }

                populateBoughtServicesData(customersTableView.getSelectionModel().getSelectedItem());*/
            }
        });
    }

    private void configureCustomersTable()
    {
        customersCol.setCellValueFactory(new PropertyValueFactory<>("alias"));
        customerList.addAll(customerService.findAll());
        customersTableView.setItems(customerList);
        customersTableView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldVal, newVal) -> {

            showCustomerDetails(newVal);
            populateBoughtServicesData(customersTableView.getSelectionModel().getSelectedItem());
        });

        customersTableView.getSelectionModel().select(0);
    }

    private void showCustomerDetails(Customer customer)
    {
        contractorNameLabel.setText(customer.getFirstName() + " " + customer.getLastName());
        companyNameLabel.setText(customer.getCompanyName());
        addressLabel.setText(customer.getAddress());
        cityLabel.setText(customer.getPostalCode() + " " + customer.getCity());
        taxIDLabel.setText(customer.getTaxIdentifier());
    }

    private void configureServicesTable()
    {
        /*serviceNameColumn.setCellValueFactory(param -> param.getValue().getServiceEntity().serviceNamePropProperty());
        symbolColumn.setCellValueFactory(param -> param.getValue().getServiceEntity().symbolPropProperty());
        unitColumn.setCellValueFactory(param -> param.getValue().getServiceEntity().unitPropProperty());
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantityColumn.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
        quantityColumn.setOnEditCommit(event -> {
            event.getRowValue().setQuantity(BigDecimal.valueOf(event.getNewValue().doubleValue()));
            event.getRowValue().setQuantityProp(event.getNewValue().doubleValue());
            boughtServicesService.save(event.getRowValue()); //TODO: why it's not working?
        });
        unitPriceColumn.setCellValueFactory(param -> param.getValue().getServiceEntity().netUnitPricePropProperty());
        taxRateColumn.setCellValueFactory(param -> param.getValue().getServiceEntity().vatPropProperty());
        //valWithoutTaxColumn.setCellValueFactory(new PropertyValueFactory<>("valWithoutTax"));
        //taxValColumn.setCellValueFactory(new PropertyValueFactory<>("taxVal"));
        //valWithTaxColumn.setCellValueFactory(new PropertyValueFactory<>("totalVal"));*/
    }

    private void populateBoughtServicesData(Customer customer)
    {
        //servicesList.clear();
        //servicesList.setAll(boughtServicesService.findBoughtServicesByCustomer(customer));
    }
}