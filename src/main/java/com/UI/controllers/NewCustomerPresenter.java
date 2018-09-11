package com.UI.controllers;

import com.UI.FxmlView;
import com.UI.SceneManager;
import com.entity.Customer;
import com.service.ICustomerService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class NewCustomerPresenter implements Initializable {

    @FXML
    private Button saveBtn;

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

    private void saveCustomer() {
        Customer customer = new Customer();
        setCustomerProperties(customer);

        Customer newCustomer = customerService.save(customer);

        saveAlert(newCustomer);
        clearFields();
        sceneManager.switchScene(FxmlView.CUSTOMERS);
    }

    private void updateCustomer(Customer customer) {
        setCustomerProperties(customer);

        Customer updatedCustomer = customerService.update(customer);
        updateAlert(updatedCustomer);
        clearFields();
        sceneManager.switchScene(FxmlView.CUSTOMERS);
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
        customer.setPayment(cashRadioBtn.isSelected() ? "Cash" : "Bank transfer");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        saveBtn.setOnAction(actionEvent -> saveCustomer());
    }

    public void initData(Customer customer) {
        saveBtn.setOnAction(actionEvent -> updateCustomer(customer));

        aliasTxtFld.setText(customer.getAlias());
        nameTxtFld.setText(customer.getFirstName());
        lastNameTxtFld.setText(customer.getLastName());
        idTxtFld.setText(customer.getTaxId());
        firmNameTxtFld.setText(customer.getFirmName());
        addressTxtFld.setText(customer.getAddress());
        postalTxtFld.setText(customer.getPostalCode());
        cityTxtFld.setText(customer.getCity());
        cashRadioBtn.setSelected(customer.getPayment().equals("Cash"));
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
        alert.setTitle("User saved successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The user " + customer.getAlias() + " has been created with an id " + customer.getId() + ".");
        alert.showAndWait();
    }

    private void updateAlert(Customer customer) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("User updated successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The user " + customer.getAlias() + " has been updated.");
        alert.showAndWait();
    }
}
