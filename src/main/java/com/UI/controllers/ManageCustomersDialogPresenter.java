package com.UI.controllers;

import com.entity.Customer;
import com.entity.enums.PaymentMethod;
import com.service.ICustomerService;
import com.utilities.CustomersList;
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
public class ManageCustomersDialogPresenter {
    //region @FXML objects
    @FXML TableView<Customer> customersListTableView;
    @FXML TableColumn<Customer, String> lastNameCol;
    @FXML TableColumn<Customer, String> firstNameCol;
    @FXML TableColumn<Customer, String> companyNameCol;
    @FXML TableColumn<Customer, String> IdNumberCol;
    @FXML TableColumn<Customer, String> addressCol;
    @FXML TableColumn<Customer, String> postalCodeCol;
    @FXML TableColumn<Customer, String> cityCol;
    @FXML TableColumn<Customer, Boolean> considerCountingCol;
    @FXML TableColumn<Customer, PaymentMethod> paymentMethodCol;
    @FXML TableColumn<Customer, String> aliasCol;
    @FXML ComboBox<String> filterComboBox;
    @FXML TextField filterTextField;
    @FXML Button filterBtn;
    @FXML Button newCustomerBtn;
    @FXML Button editCustomerBtn;
    @FXML Button removeCustomerBtn;
    @Autowired
    private ICustomerService customerService;
    //endregion
    private ObservableList<String> filterCriteria = FXCollections.observableArrayList("Nazwisko", "Imię",
            "Firma", "NIP/PESEL", "Adres", "Kod pocztowy", "Miasto", "Alias");
    private FilteredList<Customer> filteredList;

    @FXML
    public void initialize() {
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

    private void initializeButtons() {
        filterBtn.setOnAction(event -> {
            filteredList.setPredicate(Customer -> {
                if (filterTextField.getText().isEmpty())
                    return true;

                else {
                    String selected = filterTextField.getText();
                    switch (filterComboBox.getSelectionModel().getSelectedItem()) {
                        case "Nazwisko":
                            if (Customer.getLastNameProp().contains(selected))
                                return true;
                            break;

                        case "Imię":
                            if (Customer.getFirstNameProp().contains(selected))
                                return true;
                            break;

                        case "Firma":
                            if (Customer.getCompanyNameProp().contains(selected))
                                return true;
                            break;

                        case "NIP/PESEL":
                            if (Customer.getTaxIdProp().contains(selected))
                                return true;
                            break;

                        case "Adres":
                            if (Customer.getAddressProp().contains(selected))
                                return true;
                            break;

                        case "Kod pocztowy":
                            if (Customer.getPostalCodeProp().contains(selected))
                                return true;
                            break;

                        case "Miasto":
                            if (Customer.getCityProp().contains(selected))
                                return true;
                            break;

                        case "Alias":
                            if (Customer.getAliasProp().contains(selected))
                                return true;
                            break;
                    }
                }

                return false;
            });
        });
        newCustomerBtn.setOnAction(event -> {
            Customer c = new Customer();
            showCustomerWindow(c, newCustomerBtn);
        });
        editCustomerBtn.setOnAction(event -> {
            if (customersListTableView.getSelectionModel().getSelectedItems().size() != 1) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Edytowanie kontrahenta");
                alert.setHeaderText("Nie można edytować kilku kontrahentów na raz lub żadnego");
                alert.setContentText("Edytować kontrahentów można tylko pojedynczo");
                alert.show();
            } else {
                showCustomerWindow(customersListTableView.getSelectionModel().getSelectedItem(), editCustomerBtn);
            }
        });
        removeCustomerBtn.setOnAction(event -> {
            if (customersListTableView.getSelectionModel().getSelectedItems().size() > 0) {
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
                        for (Customer customer : customersListTableView.getSelectionModel().getSelectedItems())
                            CustomersList.removeCustomer(customer);
                        //FIXME: the last customer from those that are selected is not being deleted
                    });
                });
            }
        });
    }

    private void showCustomerWindow(Customer customer, Button source) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edycja kontrahenta");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
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
        yesRadioBtn.setSelected(customer.getCountProp());
        try {
            if (customer.getCountProp())
                yesRadioBtn.setSelected(true);
            else
                noRadioBtn.setSelected(true);


            if (customer.getPaymentProp().name().equals("gotówka"))
                cashRadioBtn.setSelected(true);
            else
                bankRadioBtn.setSelected(true);
        } catch (NullPointerException e) {
            cashRadioBtn.setSelected(true); // select default values
            yesRadioBtn.setSelected(true);
        }

        TextField firstNameTxtFld = new TextField(customer.getFirstNameProp());
        TextField lastNameTxtFld = new TextField(customer.getLastNameProp());
        TextField companyTxtField = new TextField(customer.getCompanyNameProp());
        TextField taxIdTxtFld = new TextField(customer.getTaxIdProp());
        TextField addressTxtFld = new TextField(customer.getAddressProp());
        TextField postalCodeTxtFld = new TextField(customer.getPostalCodeProp());
        TextField cityTxtFld = new TextField(customer.getCityProp());
        TextField aliasTxtFld = new TextField(customer.getAliasProp());

        box.getChildren().addAll(new Label("Imię"), firstNameTxtFld, new Label("Nazwisko"), lastNameTxtFld,
                new Label("Firma"), companyTxtField, new Label("NIP/PESEL"), taxIdTxtFld, new Label("Adres"),
                addressTxtFld, new Label("Kod pocztowy"), postalCodeTxtFld, new Label("Miasto"), cityTxtFld,
                new Label("Uwzględnij numer faktury"), yesRadioBtn, noRadioBtn, new Label("Forma płatności"),
                cashRadioBtn, bankRadioBtn, new Label("Alias"), aliasTxtFld);

        dialog.getDialogPane().setContent(box);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get().equals(ButtonType.OK)) {
            customer.setTaxIdProp(taxIdTxtFld.getText());
            customer.setPostalCodeProp(postalCodeTxtFld.getText());
            customer.setPaymentProp(cashRadioBtn.isSelected() ? PaymentMethod.gotówka : PaymentMethod.przelew);
            customer.setLastNameProp(lastNameTxtFld.getText());
            customer.setFirstNameProp(firstNameTxtFld.getText());
            customer.setCountProp(yesRadioBtn.isSelected());
            customer.setCompanyNameProp(companyTxtField.getText());
            customer.setCityProp(cityTxtFld.getText());
            customer.setAliasProp(aliasTxtFld.getText());
            customer.setAddressProp(addressTxtFld.getText());

            if (source.equals(newCustomerBtn)) {
                CustomersList.addCustomer(customer);
            } else {
                customerService.update(customer.getLastNameProp(), customer.getFirstNameProp(),
                        customer.getCompanyNameProp(), customer.getTaxIdProp(), customer.getAddressProp(),
                        customer.getPostalCodeProp(), customer.getCityProp(), customer.getPaymentProp(),
                        customer.getCountProp(), customer.getAliasProp(), customer.getId());
            }
        }
    }
}
