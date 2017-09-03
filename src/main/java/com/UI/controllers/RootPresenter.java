package com.UI.controllers;

import com.UI.view.*;
import com.utilities.ViewSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
        homeBtn.setOnAction(event -> ViewSwitcher.openAndInitialize(homeBtn, homeView));
        invoicesBtn.setOnAction(event -> ViewSwitcher.openView(invoicesBtn, invoicesView));
        customersBtn.setOnAction(event -> ViewSwitcher.openView(customersBtn, customersView));
        productsBtn.setOnAction(event -> ViewSwitcher.openView(productsBtn, productsView));
        warehouseBtn.setOnAction(event -> ViewSwitcher.openView(warehouseBtn, warehouseView));
    }
}
