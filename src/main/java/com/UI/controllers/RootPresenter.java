package com.UI.controllers;

import com.utilities.dialogs.ManageCustomersDialog;
import com.utilities.dialogs.ManageServicesDialog;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RootPresenter
{
    @FXML private Button settingsBtn;
    @FXML private Button printBtn;
    @FXML private Button saveBtn;
    @FXML private Button manageServicesBtn;
    @FXML private Button manageCustomersBtn;

    @Autowired
    private ManageCustomersDialog manageCustomersDialog;

    @Autowired
    private ManageServicesDialog manageServicesDialog;

    @FXML
    public void initialize()
    {
        manageCustomersBtn.setOnAction(e -> manageCustomersDialog.showDialog());
        manageServicesBtn.setOnAction(e -> manageServicesDialog.showDialog());
    }
}