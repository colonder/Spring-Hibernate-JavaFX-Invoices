package com.UI.controllers;

import com.UI.FxmlView;
import com.UI.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class HomePresenter implements Initializable {

    //region fxmls
    @FXML
    private Button customersBtn;

    @FXML
    private Button productsBtn;

    @FXML
    private Button printBtn;

    @FXML
    private Button settingsBtn;

    @FXML
    private Label customerLbl;

    @FXML
    private Button addProductBtn;

    @FXML
    private TableColumn<?, ?> nameCol;

    @FXML
    private TableColumn<?, ?> symbolCol;

    @FXML
    private TableColumn<?, ?> amountCol;

    @FXML
    private TableColumn<?, ?> unitCol;

    @FXML
    private TableColumn<?, ?> netCol;

    @FXML
    private TableColumn<?, ?> vatRateCol;

    @FXML
    private TableColumn<?, ?> vatValCol;

    @FXML
    private TableColumn<?, ?> grossCol;

    @FXML
    private TableColumn<?, ?> deleteCol;

    @FXML
    private Label totalLbl;

    @FXML
    private Label wordsLbl;

    @FXML
    private Button firstBtn;

    @FXML
    private Button prevBtn;

    @FXML
    private Button nextBtn;

    @FXML
    private Button lastBtn;

    @FXML
    private ListView<?> customersList;
    //endregion

    @Lazy
    @Autowired
    private SceneManager sceneManager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeButtons();
    }

    private void initializeButtons() {
        customersBtn.setOnAction(actionEvent -> sceneManager.switchScene(FxmlView.CUSTOMERS));
        productsBtn.setOnAction(actionEvent -> sceneManager.switchScene(FxmlView.PRODUCTS));
    }
}
