package com.UI.controllers;

import com.UI.view.NewProductView;
import com.entity.Product;
import com.service.IProductService;
import com.utilities.ViewSwitcher;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;

@Controller
public class ProductsPresenter {

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
        isActiveMap.put("Inactive", false);
    }

    //region fxml fields
    @FXML private Button addProductBtn;
    @FXML private TextField phraseTxtFld;
    @FXML private ComboBox<String> productTypeComboBox;
    @FXML private TextArea tagTxtArea;
    @FXML private ComboBox<String> activeComboBox;
    @FXML private Button searchBtn;
    @FXML private TableView<Product> productsTableView;
    @FXML private TableColumn<Product, String> nameCol;
    @FXML private TableColumn<Product, BigDecimal> netPriceCol;
    @FXML private TableColumn<Product, BigDecimal> grossPriceCol;
    @FXML private TableColumn<Product, Number> soldCol;
    @FXML private TableColumn<Product, Number> availableCol;
    @FXML private TableColumn<Product, BigDecimal> taxRateCol;
    @FXML private TableColumn<Product, Boolean> onlineSaleCol;
    @FXML private TableColumn<Product, Boolean> activeCol;
    @FXML private TableColumn<Product, Boolean> serviceCol;
    @FXML private TableColumn<Product, String> codeCol;
    @FXML private TableColumn<Product, LocalDate> creationCol;
    @FXML private TableColumn<Product, LocalDate> lastSaleCol;
    @FXML private CheckMenuItem nameCheckMenuItem;
    @FXML private CheckMenuItem netPriceCheckMenuItem;
    @FXML private CheckMenuItem grossPriceCheckMenuItem;
    @FXML private CheckMenuItem onlineSaleCheckMenuItem;
    @FXML private CheckMenuItem soldCheckMenuItem;
    @FXML private CheckMenuItem availableCheckMenuItem;
    @FXML private CheckMenuItem taxRateCheckMenuItem;
    @FXML private CheckMenuItem activeCheckMenuItem;
    @FXML private CheckMenuItem serviceCheckMenuItem;
    @FXML private CheckMenuItem codeCheckMenuItem;
    @FXML private CheckMenuItem creationCheckMenuItem;
    @FXML private CheckMenuItem lastSaleCheckMenuItem;
    @FXML private Button editProductBtn;
    @FXML private Button deleteProductBtn;
    //endregion

    @Autowired private IProductService productService;
    @Autowired private NewProductView newProductView;

    @FXML
    public void initialize()
    {
        initButtons();
        initComboBoxes();
        setSearching();
        initCheckMenuItems();
        initTableColumns();
    }

    private void initTableColumns() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        netPriceCol.setCellValueFactory(new PropertyValueFactory<>("netPrice"));
        grossPriceCol.setCellValueFactory(new PropertyValueFactory<>("grossPrice"));
        soldCol.setCellValueFactory(cell -> {
            if (!cell.getValue().isService())
                return new ReadOnlyIntegerWrapper(cell.getValue().getWarehouse().getSold());
            return null;
        });
        availableCol.setCellValueFactory(cell -> {
            if (!cell.getValue().isService())
                return new ReadOnlyIntegerWrapper(cell.getValue().getWarehouse().getAvailable());
            return null;
        });
        taxRateCol.setCellValueFactory(new PropertyValueFactory<>("vatRate"));
        onlineSaleCol.setCellValueFactory(new PropertyValueFactory<>("onlineSale"));
        activeCol.setCellValueFactory(new PropertyValueFactory<>("isActive"));
        serviceCol.setCellValueFactory(new PropertyValueFactory<>("isService"));
        codeCol.setCellValueFactory(cell -> {
            if (!cell.getValue().isService())
                return new ReadOnlyStringWrapper(cell.getValue().getWarehouse().getProductCode());
            return null;
        });
        creationCol.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        lastSaleCol.setCellValueFactory(cell -> {
            if (!cell.getValue().isService())
                return new ReadOnlyObjectWrapper<>(cell.getValue().getWarehouse().getLastSaleDate());
            return null;
        });
    }

    private void initButtons() {
        addProductBtn.setOnAction(actionEvent -> ViewSwitcher.openAndInitialize(newProductView, new Product()));
        editProductBtn.setOnAction(event -> {
            if (productsTableView.getSelectionModel().getSelectedItems().size() != 0) {
                if (productsTableView.getSelectionModel().getSelectedItems().size() == 1)
                    ViewSwitcher.openAndInitialize(newProductView, productsTableView
                            .getSelectionModel().getSelectedItem());
                else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Editing product");
                    alert.setHeaderText("An error occurred while editing objects");
                    alert.setContentText("You can't edit several objects simultaneously");
                    alert.showAndWait();
                }
            }
        });
        deleteProductBtn.setOnAction(event -> {
            if (productsTableView.getSelectionModel().getSelectedItems().size() != 0) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Invoices deletion");
                alert.setHeaderText("Are you sure you want to delete selected customers?");

                Optional<ButtonType> result = alert.showAndWait();
                result.ifPresent(choice -> {
                    if (choice.equals(ButtonType.OK)) {
                        for (Product product : productsTableView.getSelectionModel().getSelectedItems()) {
                            productService.delete(product);
                        }
                        search();
                    }
                });
            }
        });
    }

    private void initCheckMenuItems() {
        nameCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                nameCol.setVisible(newValue));
        netPriceCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                netPriceCol.setVisible(newValue));
        grossPriceCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                grossPriceCol.setVisible(newValue));
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
