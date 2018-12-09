package com.UI.controllers;

import com.UI.FxmlView;
import com.UI.SceneManager;
import com.entity.Customer;
import com.service.ICustomerService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class NewCustomerPresenter extends NewItemController implements Initializable {

    @FXML
    private TextField aliasTxtFld;

    @FXML
    private TextField nameTxtFld;

    @FXML
    private TextField lastNameTxtFld;

    @FXML
    private TextField idTxtFld;

    @FXML
    private TextField firmNameTxtFld;

    @FXML
    private TextField addressTxtFld;

    @FXML
    private TextField postalTxtFld;

    @FXML
    private TextField cityTxtFld;

    @FXML
    private RadioButton transferRadioBtn;

    @FXML
    private RadioButton cashRadioBtn;

    @Autowired
    @Lazy
    private SceneManager sceneManager;

    @Autowired
    private ICustomerService customerService;

    @FXML
    void cancel() {
        clearFields();
        sceneManager.switchScene(FxmlView.CUSTOMERS);
    }

    private Customer customer;

    @FXML
    private void saveCustomer() {
        if (validate("Imię", nameTxtFld.getText(), "[a-zA-ZĄąĆćĘęŁłŃńÓóŚśŻżŹź\\s]+", true) &&
                validate("Nazwisko", lastNameTxtFld.getText(), "[a-zA-ZĄąĆćĘęŁłŃńÓóŚśŻżŹź\\s\\-]+", true) &&
                validate("NIP/PESEL", idTxtFld.getText(), "[\\d\\-?]+", false) &&
                validate("Kod pocztowy", postalTxtFld.getText(), "\\d{2}\\-\\d{3}", false) &&
                validate("Miasto", cityTxtFld.getText(), "[a-zA-ZĄąĆćĘęŁłŃńÓóŚśŻżŹź\\s]+", false) &&
                emptyValidation("Adres", addressTxtFld.getText().isEmpty()) &&
                emptyValidation("Nazwa", aliasTxtFld.getText().isEmpty())) {
            if (this.customer == null) {
                this.customer = new Customer();
            }
            setCustomerProperties(this.customer);

            Customer newCustomer = customerService.save(this.customer);

            saveAlert(newCustomer);
            clearFields();
            sceneManager.switchScene(FxmlView.CUSTOMERS);
        }
    }

    private void setCustomerProperties(Customer customer) {
        customer.setAlias(aliasTxtFld.getText());
        customer.setFirstName(nameTxtFld.getText());
        customer.setLastName(lastNameTxtFld.getText());
        customer.setTaxId(idTxtFld.getText());
        customer.setFirmName(firmNameTxtFld.getText());
        customer.setAddress(addressTxtFld.getText());
        customer.setPostalCode(postalTxtFld.getText());
        customer.setCity(cityTxtFld.getText());
        customer.setPayment(cashRadioBtn.isSelected() ? "Gotówką" : "Przelewem");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.customer = null;
    }

    public void initData(Customer customer) {
        this.customer = customer;

        aliasTxtFld.setText(customer.getAlias());
        nameTxtFld.setText(customer.getFirstName());
        lastNameTxtFld.setText(customer.getLastName());
        idTxtFld.setText(customer.getTaxId());
        firmNameTxtFld.setText(customer.getFirmName());
        addressTxtFld.setText(customer.getAddress());
        postalTxtFld.setText(customer.getPostalCode());
        cityTxtFld.setText(customer.getCity());
        transferRadioBtn.setSelected(customer.getPayment().equals("Przelewem"));
    }

    private void clearFields() {
        aliasTxtFld.setText(null);
        nameTxtFld.setText(null);
        lastNameTxtFld.setText(null);
        idTxtFld.setText(null);
        firmNameTxtFld.setText(null);
        addressTxtFld.setText(null);
        postalTxtFld.setText(null);
        cityTxtFld.setText(null);
        cashRadioBtn.setSelected(true);
    }

    private void saveAlert(Customer customer) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Kontrahent zapisany");
        alert.setHeaderText(null);
        alert.setContentText("Kontrahent " + customer.getAlias() + " został zaktualizowany");
        alert.showAndWait();
    }
}
