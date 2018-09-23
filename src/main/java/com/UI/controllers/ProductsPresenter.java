package com.UI.controllers;

import com.UI.FxmlView;
import com.UI.SceneManager;
import com.entity.Product;
import com.entity.Templates;
import com.service.IProductService;
import com.service.ITemplatesService;
import com.utilities.Miscellaneous;
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
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@Controller
public class ProductsPresenter implements Initializable {

    //region fxmls
    @FXML
    private TableView<Product> productsTable;

    @FXML
    private TableColumn<Product, String> nameCol;

    @FXML
    private TableColumn<Product, String> symbolCol;

    @FXML
    private TableColumn<Product, String> unitCol;

    @FXML
    private TableColumn<Product, BigDecimal> unitNetPrice;

    @FXML
    private TableColumn<Product, BigDecimal>vatRateCol;

    @FXML
    private TableColumn<Product, Boolean> perMonthCol;
    //endregion

    @Lazy
    @Autowired
    private SceneManager sceneManager;

    @Autowired
    private IProductService productService;

    @Autowired
    private ITemplatesService templatesService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTable();
        loadProducts();
    }

    private void initializeTable() {
        productsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        symbolCol.setCellValueFactory(new PropertyValueFactory<>("symbol"));
        unitCol.setCellValueFactory(new PropertyValueFactory<>("unit"));
        unitNetPrice.setCellValueFactory(new PropertyValueFactory<>("unitNetPrice"));
        unitNetPrice.setCellFactory(Miscellaneous.getCellFactory());
        vatRateCol.setCellValueFactory(new PropertyValueFactory<>("vatRate"));
        vatRateCol.setCellFactory(Miscellaneous.getCellFactory());
        perMonthCol.setCellValueFactory(new PropertyValueFactory<>("perMonth"));
    }

    private void loadProducts() {
        productsTable.getItems().clear();
        productsTable.getItems().addAll(productService.findAll());
    }

    @FXML
    void switchToCustomers() {
        sceneManager.switchScene(FxmlView.CUSTOMERS);
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
    void addProduct() {
        sceneManager.switchScene(FxmlView.NEW_PRODUCT);
    }

    @FXML
    void deleteProduct() {
        List<Product> products = productsTable.getSelectionModel().getSelectedItems();

        if (!products.isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "",
                    Miscellaneous.ok,
                    Miscellaneous.cancel);
            alert.setTitle("Potwierdzenie");
            List<Templates> templates = templatesService.findByProductIn(products);

            if (!templates.isEmpty()) {
                alert.setContentText("Ten produkt znajduje się w kilku szablonach. " +
                        "Usunięcie produktu spowoduje usunięcie ich również z szablonów. " +
                        "Czy na pewno usunąć wybrane produkty?");
                Optional<ButtonType> action = alert.showAndWait();

                if (action.orElse(Miscellaneous.cancel) == Miscellaneous.ok) {
                    templatesService.deleteInBatch(templates);
                    productService.deleteInBatch(products);
                }
            } else {
                alert.setContentText("Czy na pewno usunąć wybrane produkty?");
                Optional<ButtonType> action = alert.showAndWait();

                if (action.orElse(Miscellaneous.cancel) == Miscellaneous.ok)
                    productService.deleteInBatch(products);
            }

            loadProducts();
        } else {
            actionAlert();
        }
    }

    @FXML
    void editProduct() {
        Parent rootNode = null;

        FXMLLoader loader = sceneManager.getLoader(FxmlView.NEW_PRODUCT);
        try {
            rootNode = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        NewProductPresenter presenter = loader.getController();
        try {
            presenter.initData(productsTable.getSelectionModel().getSelectedItem());
            sceneManager.show(rootNode, FxmlView.NEW_PRODUCT.getTitle());
        } catch (NullPointerException e) {
            actionAlert();
        }
    }

    private void actionAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Edycja produktu");
        alert.setContentText("Musisz wybrać produkt by móc edytować");
        alert.showAndWait();
    }
}