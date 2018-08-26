package com.UI.controllers;

import com.UI.FxmlView;
import com.UI.SceneManager;
import com.entity.Customer;
import com.service.ICustomerService;
import javafx.event.ActionEvent;
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
        sceneManager.switchScene(FxmlView.NEW_CUSTOMER);
    }

    @FXML
    void deleteCustomer(ActionEvent event) {
        List<Customer> customers = customersTable.getSelectionModel().getSelectedItems();

        if (!customers.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete selected?");
            Optional<ButtonType> action = alert.showAndWait();

            if (action.orElse(null) == ButtonType.OK)
                customerService.deleteInBatch(customers);

            loadCustomers();
        } else
            actionAlert();
    }

    @FXML
    void editCustomer(ActionEvent event) {
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
