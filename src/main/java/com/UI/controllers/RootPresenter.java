package com.UI.controllers;

import com.UI.view.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RootPresenter {

    @Autowired private RootView rootView;
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
        homeBtn.setOnAction(event ->
        {
            BorderPane pane = (BorderPane) homeBtn.getScene().getRoot();
            pane.setCenter(homeView.getView());
        });

        invoicesBtn.setOnAction(event -> {
            BorderPane pane = (BorderPane) invoicesBtn.getScene().getRoot();
            pane.setCenter(invoicesView.getView());
        });

        customersBtn.setOnAction(event -> {
            BorderPane pane = (BorderPane) customersBtn.getScene().getRoot();
            pane.setCenter(customersView.getView());
        });

        productsBtn.setOnAction(event -> {
            BorderPane pane = (BorderPane) productsBtn.getScene().getRoot();
            pane.setCenter(productsView.getView());
        });

        warehouseBtn.setOnAction(event -> {
            BorderPane pane = (BorderPane) warehouseBtn.getScene().getRoot();
            pane.setCenter(warehouseView.getView());
        });
    }
}
