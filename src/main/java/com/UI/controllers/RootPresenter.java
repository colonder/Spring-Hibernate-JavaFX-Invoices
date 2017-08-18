package com.UI.controllers;

import com.UI.view.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RootPresenter {

    @Autowired private HomeView homeView;
    @Autowired private InvoicesView invoicesView;
    @Autowired private CustomersView customersView;
    @Autowired private ProductsView productsView;
    @Autowired private WarehouseView warehouseView;

    @FXML private Button homeBtn;
    @FXML private Button invoicesBtn;
    @FXML private Button customersBtn;
    @FXML private Button productsBtn;
    @FXML private Button warehouseBtn;

    @FXML
    public void initialize()
    {
        homeBtn.setOnAction(event -> openView(homeBtn, homeView));
        invoicesBtn.setOnAction(event -> openView(invoicesBtn, invoicesView));
        customersBtn.setOnAction(event -> openView(customersBtn, customersView));
        productsBtn.setOnAction(event -> openView(productsBtn, productsView));
        warehouseBtn.setOnAction(event -> openView(warehouseBtn, warehouseView));
    }

    private void openView(Button button, AbstractFxmlView view)
    {
        BorderPane parent = (BorderPane) button.getScene().getRoot();
        parent.setCenter(view.getView());
    }
}
