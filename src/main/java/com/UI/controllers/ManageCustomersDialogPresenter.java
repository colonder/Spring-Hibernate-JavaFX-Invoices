package com.UI.controllers;

import com.entity.Customer.CustomerProps;
import com.entity.PaymentMethod;
import com.utilities.classes.CustomersList;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import org.springframework.stereotype.Component;

@Component
public class ManageCustomersDialogPresenter
{
    //region @FXML objects
    @FXML TableView<CustomerProps> customersListTableView;
    @FXML TableColumn<CustomerProps, String> lastNameCol;
    @FXML TableColumn<CustomerProps, String> firstNameCol;
    @FXML TableColumn<CustomerProps, String> companyNameCol;
    @FXML TableColumn<CustomerProps, String> IdNumberCol;
    @FXML TableColumn<CustomerProps, String> addressCol;
    @FXML TableColumn<CustomerProps, String> postalCodeCol;
    @FXML TableColumn<CustomerProps, String> cityCol;
    @FXML TableColumn<CustomerProps, Boolean> considerCountingCol;
    @FXML TableColumn<CustomerProps, PaymentMethod> paymentMethodCol;
    @FXML TableColumn<CustomerProps, String> aliasCol;
    @FXML ComboBox filterComboBox;
    @FXML TextField filterTextField;
    @FXML Button filterBtn;
    @FXML Button newCustomerBtn;
    @FXML Button editCustomerBtn;
    @FXML Button removeCustomerBtn;
    //endregion

    @FXML
    public void initialize()
    {
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastNameProp"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstNameProp"));
        companyNameCol.setCellValueFactory(new PropertyValueFactory<>("companyNameProp"));
        IdNumberCol.setCellValueFactory(new PropertyValueFactory<>("taxIdProp"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("addressProp"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCodeProp"));
        cityCol.setCellValueFactory(new PropertyValueFactory<>("cityProp"));
        considerCountingCol.setCellValueFactory(new PropertyValueFactory<>("countProp"));
        paymentMethodCol.setCellValueFactory(new PropertyValueFactory<>("paymentProp"));
        aliasCol.setCellValueFactory(new PropertyValueFactory<>("aliasProp"));

        //TODO: add list sorting capability
        //TODO: write code for new controls

        customersListTableView.setItems(CustomersList.customerList);
        customersListTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        TextField searchTextBox = new TextField();
        searchTextBox.setPromptText("Wyszukaj kontrahenta");
        searchTextBox.textProperty().addListener((observable, oldValue, newValue) -> {

        });
    }
}
