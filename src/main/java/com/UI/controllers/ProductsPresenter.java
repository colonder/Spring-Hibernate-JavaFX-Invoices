package com.UI.controllers;

import com.UI.FxmlView;
import com.UI.SceneManager;
import com.entity.Product;
import com.service.IProductService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@Controller
public class ProductsPresenter implements Initializable {

    //region fxmls
    @FXML
    private Button homeBtn;

    @FXML
    private Button customersBtn;

    @FXML
    private Button settingsBtn;

    @FXML
    private Button addBtn;

    @FXML
    private Button editBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private TableView<Product> productsTable;

    @FXML
    private TableColumn<Product, String> nameCol;

    @FXML
    private TableColumn<Product, String> symbolCol;

    @FXML
    private TableColumn<Product, String> unitCol;

    @FXML
    private TableColumn<Product, BigDecimal> grossCol;

    @FXML
    private TableColumn<Product, BigDecimal>vatRateCol;
    //endregion

    @Lazy
    @Autowired
    private SceneManager sceneManager;

    @Autowired
    private IProductService productService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeButtons();
        initializeTable();
        loadProducts();
    }

    private void initializeTable() {
        productsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        symbolCol.setCellValueFactory(new PropertyValueFactory<>("symbol"));
        unitCol.setCellValueFactory(new PropertyValueFactory<>("unit"));
        grossCol.setCellValueFactory(new PropertyValueFactory<>("grossPrice"));
        vatRateCol.setCellValueFactory(new PropertyValueFactory<>("vatRate"));
    }

    private void initializeButtons() {
        homeBtn.setOnAction(actionEvent -> sceneManager.switchScene(FxmlView.HOME));
        customersBtn.setOnAction(actionEvent -> sceneManager.switchScene(FxmlView.CUSTOMERS));
    }

    private void loadProducts() {
        productsTable.getItems().clear();
        productsTable.getItems().addAll(productService.findAll());
    }

    @FXML
    void addProduct(ActionEvent event) {
        sceneManager.switchScene(FxmlView.NEW_PRODUCT);
    }

    @FXML
    void deleteProduct(ActionEvent event) {
        List<Product> users = productsTable.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete selected?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.orElse(null) == ButtonType.OK)
            productService.deleteInBatch(users);

        loadProducts();
    }

    @FXML
    void editProduct(ActionEvent event) {

    }
}
//
//    private void initButtons() {
////        addProductBtn.setOnAction(actionEvent -> SceneManager.openAndInitialize(newProductView, new Product()));
////        editProductBtn.setOnAction(event -> {
////            if (productsTableView.getSelectionModel().getSelectedItems().size() != 0) {
////                if (productsTableView.getSelectionModel().getSelectedItems().size() == 1)
////                    SceneManager.openAndInitialize(newProductView, productsTableView
////                            .getSelectionModel().getSelectedItem());
////                else {
////                    Alert alert = new Alert(Alert.AlertType.WARNING);
////                    alert.setTitle("Editing product");
////                    alert.setHeaderText("An error occurred while editing objects");
////                    alert.setContentText("You can't edit several objects simultaneously");
////                    alert.showAndWait();
////                }
////            }
////        });
//        deleteProductBtn.setOnAction(event -> {
//            if (productsTableView.getSelectionModel().getSelectedItems().size() != 0) {
//                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//                alert.setTitle("Invoices deletion");
//                alert.setHeaderText("Are you sure you want to delete selected customers?");
//
//                Optional<ButtonType> result = alert.showAndWait();
//                result.ifPresent(choice -> {
//                    if (choice.equals(ButtonType.OK)) {
//                        for (Product product : productsTableView.getSelectionModel().getSelectedItems()) {
//                            productService.delete(product);
//                        }
//                        search();
//                    }
//                });
//            }
//        });
//    }
//
//    private void initCheckMenuItems() {
////        nameCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
////                nameCol.setVisible(newValue));
////        netPriceCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
////                netPriceCol.setVisible(newValue));
////        taxRateCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
////                taxRateCol.setVisible(newValue));
////        activeCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
////                activeCol.setVisible(newValue));
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
////        productsTableView.getItems().setAll(productService.findAll(phraseTxtFld.getText()
////                        .isEmpty() ? null : phraseTxtFld.getText(),
////                isActiveMap.get(activeComboBox.getSelectionModel().getSelectedItem())));
//    }
//
//    private void initComboBoxes()
//    {
//        activeComboBox.getItems().setAll(isActiveMap.keySet());
//        activeComboBox.getSelectionModel().selectFirst();
//    }
//}
