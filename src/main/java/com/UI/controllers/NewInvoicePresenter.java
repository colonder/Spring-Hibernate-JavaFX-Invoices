package com.UI.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import org.springframework.stereotype.Component;

@Component
public class NewInvoicePresenter {

    @FXML private Button addItemBtn;
    @FXML private Button removeItemBtn;
    @FXML private GridPane itemsGridPane;

    @FXML
    public void initialize()
    {
        addItemBtn.setOnAction(event -> {

        });
    }
}
