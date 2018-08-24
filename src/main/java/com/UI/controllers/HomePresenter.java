package com.UI.controllers;

import com.UI.FxmlView;
import com.UI.SceneManager;
import com.entity.Product;
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
    private Button customersBtn;

    @FXML
    private Button productsBtn;

    @FXML
    private Button printBtn;

    @FXML
    private Button settingsBtn;

    @FXML
    private Label customerLbl;

    @FXML
    private Button addProductBtn;

    @FXML
    private TableColumn<Product, String> nameCol;

    @FXML
    private TableColumn<Product, String> symbolCol;

    @FXML
    private TableColumn<?, ?> amountCol;

    @FXML
    private TableColumn<Product, String> unitCol;

    @FXML
    private TableColumn<?, ?> netCol;

    @FXML
    private TableColumn<Product, BigDecimal> vatRateCol;

    @FXML
    private TableColumn<?, ?> vatValCol;

    @FXML
    private TableColumn<Product, BigDecimal> grossCol;

    @FXML
    private TableColumn<?, ?> deleteCol;

    @FXML
    private Label totalLbl;

    @FXML
    private Label wordsLbl;

    @FXML
    private Button firstBtn;

    @FXML
    private Button prevBtn;

    @FXML
    private Button nextBtn;

    @FXML
    private Button lastBtn;

    @FXML
    private ListView<String> customersList;

    @FXML
    private DatePicker datePicker;
    //endregion

    @Lazy
    @Autowired
    private SceneManager sceneManager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeButtons();
        initializeTable();
    }

    private void initializeTable() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        symbolCol.setCellValueFactory(new PropertyValueFactory<>("symbol"));
        unitCol.setCellValueFactory(new PropertyValueFactory<>("unit"));
        grossCol.setCellValueFactory(new PropertyValueFactory<>("grossPrice"));
        vatRateCol.setCellValueFactory(new PropertyValueFactory<>("vatRate"));
    }

    private void initializeButtons() {
        customersBtn.setOnAction(actionEvent -> sceneManager.switchScene(FxmlView.CUSTOMERS));
        productsBtn.setOnAction(actionEvent -> sceneManager.switchScene(FxmlView.PRODUCTS));
    }
}
