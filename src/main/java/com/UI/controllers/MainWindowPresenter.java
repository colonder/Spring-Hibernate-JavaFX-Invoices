package com.UI.controllers;

import com.entity.BoughtServices.BoughtServicesProps;
import com.entity.Customer.CustomerProps;
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
    @FXML private TableView<BoughtServicesProps> boughtServicesTableView;
    @FXML private TableColumn<BoughtServicesProps, Number> orderColumn;
    @FXML private TableColumn<BoughtServicesProps, String> serviceNameColumn;
    @FXML private TableColumn<BoughtServicesProps, String> symbolColumn;
    @FXML private TableColumn<BoughtServicesProps, String> unitColumn;
    @FXML private TableColumn<BoughtServicesProps, BigDecimal> quantityColumn;
    @FXML private TableColumn<BoughtServicesProps, BigDecimal> unitPriceColumn;
    @FXML private TableColumn<BoughtServicesProps, BigDecimal> valWithoutTaxColumn;
    @FXML private TableColumn<BoughtServicesProps, Integer> taxRateColumn;
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
    @Autowired private ChoiceServiceDialog choiceServiceDialog;
    @Autowired private IBoughtServicesService boughtServicesService;
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
            if (result.isPresent())
            {
                for (BoughtServicesProps serviceToDelete : boughtServicesTableView.getSelectionModel().getSelectedItems())
                {
                    customersTableView.getSelectionModel().getSelectedItem().removeBoughtSerbicesProps(serviceToDelete);
                    boughtServicesService.delete(serviceToDelete.getBoughtService());
                }
            }
        });
    }

    private void configureCustomersTable()
    {
        customersTableView.setItems(CustomersList.customerList);
        customersCol.setCellValueFactory(new PropertyValueFactory<>("aliasProp"));
        customersTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal) -> {
            try
            {
                showCustomerDetails(newVal);
                populateBoughtServicesData(customersTableView.getSelectionModel().getSelectedItem());
                sumAll(customersTableView.getSelectionModel().getSelectedItem());
            }

            catch (NullPointerException e)
            {
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

    private void showCustomerDetails(CustomerProps customer) {
        contractorNameLabel.setText(customer.getFirstNameProp() + " " + customer.getLastNameProp());
        companyNameLabel.setText(customer.getCompanyNameProp());
        addressLabel.setText(customer.getAddressProp());
        cityLabel.setText(customer.getPostalCodeProp() + " " + customer.getCityProp());
        taxIDLabel.setText(customer.getTaxIdProp());
        paymentLabel.setText(customer.getPaymentProp().toString());
    }

    private void configureServicesTable()
    {
        orderColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(boughtServicesTableView.getItems().indexOf(param.getValue()) + 1));
        serviceNameColumn.setCellValueFactory(new PropertyValueFactory<>("serviceNameProp"));
        symbolColumn.setCellValueFactory(new PropertyValueFactory<>("symbolProp"));
        unitColumn.setCellValueFactory(new PropertyValueFactory<>("unitProp"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantityProp"));
        quantityColumn.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalStringConverter()));
        quantityColumn.setOnEditCommit(event -> { //TODO: change decimal separator to comma instead of period
            if(event.getNewValue().compareTo(BigDecimal.valueOf(9999.99)) > 0)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Wartość zbyt duża");
                alert.setContentText("Wartość musi być mniejsza niż 9999.99");
                alert.show();
            }
            else if (event.getNewValue().compareTo(BigDecimal.ZERO) < 0)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Wartość nieprawidłowa");
                alert.setContentText("Wartość nie może być ujemna");
                alert.show();
            }
            else
            {
                try {
                    boughtServicesService.update(event.getNewValue(), event.getRowValue().getBoughtService().
                            getInternalId().getId());
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

    private void populateBoughtServicesData(CustomerProps customerProps) {
        // lazy load (and only once) list of bought services
        if (customerProps.getBoughtServicesProps().isEmpty())
        {
            boughtServicesService.findBoughtServicesByCustomer(customerProps.getCustomer()).forEach(service ->
                    customerProps.addBoughtServicesProps(service.getBoughtServicesProps()));
        }
        boughtServicesTableView.setItems(customerProps.getBoughtServicesProps());
    }

    private void sumAll(CustomerProps customerProps)
    {
        BigDecimal sum = BigDecimal.ZERO;
        for (BoughtServicesProps service : customerProps.getBoughtServicesProps())
        {
            sum = sum.add(service.getTotalVal());
        }
        sumLabel.setText(sum.toString());
        sumWordsLabel.setText(CurrencyHandler.convertToWords(sum));
    }
}