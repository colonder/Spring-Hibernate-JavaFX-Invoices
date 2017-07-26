package com.UI.controllers;

import com.entity.BoughtServices;
import com.entity.Customer;
import com.service.IBoughtServicesService;
import com.utilities.classes.CurrencyHandler;
import com.utilities.classes.CustomersList;
import com.utilities.dialogs.ChoiceServiceDialog;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.BigDecimalStringConverter;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class MainWindowPresenter {
    //region variables declarations
    @FXML
    private TableView<BoughtServices> boughtServicesTableView;
    @FXML
    private TableColumn<BoughtServices, Number> orderColumn;
    @FXML
    private TableColumn<BoughtServices, String> serviceNameColumn;
    @FXML
    private TableColumn<BoughtServices, String> symbolColumn;
    @FXML
    private TableColumn<BoughtServices, String> unitColumn;
    @FXML
    private TableColumn<BoughtServices, BigDecimal> quantityColumn;
    @FXML
    private TableColumn<BoughtServices, BigDecimal> unitPriceColumn;
    @FXML
    private TableColumn<BoughtServices, BigDecimal> valWithoutTaxColumn;
    @FXML
    private TableColumn<BoughtServices, Integer> taxRateColumn;
    @FXML
    private TableColumn<BoughtServices, BigDecimal> taxValColumn;
    @FXML
    private TableColumn<BoughtServices, BigDecimal> valWithTaxColumn;
    @FXML
    private TableView<Customer> customersTableView;
    @FXML
    private TableColumn<Customer, String> customersCol;
    @FXML
    private Label contractorNameLabel;
    @FXML
    private Label companyNameLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label taxIDLabel;
    @FXML
    private Label paymentLabel;
    @FXML
    private Label sumWordsLabel;
    @FXML
    private Label sumLabel;
    @FXML
    private Button serviceAddButton;
    @FXML
    private Button serviceDeleteButton;
    @Autowired
    private ChoiceServiceDialog choiceServiceDialog;
    @Autowired
    private IBoughtServicesService boughtServicesService;
    //endregion

    @FXML
    public void initialize() {
        configureCustomersTable();
        configureButtons();
        configureServicesTable();
    }

    private void configureButtons() {
        serviceAddButton.setOnAction(actionEvent ->
                choiceServiceDialog.showDialog(customersTableView.getSelectionModel().getSelectedItem()));

        serviceDeleteButton.setOnAction(actionEvent -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Usunięcie usługi");
            alert.setHeaderText("Na pewno usunąć?");
            ButtonType okBtn = new ButtonType("Tak", ButtonBar.ButtonData.OK_DONE);
            ButtonType noBtn = new ButtonType("Nie", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(okBtn, noBtn);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent()) {
                for (BoughtServices serviceToDelete : boughtServicesTableView.getSelectionModel().getSelectedItems()) {
                    customersTableView.getSelectionModel().getSelectedItem().removeBoughtSerbices(serviceToDelete);
                    boughtServicesService.delete(serviceToDelete);
                }
            }
        });
    }

    private void configureCustomersTable() {
        customersTableView.setItems(CustomersList.customerList);
        customersCol.setCellValueFactory(new PropertyValueFactory<>("aliasProp"));
        customersTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal) -> {
            try {
                showCustomerDetails(newVal);
                populateBoughtServicesData(customersTableView.getSelectionModel().getSelectedItem());
                sumAll(customersTableView.getSelectionModel().getSelectedItem());
            } catch (NullPointerException e) {
                contractorNameLabel.setText("");
                companyNameLabel.setText("");
                addressLabel.setText("");
                cityLabel.setText("");
                taxIDLabel.setText("");
                paymentLabel.setText("");
                boughtServicesTableView.getItems().clear();
                sumLabel.setText("");
                sumWordsLabel.setText("");
            }
        });

        customersTableView.getSelectionModel().select(0);
    }

    private void showCustomerDetails(Customer customer) {
        contractorNameLabel.setText(customer.getFirstNameProp() + " " + customer.getLastNameProp());
        companyNameLabel.setText(customer.getCompanyNameProp());
        addressLabel.setText(customer.getAddressProp());
        cityLabel.setText(customer.getPostalCodeProp() + " " + customer.getCityProp());
        taxIDLabel.setText(customer.getTaxIdProp());
        paymentLabel.setText(customer.getPaymentProp().toString());
    }

    private void configureServicesTable() {
        orderColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(boughtServicesTableView.getItems().indexOf(param.getValue()) + 1));
        serviceNameColumn.setCellValueFactory(new PropertyValueFactory<>("serviceNameProp"));
        symbolColumn.setCellValueFactory(new PropertyValueFactory<>("symbolProp"));
        unitColumn.setCellValueFactory(new PropertyValueFactory<>("unitProp"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantityProp"));
        quantityColumn.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalStringConverter()));
        quantityColumn.setOnEditCommit(event -> { //TODO: change decimal separator to comma instead of period
            if (event.getNewValue().compareTo(BigDecimal.valueOf(9999.99)) > 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Wartość zbyt duża");
                alert.setContentText("Wartość musi być mniejsza niż 9999.99");
                alert.show();
            } else if (event.getNewValue().compareTo(BigDecimal.ZERO) < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Wartość nieprawidłowa");
                alert.setContentText("Wartość nie może być ujemna");
                alert.show();
            } else {
                try {
                    boughtServicesService.update(event.getNewValue(), event.getRowValue().getId());
                } catch (PSQLException e) {
                    e.printStackTrace();
                }
                event.getRowValue().setQuantityProp(event.getNewValue());
                sumAll(customersTableView.getSelectionModel().getSelectedItem());
            }
        });
        unitPriceColumn.setCellValueFactory(new PropertyValueFactory<>("netUnitPriceProp"));
        taxRateColumn.setCellValueFactory(new PropertyValueFactory<>("vatProp"));
        valWithoutTaxColumn.setCellValueFactory(new PropertyValueFactory<>("valWithoutTax"));
        taxValColumn.setCellValueFactory(new PropertyValueFactory<>("taxVal"));
        valWithTaxColumn.setCellValueFactory(new PropertyValueFactory<>("totalVal"));
        boughtServicesTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void populateBoughtServicesData(Customer customer) {
        // lazy load (and only once) list of bought services
        if (customer.getBoughtServices().isEmpty()) {
            boughtServicesService.findBoughtServicesByCustomer(customer).forEach(customer::addBoughtServices);
        }
        boughtServicesTableView.setItems(customer.getBoughtServices());
    }

    private void sumAll(Customer customerProps) {
        BigDecimal sum = BigDecimal.ZERO;
        for (BoughtServices service : customerProps.getBoughtServices()) {
            sum = sum.add(service.getTotalVal());
        }
        sumLabel.setText(CurrencyHandler.formatToCurrency(sum));
        sumWordsLabel.setText(CurrencyHandler.convertSumToWords(sum));
    }
}