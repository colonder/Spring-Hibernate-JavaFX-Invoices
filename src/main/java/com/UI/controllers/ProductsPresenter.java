package com.UI.controllers;

import com.UI.FxmlView;
import com.entity.Product;
import com.service.IProductService;
import com.UI.SceneManager;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
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
    private TableView<?> productsTable;

    @FXML
    private TableColumn<?, ?> nameCol;

    @FXML
    private TableColumn<?, ?> symbolCol;

    @FXML
    private TableColumn<?, ?> unitCol;

    @FXML
    private TableColumn<?, ?> grossCol;

    @FXML
    private TableColumn<?, ?> vatRateCol;
    //endregion

    @Lazy
    @Autowired
    private SceneManager sceneManager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeButtons();
    }

    private void initializeButtons() {
        homeBtn.setOnAction(actionEvent -> sceneManager.switchScene(FxmlView.HOME));
        customersBtn.setOnAction(actionEvent -> sceneManager.switchScene(FxmlView.CUSTOMERS));
    }
}

//    @FXML
//    public void initialize()
//    {
//        initButtons();
//        initComboBoxes();
//        setSearching();
//        initCheckMenuItems();
//        initTableColumns();
//    }
//
//    private void initTableColumns() {
//        nameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
//        netPriceCol.setCellValueFactory(new PropertyValueFactory<>("netPrice"));
//        taxRateCol.setCellValueFactory(new PropertyValueFactory<>("vatRate"));
//        activeCol.setCellValueFactory(new PropertyValueFactory<>("isActive"));
//    }
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
