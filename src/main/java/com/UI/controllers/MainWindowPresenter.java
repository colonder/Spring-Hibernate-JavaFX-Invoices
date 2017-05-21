package com.UI.controllers;

import com.entity.BoughtServices;
import com.entity.Customer;
import com.service.IBoughtServicesService;
import com.service.ICustomerService;
import com.utilities.ChoiceServiceDialog;
import javafx.beans.property.ReadOnlyObjectWrapper;
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
    @FXML private TableView<BoughtServices> boughtServicesTableView;

    @FXML private TableColumn orderColumn;
    @FXML private TableColumn<BoughtServices, String>  serviceNameColumn;
    @FXML private TableColumn<BoughtServices, String>  symbolColumn;
    @FXML private TableColumn<BoughtServices, String>  unitColumn;
    @FXML private TableColumn<BoughtServices, BigDecimal> quantityColumn;
    @FXML private TableColumn<BoughtServices, BigDecimal>  unitPriceColumn;
    @FXML private TableColumn<BoughtServices, BigDecimal> valWithoutTax;
    @FXML private TableColumn<BoughtServices, Integer>  taxRateColumn;
    @FXML private TableColumn<BoughtServices, BigDecimal> taxValColumn;
    @FXML private TableColumn<BoughtServices, BigDecimal> valWithTaxColumn;

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
    private ICustomerService customerService;

    @Autowired
    private IBoughtServicesService boughtServicesService;

    @Autowired
    private ChoiceServiceDialog choiceServiceDialog;

    private ObservableList<Customer> customerList = FXCollections.observableArrayList();
    private ObservableList<BoughtServices> servicesList = FXCollections.observableArrayList();

    @FXML
    public void initialize()
    {
        configureServicesTable();
        configureCustomersTable();

        showCustomerDetails(customersTableView.getSelectionModel().getSelectedItem());
        boughtServicesTableView.setItems(servicesList);
        boughtServicesTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        configureButtons();
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
                for(BoughtServices serviceToDelete : boughtServicesTableView.getSelectionModel().getSelectedItems())
                {
                    boughtServicesService.delete(serviceToDelete);
                }

                populateBoughtServicesData(customersTableView.getSelectionModel().getSelectedItem());
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
        serviceNameColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getServiceEntity().getServiceName()));
        symbolColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getServiceEntity().getSymbol()));
        unitColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getServiceEntity().getUnit()));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantityColumn.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalStringConverter()));
        quantityColumn.setOnEditCommit(event -> boughtServicesService.save(event.getRowValue())); //TODO: why it's not working?
        unitPriceColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getServiceEntity().getNetUnitPrice()));
        valWithoutTax.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getServiceEntity().getNetUnitPrice()
                        .multiply(param.getValue().getQuantity()).setScale(2, BigDecimal.ROUND_HALF_DOWN)));
        taxRateColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getServiceEntity().getVatTaxRate()));

        taxValColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(
                BigDecimal.valueOf(param.getValue().getServiceEntity().getVatTaxRate()) // tax rate
                        .multiply(new BigDecimal(0.01)) // tax as percents
                        .multiply(param.getValue().getServiceEntity().getNetUnitPrice().multiply(param.getValue()
                                .getQuantity())) // value without tax
                        .setScale(2, BigDecimal.ROUND_HALF_DOWN)));

        valWithTaxColumn.setCellValueFactory(new PropertyValueFactory<>("withTax"));
    }

    private void populateBoughtServicesData(Customer customer)
    {
        servicesList.clear();
        servicesList.setAll(boughtServicesService.findBoughtServicesByCustomer(customer));
    }
}