package com.UI.controllers;

import com.entity.Customer;
import com.service.ICustomerService;
import com.UI.SceneManager;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Controller
public class CustomersPresenter {
    //region fxml fields
    @FXML private Button addCustomerBtn;
    @FXML private TextField phraseTxtFld;
    @FXML private Button searchBtn;
    @FXML private CheckMenuItem aliasCheckMenuItem;
    @FXML private CheckMenuItem lastNameCheckMenuItem;
    @FXML private CheckMenuItem firstNameCheckMenuItem;
    @FXML private CheckMenuItem taxIdCheckMenuItem;
    @FXML private CheckMenuItem addressCheckMenuItem;
    @FXML private CheckMenuItem postalCodeCheckMenuItem;
    @FXML private CheckMenuItem cityCheckMenuItem;
    @FXML private CheckMenuItem companyNumCheckMenuItem;
    @FXML private CheckMenuItem paymentCheckMenuItem;
    @FXML private TableView<Customer> customersTableView;
    @FXML private TableColumn<Customer, String> aliasCol;
    @FXML private TableColumn<Customer, String> lastNameCol;
    @FXML private TableColumn<Customer, String> firstNameCol;
    @FXML private TableColumn<Customer, String> taxIdCol;
    @FXML private TableColumn<Customer, String> addressCol;
    @FXML private TableColumn<Customer, String> postalCodeCol;
    @FXML private TableColumn<Customer, String> cityCol;
    @FXML private TableColumn<Customer, Integer> companyNumCol;
    @FXML private TableColumn<Customer, String> paymentCol;
    @FXML private Button editCustomerBtn;
    @FXML private Button deleteCustomerBtn;
    //endregion

    @Autowired
    private ICustomerService customerService;

    @FXML
    public void initialize() {
        initButtons();
        setSearching();
        initCheckMenuItems();
        initTableColumns();
    }

    private void initTableColumns() {
        aliasCol.setCellValueFactory(new PropertyValueFactory<>("alias"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        taxIdCol.setCellValueFactory(new PropertyValueFactory<>("taxIdentifier"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        companyNumCol.setCellValueFactory(new PropertyValueFactory<>("companySpecialNumber"));
    }

    private void initButtons() {
//        addCustomerBtn.setOnAction(event -> SceneManager.openAndInitialize(newCustomerView, new Customer()));
//        editCustomerBtn.setOnAction(event -> {
//            if (customersTableView.getSelectionModel().getSelectedItems().size() != 0) {
//                if (customersTableView.getSelectionModel().getSelectedItems().size() == 1)
//                    SceneManager.openAndInitialize(newCustomerView, customersTableView
//                            .getSelectionModel().getSelectedItem());
//                else {
//                    Alert alert = new Alert(Alert.AlertType.WARNING);
//                    alert.setTitle("Editing customer");
//                    alert.setHeaderText("An error occurred while editing objects");
//                    alert.setContentText("You can't edit several objects simultaneously");
//                    alert.showAndWait();
//                }
//            }
//        });
        deleteCustomerBtn.setOnAction(event -> {
            if (customersTableView.getSelectionModel().getSelectedItems().size() != 0) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Invoices deletion");
                alert.setHeaderText("Are you sure you want to delete selected customers?");

                Optional<ButtonType> result = alert.showAndWait();
                result.ifPresent(choice -> {
                    if (choice.equals(ButtonType.OK)) {
                        for (Customer customer : customersTableView.getSelectionModel().getSelectedItems()) {
                            customerService.delete(customer);
                        }
                        search();
                    }
                });
            }
        });
    }

    private void initCheckMenuItems() {
        aliasCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                aliasCol.setVisible(newValue));
        lastNameCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                lastNameCol.setVisible(newValue));
        firstNameCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                firstNameCol.setVisible(newValue));
        taxIdCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                taxIdCol.setVisible(newValue));
        addressCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                addressCol.setVisible(newValue));
        postalCodeCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                postalCodeCol.setVisible(newValue));
        cityCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                cityCol.setVisible(newValue));
        companyNumCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                companyNumCol.setVisible(newValue));
        paymentCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                paymentCol.setVisible(newValue));
    }

    private void setSearching() {
        phraseTxtFld.setOnKeyPressed(event -> {
            if (!phraseTxtFld.getText().isEmpty() && event.getCode().equals(KeyCode.ENTER)) {
                search();
            }
        });

        searchBtn.setOnAction(event -> search());
    }

    private void search() {

//        customersTableView.getItems().setAll(customerService.findAll(CustomerType.customerMap.get(customerTypeComboBox
//                .getSelectionModel().getSelectedItem())));
    }
}
