package com.UI.controllers;

import com.UI.FxmlView;
import com.UI.SceneManager;
import com.entity.Customer;
import com.entity.Templates;
import com.service.ICustomerService;
import com.service.ITemplatesService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@Controller
public class CustomersPresenter implements Initializable {

    //region fxmls
    @FXML
    private TableView<Customer> customersTable;

    @FXML
    private TableColumn<Customer, String> aliasCol;

    @FXML
    private TableColumn<Customer, String> nameCol;

    @FXML
    private TableColumn<Customer, String> lastNameCol;

    @FXML
    private TableColumn<Customer, String> idCol;

    @FXML
    private TableColumn<Customer, String> firmCol;

    @FXML
    private TableColumn<Customer, String> addressCol;

    @FXML
    private TableColumn<Customer, String> postalCodeCol;

    @FXML
    private TableColumn<Customer, String> cityCol;

    @FXML
    private TableColumn<Customer, String> paymentCol;
    //endregion

    @Lazy
    @Autowired
    private SceneManager sceneManager;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private ITemplatesService templatesService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTable();
        loadCustomers();
    }

    private void initializeTable() {
        customersTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        aliasCol.setCellValueFactory(new PropertyValueFactory<>("alias"));
        firmCol.setCellValueFactory(new PropertyValueFactory<>("firmName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("taxId"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        paymentCol.setCellValueFactory(new PropertyValueFactory<>("payment"));
    }

    private void loadCustomers() {
        customersTable.getItems().clear();
        customersTable.getItems().addAll(customerService.findAll());
    }

    @FXML
    void switchToProducts() {
        sceneManager.switchScene(FxmlView.PRODUCTS);
    }

    @FXML
    void switchToHome() {
        sceneManager.switchScene(FxmlView.HOME);
    }

    @FXML
    void switchToSettings() {
        sceneManager.switchScene(FxmlView.SETTINGS);
    }

    @FXML
    void addCustomer() {
        sceneManager.switchScene(FxmlView.NEW_CUSTOMER);
    }

    @FXML
    void deleteCustomer() {
        List<Customer> customers = customersTable.getSelectionModel().getSelectedItems();

        if (!customers.isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            List<Templates> templates = templatesService.findByCustomerIn(customers);

            if (!templates.isEmpty()) {
                alert.setContentText("There are saved templates containing for this customer. " +
                        "Deleting it will also delete those templates. Are you sure you want to delete selected?");
                Optional<ButtonType> action = alert.showAndWait();

                if (action.orElse(null) == ButtonType.OK) {
                    templatesService.deleteInBatch(templates);
                    customerService.deleteInBatch(customers);
                }
            } else {
                alert.setContentText("Are you sure you want to delete selected?");
                Optional<ButtonType> action = alert.showAndWait();
                if (action.orElse(null) == ButtonType.OK) {
                    customerService.deleteInBatch(customers);
                }
            }

            loadCustomers();
        } else {
            actionAlert();
        }
    }

    @FXML
    void editCustomer() {
        Parent rootNode = null;

        FXMLLoader loader = sceneManager.getLoader(FxmlView.NEW_CUSTOMER);
        try {
            rootNode = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        NewCustomerPresenter presenter = loader.getController();
        try {
            presenter.initData(customersTable.getSelectionModel().getSelectedItem());
            sceneManager.show(rootNode, FxmlView.NEW_CUSTOMER.getTitle());
        } catch (NullPointerException e) {
            actionAlert();
        }
    }

    private void actionAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Editing customer");
        alert.setContentText("You have to select customer to edit");
        alert.showAndWait();
    }
}
