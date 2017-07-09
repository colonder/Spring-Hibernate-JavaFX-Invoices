package com.UI.controllers;

import com.entity.Customer;
import com.entity.Customer.CustomerProps;
import com.entity.PaymentMethod;
import com.service.ICustomerService;
import com.utilities.classes.CustomersList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ManageCustomersDialogPresenter
{
    @Autowired
    private ICustomerService customerService;

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
        newCustomerBtn.setOnAction(event -> showCustomerWindow(null));
        editCustomerBtn.setOnAction(event -> {
            if (customersListTableView.getSelectionModel().getSelectedItems().size() != 1)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Edytowanie kontrahenta");
                alert.setHeaderText("Nie można edytować kilku kontrahentów na raz lub żadnego");
                alert.setContentText("Edytować kontrahentów można tylko pojedynczo");
                alert.show();
            }

            else
            {
                showCustomerWindow(customersListTableView.getSelectionModel().getSelectedItem());
            }
        });

        removeCustomerBtn.setOnAction(event -> {
            if(customersListTableView.getSelectionModel().getSelectedItems().size() > 0)
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Usunięcie kontrahenta");
                alert.setHeaderText("Czy na pewno chcesz usunąć wybranych kontrahentów?");
                Optional<ButtonType> result = alert.showAndWait();
                result.ifPresent(e -> {
                    Alert lastStand = new Alert(Alert.AlertType.CONFIRMATION);
                    lastStand.setTitle("Usunięcie kontrahenta");
                    lastStand.setHeaderText("To jest ostatni moment kiedy możesz się wycofać!");

                    Optional<ButtonType> finalResult = lastStand.showAndWait();
                    finalResult.ifPresent(c -> {
                        for (CustomerProps props : customersListTableView.getSelectionModel().getSelectedItems())
                            CustomersList.removeCustomer(props);
                        //FIXME: the last customer from those that are selected is not being deleted
                    });
                });
            }
        });
    }

    private void showCustomerWindow(CustomerProps props)
    {
        Dialog<Customer> dialog = new Dialog<>();
        dialog.setTitle("Edycja kontrahenta");
        ButtonType acceptBtnType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(acceptBtnType, ButtonType.CANCEL);
        VBox box = new VBox(5);
        RadioButton yesRadioBtn = new RadioButton("Tak");
        RadioButton noRadioBtn = new RadioButton("Nie");
        RadioButton cashRadioBtn = new RadioButton("Gotówka");
        RadioButton bankRadioBtn = new RadioButton("Przelew");
        ToggleGroup countGroup = new ToggleGroup();
        ToggleGroup paymentGroup = new ToggleGroup();
        yesRadioBtn.setToggleGroup(countGroup);
        noRadioBtn.setToggleGroup(countGroup);
        cashRadioBtn.setToggleGroup(paymentGroup);
        bankRadioBtn.setToggleGroup(paymentGroup);
        yesRadioBtn.setSelected(true);
        cashRadioBtn.setSelected(true);

        TextField firstNameTxtFld;
        TextField lastNameTxtFld;
        TextField companyTxtField;
        TextField taxIdTxtFld;
        TextField addressTxtFld;
        TextField postalCodeTxtFld;
        TextField cityTxtFld;
        TextField aliasTxtFld;
        if(props != null)
        {
            firstNameTxtFld = new TextField(props.getFirstNameProp());
            lastNameTxtFld = new TextField(props.getLastNameProp());
            companyTxtField = new TextField(props.getCompanyNameProp());
            taxIdTxtFld = new TextField(props.getTaxIdProp());
            addressTxtFld = new TextField(props.getAddressProp());
            postalCodeTxtFld = new TextField(props.getPostalCodeProp());
            cityTxtFld = new TextField(props.getCityProp());
            aliasTxtFld = new TextField(props.getAliasProp());
        }
        else
        {
            firstNameTxtFld = new TextField();
            lastNameTxtFld = new TextField();
            companyTxtField = new TextField();
            taxIdTxtFld = new TextField();
            addressTxtFld = new TextField();
            postalCodeTxtFld = new TextField();
            cityTxtFld = new TextField();
            aliasTxtFld = new TextField();
        }

        box.getChildren().addAll(new Label("Imię"), firstNameTxtFld, new Label("Nazwisko"), lastNameTxtFld,
                new Label("Firma"), companyTxtField, new Label("NIP/PESEL"), taxIdTxtFld, new Label("Adres"),
                addressTxtFld, new Label("Kod pocztowy"), postalCodeTxtFld, new Label("Miasto"), cityTxtFld,
                new Label("Uwzględnij numer faktury"), yesRadioBtn, noRadioBtn, new Label("Forma płatności"),
                cashRadioBtn, bankRadioBtn, new Label("Alias"), aliasTxtFld);

        dialog.getDialogPane().setContent(box);
        dialog.setResultConverter(dialogButton -> {
            if(dialogButton == acceptBtnType)
            {
                return new Customer(lastNameTxtFld.getText(), firstNameTxtFld.getText(), companyTxtField.getText(),
                        taxIdTxtFld.getText(), addressTxtFld.getText(), postalCodeTxtFld.getText(), cityTxtFld.getText(),
                        (cashRadioBtn.isSelected()) ? PaymentMethod.gotówka : PaymentMethod.przelew,
                        yesRadioBtn.isSelected(), aliasTxtFld.getText());
                //FIXME: Need to write native query for inserting values into postgres with casted enum values
            }

            return null;
        });
        Optional<Customer> result = dialog.showAndWait();
        result.ifPresent(c -> {
            customerService.save(c);
            CustomersList.addCustomer(c);
        });
    }
}
