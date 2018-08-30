package com.UI.controllers;

import com.UI.FxmlView;
import com.UI.SceneManager;
import com.entity.Customer;
import com.entity.Product;
import com.service.ICustomerService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class HomePresenter implements Initializable {

    //region fxmls

    @FXML
    private Label customerLbl;

    @FXML
    private TableColumn<Product, String> nameCol;

    @FXML
    private TableColumn<Product, String> symbolCol;

    @FXML
    private TableColumn<?, BigDecimal> amountCol;

    @FXML
    private TableColumn<Product, String> unitCol;

    @FXML
    private TableColumn<?, BigDecimal> netCol;

    @FXML
    private TableColumn<Product, BigDecimal> vatRateCol;

    @FXML
    private TableColumn<?, BigDecimal> vatValCol;

    @FXML
    private TableColumn<Product, BigDecimal> grossCol;

    @FXML
    private TableColumn<?, ?> deleteCol;

    @FXML
    private Label totalLbl;

    @FXML
    private Label wordsLbl;

    @FXML
    private ListView<Customer> customersList;

    @FXML
    private DatePicker datePicker;
    //endregion

    @Lazy
    @Autowired
    private SceneManager sceneManager;

    @Autowired
    private ICustomerService customerService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTable();
        setListViewProperties();
        loadCustomers();
    }

    private void setListViewProperties() {
        customersList.setCellFactory(param -> new ListCell<Customer>() {
            @Override
            protected void updateItem(Customer item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getAlias() == null) {
                    setText(null);
                } else {
                    setText(item.getAlias());
                }
            }
        });
        customersList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        customersList.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldVal, newVal) -> {
            customerLbl.setText(newVal.getAlias());
            // add loading template of invoice from database
        }));
    }

    private void loadCustomers() {
        customersList.getItems().clear();
        customersList.getItems().addAll(customerService.findAll());
        customersList.getSelectionModel().selectFirst();
    }

    private void initializeTable() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        symbolCol.setCellValueFactory(new PropertyValueFactory<>("symbol"));
        unitCol.setCellValueFactory(new PropertyValueFactory<>("unit"));
        grossCol.setCellValueFactory(new PropertyValueFactory<>("grossPrice"));
        vatRateCol.setCellValueFactory(new PropertyValueFactory<>("vatRate"));
    }

    @FXML
    void switchToCustomers() {
        sceneManager.switchScene(FxmlView.CUSTOMERS);
    }

    @FXML
    void switchToProducts() {
        sceneManager.switchScene(FxmlView.PRODUCTS);
    }

    @FXML
    void firstCustomer() {
        customersList.getSelectionModel().selectFirst();
    }

    @FXML
    void lastCustomer() {
        customersList.getSelectionModel().selectLast();
    }

    @FXML
    void prevCustomer() {
        customersList.getSelectionModel().selectPrevious();
    }

    @FXML
    void nextCustomer() {
        customersList.getSelectionModel().selectNext();
    }
}
