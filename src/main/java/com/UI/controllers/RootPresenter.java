package com.UI.controllers;

import com.UI.view.AbstractFxmlView;
import com.UI.view.ManageCustomersDialogView;
import com.UI.view.ManageServicesDialogView;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
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
    private ManageCustomersDialogView manageCustomersDialog;

    @Autowired
    private ManageServicesDialogView manageServicesDialog;

    @FXML
    public void initialize()
    {
        manageCustomersBtn.setOnAction(e -> showManagementWindow(manageCustomersDialog, manageCustomersBtn.getText()));
        manageServicesBtn.setOnAction(e -> showManagementWindow(manageServicesDialog, manageServicesBtn.getText()));
    }

    private void showManagementWindow(AbstractFxmlView view, String windowTitle)
    {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Platform.runLater(() -> {
                    Stage stage = new Stage();
                    AnchorPane rootLayout = new AnchorPane(view.getView());
                    stage.setTitle(windowTitle);
                    stage.setScene(new Scene(rootLayout));
                    stage.setResizable(false);
                    stage.centerOnScreen();
                    stage.show();
                });
                return null;
            }
        };

        task.run();
    }
}