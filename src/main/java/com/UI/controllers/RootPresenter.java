package com.UI.controllers;

import com.UI.view.HomeView;
import com.UI.view.InvoicesView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RootPresenter {

    @Autowired private HomeView homeView;
    @Autowired private InvoicesView invoicesView;

    @FXML Button homeBtn;
    @FXML Button invoicesBtn;
    @FXML Button customersBtn;
    @FXML Button productsBtn;
    @FXML Button warehouseBtn;

    @FXML
    public void initialize()
    {
        homeBtn.setOnAction(event -> {
            BorderPane parent = (BorderPane) homeBtn.getScene().getRoot();
            parent.setCenter(homeView.getView());
        });

        invoicesBtn.setOnAction(event -> {
            BorderPane parent = (BorderPane) invoicesBtn.getScene().getRoot();
            parent.setCenter(invoicesView.getView());
        });

        customersBtn.setOnAction(event -> {

        });

        productsBtn.setOnAction(event -> {

        });

        warehouseBtn.setOnAction(event -> {

        });
    }
}
