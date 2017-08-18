package com.UI.controllers;

import com.UI.view.HomeView;
import com.UI.view.InvoicesView;
import com.UI.view.RootView;
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

    @FXML private Button homeBtn;
    @FXML private Button invoicesBtn;
    @FXML private Button customersBtn;
    @FXML private Button productsBtn;
    @FXML private Button warehouseBtn;

    @FXML
    public void initialize()
    {
        homeBtn.setOnAction(event -> {
            BorderPane parent = (BorderPane) homeBtn.getParent();
            parent.setCenter(homeView.getView());
        });

        invoicesBtn.setOnAction(event -> {
            BorderPane parent = (BorderPane) invoicesBtn.getParent();
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
