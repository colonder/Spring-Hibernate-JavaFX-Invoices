package com.UI.controllers;

import com.entity.Product;
import com.service.IProductService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

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
    private static final HashMap<String, Boolean> isServiceMap;
    private static final HashMap<String, Boolean> isActiveMap;

    static
    {
        isServiceMap = new HashMap<>();
        isActiveMap = new HashMap<>();
        isServiceMap.put("All", null);
        isServiceMap.put("Only services", true);
        isServiceMap.put("Only products", false);
        isActiveMap.put("All", null);
        isActiveMap.put("Active", true);
        isActiveMap.put("Non active", false);
    }

    @FXML private TableColumn<Product, Boolean> onlineSaleCol;
    @FXML private TableColumn<Product, Boolean> activeCol;
    @FXML private TableColumn<Product, Boolean> serviceCol;
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
    @FXML private TableColumn<Product, String> codeCol;
    @FXML private TableColumn<Product, LocalDate> creationCol;
    @FXML private TableColumn<Product, LocalDate> lastSaleCol;

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
        nameCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                nameCol.setVisible(newValue));
        netPriceCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                netPriceCol.setVisible(newValue));
        onlineSaleCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                onlineSaleCol.setVisible(newValue));
        soldCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                soldCol.setVisible(newValue));
        availableCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                availableCol.setVisible(newValue));
        taxRateCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                taxRateCol.setVisible(newValue));
        activeCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                activeCol.setVisible(newValue));
        serviceCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                serviceCol.setVisible(newValue));
        codeCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                codeCol.setVisible(newValue));
        creationCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                creationCol.setVisible(newValue));
        lastSaleCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                lastSaleCol.setVisible(newValue));
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
        productsTableView.getItems().setAll(productService.findAll(phraseTxtFld.getText()
                        .isEmpty() ? null : phraseTxtFld.getText(),
                isServiceMap.get(productTypeComboBox.getSelectionModel().getSelectedItem()),
                tagTxtArea.getText().isEmpty() ? null : tagTxtArea.getText().split(","),
                isActiveMap.get(activeComboBox.getSelectionModel().getSelectedItem())));
    }

    private void initComboBoxes()
    {
        productTypeComboBox.getItems().setAll(isServiceMap.keySet());
        productTypeComboBox.getSelectionModel().selectFirst();
        activeComboBox.getItems().setAll(isActiveMap.keySet());
        activeComboBox.getSelectionModel().selectFirst();
    }
}
