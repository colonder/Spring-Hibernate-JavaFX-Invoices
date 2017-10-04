package com.UI.controllers;

import com.entity.Product;
import com.service.IProductService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;

@Controller
public class ProductsPresenter {

    @FXML private Button addProductBtn;
    @FXML private TextField phraseTxtFld;
    @FXML private ComboBox<String> productTypeComboBox;
    @FXML private TextArea tagTxtArea;
    @FXML private ComboBox<String> filterComboBox;
    @FXML private ComboBox<String> activeComboBox;
    @FXML private Button searchBtn;
    @FXML private TableView<Product> productsTableView;
    @FXML private TableColumn<Product, String> nameCol;
    @FXML private TableColumn<Product, BigDecimal> netPriceCol;
    @FXML private TableColumn<Product, Integer> soldCol;
    @FXML private TableColumn<Product, Integer> availableCol;
    @FXML private TableColumn<Product, BigDecimal> taxRateCol;
    @FXML private CheckMenuItem nameCheckMenuItem;
    @FXML private CheckMenuItem netPriceCheckMenuItem;
    @FXML private CheckMenuItem onlineSaleCheckMenuItem;
    @FXML private CheckMenuItem soldCheckMenuItem;
    @FXML private CheckMenuItem availableCheckMenuItem;
    @FXML private CheckMenuItem taxRateCheckMenuItem;
    @FXML private CheckMenuItem activeCheckMenuItem;
    @FXML private CheckMenuItem serviceCheckMenuItem;
    @FXML private CheckMenuItem codeCheckMenuItem;
    @FXML private CheckMenuItem creationCheckMenuItem;
    @FXML private CheckMenuItem lastSaleCheckMenuItem;

    @Autowired
    private IProductService productService;
    // TODO: add new customer create view

    @FXML
    public void initialize()
    {
        initButtons();
        initComboBoxes();
        setSearching();
        initCheckMenuItems();
    }

    private void initButtons() {
        //addCustomerBtn.setOnAction(event -> ViewSwitcher.openView());
    }

    private void initCheckMenuItems() {
        aliasCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                aliasCol.setVisible(newValue));
        lastNameCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                lastNameCol.setVisible(newValue));
        firstNameCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                firstNameCol.setVisible(newValue));
        personalIdCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                personalIdCol.setVisible(newValue));
        taxIdCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                taxIdCol.setVisible(newValue));
        emailCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                emailCol.setVisible(newValue));
        addressCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                addressCol.setVisible(newValue));
        postalCodeCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                postalCodeCol.setVisible(newValue));
        cityCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                cityCol.setVisible(newValue));
        telephoneCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                telephoneCol.setVisible(newValue));
        cellphoneCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                cellphoneCol.setVisible(newValue));
        faxCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                faxCol.setVisible(newValue));
        companyNumCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                companyNumCol.setVisible(newValue));
        paymentCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                paymentCol.setVisible(newValue));
        creationDateCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                creationDateCol.setVisible(newValue));
        lastPurchaseCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                purchaseCol.setVisible(newValue));
        countryCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                countryCol.setVisible(newValue));
        currencyCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                currencyCol.setVisible(newValue));
        discountCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                discountCol.setVisible(newValue));
        paymentDateCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                paymentDateCol.setVisible(newValue));
        tagCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                tagCol.setVisible(newValue));
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
        productsTableView.getItems().setAll(productService.findAll(CustomerType.customerMap.get(customerTypeComboBox
                .getSelectionModel().getSelectedItem()), tagTxtArea.getText().isEmpty() ? null : tagTxtArea.getText()
                .split(",")));
    }

    private void initComboBoxes()
    {
        //customerTypeComboBox.getItems().setAll(CustomerType.customerMap.keySet());
        //customerTypeComboBox.getSelectionModel().selectFirst();
        // TODO: initialize filter variant combo box
    }
}
