package com.UI.controllers;

import com.entity.BaseAbstractEntity;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import org.springframework.stereotype.Component;

@Component
public class NewInvoicePresenter implements IInitializableFromEntity {

    @FXML private Button addItemBtn;
    @FXML private Button removeItem0Btn;
    @FXML private GridPane itemsGridPane;

    @FXML
    public void initialize()
    {
        addItemBtn.setOnAction(event -> {

        });
    }

    @Override
    public void initializeFields(BaseAbstractEntity entity) {

    }
}