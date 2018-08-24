package com.UI.controllers;

import com.UI.FxmlView;
import com.UI.SceneManager;
import com.entity.Customer;
import com.service.ICustomerService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class CustomersPresenter implements Initializable {

    //region fxmls
    @FXML
    private Button homeBtn;

    @FXML
    private Button productsBtn;

    @FXML
    private Button settingsBtn;

    @FXML
    private Button addBtn;

    @FXML
    private Button editBtn;

    @FXML
    private Button deleteBtn;

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
    private TableColumn<Customer, Integer> firmIdCol;

    @FXML
    private TableColumn<Customer, String> addressCol;

    @FXML
    private TableColumn<Customer, String> postalCodeCol;

    @FXML
    private TableColumn<Customer, String> cityCol;

    @FXML
    private TableColumn<Customer, Integer> paymentCol;
    //endregion

    @Lazy
    @Autowired
    private SceneManager sceneManager;

    @Autowired
    private ICustomerService customerService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeButtons();
        initializeTable();
        loadCustomers();
    }

    private void initializeTable() {
        customersTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        aliasCol.setCellValueFactory(new PropertyValueFactory<>("alias"));
        firmCol.setCellValueFactory(new PropertyValueFactory<>("firm_name"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("tax_id"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postal_code"));
        cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        paymentCol.setCellValueFactory(new PropertyValueFactory<>("payment"));
        firmIdCol.setCellValueFactory(new PropertyValueFactory<>("firm_id"));
    }

    private void initializeButtons() {
        productsBtn.setOnAction(actionEvent -> sceneManager.switchScene(FxmlView.PRODUCTS));
        homeBtn.setOnAction(actionEvent -> sceneManager.switchScene(FxmlView.HOME));
    }

    private void loadCustomers() {
        customersTable.getItems().clear();
        customersTable.getItems().addAll(customerService.findAll());
    }

    @FXML
    void addCustomer(ActionEvent event) {

    }

    @FXML
    void deleteCustomer(ActionEvent event) {

    }

    @FXML
    void editCustomer(ActionEvent event) {

    }
}

//public class CustomersPresenter {
//    //region fxml fields
//    @FXML private Button addCustomerBtn;
//    @FXML private TextField phraseTxtFld;
//    @FXML private Button searchBtn;
//    @FXML private CheckMenuItem aliasCheckMenuItem;
//    @FXML private CheckMenuItem lastNameCheckMenuItem;
//    @FXML private CheckMenuItem firstNameCheckMenuItem;
//    @FXML private CheckMenuItem taxIdCheckMenuItem;
//    @FXML private CheckMenuItem addressCheckMenuItem;
//    @FXML private CheckMenuItem postalCodeCheckMenuItem;
//    @FXML private CheckMenuItem cityCheckMenuItem;
//    @FXML private CheckMenuItem companyNumCheckMenuItem;
//    @FXML private CheckMenuItem paymentCheckMenuItem;
//    @FXML private TableView<Customer> customersTableView;
//    @FXML private TableColumn<Customer, String> aliasCol;
//    @FXML private TableColumn<Customer, String> lastNameCol;
//    @FXML private TableColumn<Customer, String> firstNameCol;
//    @FXML private TableColumn<Customer, String> taxIdCol;
//    @FXML private TableColumn<Customer, String> addressCol;
//    @FXML private TableColumn<Customer, String> postalCodeCol;
//    @FXML private TableColumn<Customer, String> cityCol;
//    @FXML private TableColumn<Customer, Integer> companyNumCol;
//    @FXML private TableColumn<Customer, String> paymentCol;
//    @FXML private Button editCustomerBtn;
//    @FXML private Button deleteCustomerBtn;
//    //endregion
//
//    @Autowired
//    private ICustomerService customerService;
//
//    @FXML
//    public void initialize() {
//        initButtons();
//        setSearching();
//        initCheckMenuItems();
//        initTableColumns();
//    }
//
//    private void initTableColumns() {
//        aliasCol.setCellValueFactory(new PropertyValueFactory<>("alias"));
//        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
//        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
//        taxIdCol.setCellValueFactory(new PropertyValueFactory<>("taxIdentifier"));
//        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
//        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
//        cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
//        companyNumCol.setCellValueFactory(new PropertyValueFactory<>("companySpecialNumber"));
//    }
//
//    private void initButtons() {
////        addCustomerBtn.setOnAction(event -> SceneManager.openAndInitialize(newCustomerView, new Customer()));
////        editCustomerBtn.setOnAction(event -> {
////            if (customersTableView.getSelectionModel().getSelectedItems().size() != 0) {
////                if (customersTableView.getSelectionModel().getSelectedItems().size() == 1)
////                    SceneManager.openAndInitialize(newCustomerView, customersTableView
////                            .getSelectionModel().getSelectedItem());
////                else {
////                    Alert alert = new Alert(Alert.AlertType.WARNING);
////                    alert.setTitle("Editing customer");
////                    alert.setHeaderText("An error occurred while editing objects");
////                    alert.setContentText("You can't edit several objects simultaneously");
////                    alert.showAndWait();
////                }
////            }
////        });
//        deleteCustomerBtn.setOnAction(event -> {
//            if (customersTableView.getSelectionModel().getSelectedItems().size() != 0) {
//                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//                alert.setTitle("Invoices deletion");
//                alert.setHeaderText("Are you sure you want to delete selected customers?");
//
//                Optional<ButtonType> result = alert.showAndWait();
//                result.ifPresent(choice -> {
//                    if (choice.equals(ButtonType.OK)) {
//                        for (Customer customer : customersTableView.getSelectionModel().getSelectedItems()) {
//                            customerService.delete(customer);
//                        }
//                        search();
//                    }
//                });
//            }
//        });
//    }
//
//    private void initCheckMenuItems() {
//        aliasCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
//                aliasCol.setVisible(newValue));
//        lastNameCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
//                lastNameCol.setVisible(newValue));
//        firstNameCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
//                firstNameCol.setVisible(newValue));
//        taxIdCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
//                taxIdCol.setVisible(newValue));
//        addressCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
//                addressCol.setVisible(newValue));
//        postalCodeCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
//                postalCodeCol.setVisible(newValue));
//        cityCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
//                cityCol.setVisible(newValue));
//        companyNumCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
//                companyNumCol.setVisible(newValue));
//        paymentCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
//                paymentCol.setVisible(newValue));
//    }
//
//    private void setSearching() {
//        phraseTxtFld.setOnKeyPressed(event -> {
//            if (!phraseTxtFld.getText().isEmpty() && event.getCode().equals(KeyCode.ENTER)) {
//                search();
//            }
//        });
//
//        searchBtn.setOnAction(event -> search());
//    }
//
//    private void search() {
//
////        customersTableView.getItems().setAll(customerService.findAll(CustomerType.customerMap.get(customerTypeComboBox
////                .getSelectionModel().getSelectedItem())));
//    }
//}
