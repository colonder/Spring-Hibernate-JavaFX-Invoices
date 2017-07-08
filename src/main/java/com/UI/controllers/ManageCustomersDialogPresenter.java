package com.UI.controllers;

import com.entity.Customer.CustomerProps;
import com.entity.PaymentMethod;
import com.utilities.classes.CustomersList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    @FXML ComboBox<String> filterComboBox;
    @FXML TextField filterTextField;
    @FXML Button filterBtn;
    @FXML Button newCustomerBtn;
    @FXML Button editCustomerBtn;
    @FXML Button removeCustomerBtn;
    //endregion

    private ObservableList<String> filterCriteria = FXCollections.observableArrayList("Nazwisko", "Imię",
            "Firma", "NIP/PESEL", "Adres", "Kod pocztowy", "Miasto", "Alias");
    private FilteredList<CustomerProps> filteredList;

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

        //initially display all tha data
        filteredList = new FilteredList<>(CustomersList.customerList, p -> true);
        customersListTableView.setItems(filteredList);

        customersListTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        filterComboBox.setItems(filterCriteria);
        filterComboBox.getSelectionModel().select(0);

        initializeButtons();
    }

    private void initializeButtons()
    {
        filterBtn.setOnAction(event -> {
            filteredList.setPredicate(customerProps -> {
                if(filterTextField.getText().isEmpty())
                    return true;

                else
                {
                    String selected = filterTextField.getText();
                    switch (filterComboBox.getSelectionModel().getSelectedItem())
                    {
                        case "Nazwisko":
                            if(customerProps.getLastNameProp().contains(selected))
                                return true;
                            break;

                        case "Imię":
                            if(customerProps.getFirstNameProp().contains(selected))
                                return true;
                            break;

                        case "Firma":
                            if(customerProps.getCompanyNameProp().contains(selected))
                                return true;
                            break;

                        case "NIP/PESEL":
                            if(customerProps.getTaxIdProp().contains(selected))
                                return true;
                            break;

                        case "Adres":
                            if(customerProps.getAddressProp().contains(selected))
                                return true;
                            break;

                        case "Kod pocztowy":
                            if(customerProps.getPostalCodeProp().contains(selected))
                                return true;
                            break;

                        case "Miasto":
                            if(customerProps.getCityProp().contains(selected))
                                return true;
                            break;

                        case "Alias":
                            if(customerProps.getAliasProp().contains(selected))
                                return true;
                            break;
                    }
                }

                return false;
            });
        });

        newCustomerBtn.setOnAction(event -> {

        });

        editCustomerBtn.setOnAction(event -> {

        });

        removeCustomerBtn.setOnAction(event -> {

        });
    }
}
