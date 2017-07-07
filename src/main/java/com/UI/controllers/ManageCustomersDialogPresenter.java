package com.UI.controllers;

import com.entity.Customer;
import com.entity.PaymentMethod;
import com.utilities.classes.CustomersList;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import org.springframework.stereotype.Component;

@Component
public class ManageCustomersDialogPresenter
{
    @FXML TableView<Customer.CustomerProps> customersListTableView;
    @FXML TableColumn<Customer.CustomerProps, String> lastNameCol;
    @FXML TableColumn<Customer.CustomerProps, String> firstNameCol;
    @FXML TableColumn<Customer.CustomerProps, String> companyNameCol;
    @FXML TableColumn<Customer.CustomerProps, String> IdNumberCol;
    @FXML TableColumn<Customer.CustomerProps, String> addressCol;
    @FXML TableColumn<Customer.CustomerProps, String> postalCodeCol;
    @FXML TableColumn<Customer.CustomerProps, String> cityCol;
    @FXML TableColumn<Customer.CustomerProps, Boolean> considerCountingCol;
    @FXML TableColumn<Customer.CustomerProps, PaymentMethod> paymentMethodCol;
    @FXML TableColumn<Customer.CustomerProps, String> aliasCol;

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

        customersListTableView.setItems(CustomersList.customerList);

        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        TextField searchTextBox = new TextField();
        searchTextBox.setPromptText("Wyszukaj kontrahenta");
        searchTextBox.textProperty().addListener((observable, oldValue, newValue) -> {

            // to decrease numbers of database queries
            pause.playFromStart();
            /*switch (comboBox.getSelectionModel().getSelectedItem())
            {
                case "Imię":
                    data.addAll(customerService.findAllByFirstNameContaining(newValue));
                    break;

                case "Nazwisko":
                    data.addAll(customerService.findAllByLastNameContaining(newValue));
                    break;

                case "Alias":
                    data.addAll(customerService.findAllByAliasContaining(newValue));
                    break;

                case "Nazwa firmy":
                    data.addAll(customerService.findAllByCompanyNameContaining(newValue));
                    break;

                case "PESEL/NIP":
                    data.addAll(customerService.findAllByTaxIdentifierContaining(newValue));
                    break;

                case "Adres":
                    data.addAll(customerService.findAllByAddressContaining(newValue));
                    break;

                case "Kod pocztowy":
                    data.addAll(customerService.findAllByPostalCodeContaining(newValue));
                    break;

                case "Miejscowość":
                    data.addAll(customerService.findAllByCityContaining(newValue));
                    break;

                case "Sposób zapłaty":
                    data.addAll(customerService.findAllByPaymentMethod(newValue));
                    break;

                case "Uwzględnij nr faktury":
                    data.addAll(customerService.findAllByIncludeInCount(newValue));
                    break;
            }*/

        });
    }
}
