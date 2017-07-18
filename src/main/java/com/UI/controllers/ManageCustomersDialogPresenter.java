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
public class ManageCustomersDialogPresenter {
    //region @FXML objects
    @FXML
    TableView<CustomerProps> customersListTableView;
    @FXML
    TableColumn<CustomerProps, String> lastNameCol;
    @FXML
    TableColumn<CustomerProps, String> firstNameCol;
    @FXML
    TableColumn<CustomerProps, String> companyNameCol;
    @FXML
    TableColumn<CustomerProps, String> IdNumberCol;
    @FXML
    TableColumn<CustomerProps, String> addressCol;
    @FXML
    TableColumn<CustomerProps, String> postalCodeCol;
    @FXML
    TableColumn<CustomerProps, String> cityCol;
    @FXML
    TableColumn<CustomerProps, Boolean> considerCountingCol;
    @FXML
    TableColumn<CustomerProps, PaymentMethod> paymentMethodCol;
    @FXML
    TableColumn<CustomerProps, String> aliasCol;
    @FXML
    ComboBox<String> filterComboBox;
    @FXML
    TextField filterTextField;
    @FXML
    Button filterBtn;
    @FXML
    Button newCustomerBtn;
    @FXML
    Button editCustomerBtn;
    @FXML
    Button removeCustomerBtn;
    @Autowired
    private ICustomerService customerService;
    //endregion
    private ObservableList<String> filterCriteria = FXCollections.observableArrayList("Nazwisko", "Imię",
            "Firma", "NIP/PESEL", "Adres", "Kod pocztowy", "Miasto", "Alias");
    private FilteredList<CustomerProps> filteredList;

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
            filteredList.setPredicate(customerProps -> {
                if (filterTextField.getText().isEmpty())
                    return true;

                else {
                    String selected = filterTextField.getText();
                    switch (filterComboBox.getSelectionModel().getSelectedItem()) {
                        case "Nazwisko":
                            if (customerProps.getLastNameProp().contains(selected))
                                return true;
                            break;

                        case "Imię":
                            if (customerProps.getFirstNameProp().contains(selected))
                                return true;
                            break;

                        case "Firma":
                            if (customerProps.getCompanyNameProp().contains(selected))
                                return true;
                            break;

                        case "NIP/PESEL":
                            if (customerProps.getTaxIdProp().contains(selected))
                                return true;
                            break;

                        case "Adres":
                            if (customerProps.getAddressProp().contains(selected))
                                return true;
                            break;

                        case "Kod pocztowy":
                            if (customerProps.getPostalCodeProp().contains(selected))
                                return true;
                            break;

                        case "Miasto":
                            if (customerProps.getCityProp().contains(selected))
                                return true;
                            break;

                        case "Alias":
                            if (customerProps.getAliasProp().contains(selected))
                                return true;
                            break;
                    }
                }

                return false;
            });
        });
        newCustomerBtn.setOnAction(event -> {
            Customer c = new Customer();
            showCustomerWindow(c.new CustomerProps(), newCustomerBtn);
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
                        for (CustomerProps props : customersListTableView.getSelectionModel().getSelectedItems())
                            CustomersList.removeCustomer(props);
                        //FIXME: the last customer from those that are selected is not being deleted
                    });
                });
            }
        });
    }

    private void showCustomerWindow(CustomerProps props, Button source) {
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
        yesRadioBtn.setSelected(props.getCountProp());
        try {
            if (props.getCountProp())
                yesRadioBtn.setSelected(true);
            else
                noRadioBtn.setSelected(true);


            if (props.getPaymentProp().name().equals("gotówka"))
                cashRadioBtn.setSelected(true);
            else
                bankRadioBtn.setSelected(true);
        } catch (NullPointerException e) {
            cashRadioBtn.setSelected(true); // select default values
            yesRadioBtn.setSelected(true);
        }

        TextField firstNameTxtFld = new TextField(props.getFirstNameProp());
        TextField lastNameTxtFld = new TextField(props.getLastNameProp());
        TextField companyTxtField = new TextField(props.getCompanyNameProp());
        TextField taxIdTxtFld = new TextField(props.getTaxIdProp());
        TextField addressTxtFld = new TextField(props.getAddressProp());
        TextField postalCodeTxtFld = new TextField(props.getPostalCodeProp());
        TextField cityTxtFld = new TextField(props.getCityProp());
        TextField aliasTxtFld = new TextField(props.getAliasProp());

        box.getChildren().addAll(new Label("Imię"), firstNameTxtFld, new Label("Nazwisko"), lastNameTxtFld,
                new Label("Firma"), companyTxtField, new Label("NIP/PESEL"), taxIdTxtFld, new Label("Adres"),
                addressTxtFld, new Label("Kod pocztowy"), postalCodeTxtFld, new Label("Miasto"), cityTxtFld,
                new Label("Uwzględnij numer faktury"), yesRadioBtn, noRadioBtn, new Label("Forma płatności"),
                cashRadioBtn, bankRadioBtn, new Label("Alias"), aliasTxtFld);

        dialog.getDialogPane().setContent(box);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get().equals(ButtonType.OK)) {
            props.setTaxIdProp(taxIdTxtFld.getText());
            props.setPostalCodeProp(postalCodeTxtFld.getText());
            props.setPaymentProp(cashRadioBtn.isSelected() ? PaymentMethod.gotówka : PaymentMethod.przelew);
            props.setLastNameProp(lastNameTxtFld.getText());
            props.setFirstNameProp(firstNameTxtFld.getText());
            props.setCountProp(yesRadioBtn.isSelected());
            props.setCompanyNameProp(companyTxtField.getText());
            props.setCityProp(cityTxtFld.getText());
            props.setAliasProp(aliasTxtFld.getText());
            props.setAddressProp(addressTxtFld.getText());

            if (source.equals(newCustomerBtn)) {
                CustomersList.addCustomer(props);
            } else {
                Customer tmp = props.getCustomer();
                customerService.update(tmp.getLastName(), tmp.getFirstName(), tmp.getCompanyName(), tmp.getTaxIdentifier(),
                        tmp.getAddress(), tmp.getPostalCode(), tmp.getCity(), tmp.getPaymentMethod(), tmp.isIncludeInCount(),
                        tmp.getAlias(), tmp.getId());
            }
        }
    }
}
