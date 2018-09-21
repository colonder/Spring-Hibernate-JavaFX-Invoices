package com.UI.controllers;

import com.UI.FxmlView;
import com.UI.SceneManager;
import com.entity.Numbering;
import com.entity.Settings;
import com.service.INumberingService;
import com.service.ISettingsService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class SettingsPresenter implements Initializable {

    @FXML
    private TextField nameTxtFld;

    @FXML
    private TextField lastNameTxtFld;

    @FXML
    private TextField idTxtFld;

    @FXML
    private TextField firmNameTxtFld;

    @FXML
    private TextField addressTxtFld;

    @FXML
    private TextField postalTxtFld;

    @FXML
    private TextField cityTxtFld;

    @FXML
    private TextField bankAccTxtFld;

    @FXML
    private TextField currInvoiceNumLbl;

    @Autowired
    private ISettingsService settingsService;

    @Autowired
    private INumberingService numberingService;

    @Autowired
    @Lazy
    private SceneManager sceneManager;

    private Settings settings;
    private Numbering numbering;

    @FXML
    void save() {

        settings.setFirstName(nameTxtFld.getText());
        settings.setLastName(lastNameTxtFld.getText());
        settings.setCity(cityTxtFld.getText());
        settings.setAddress(addressTxtFld.getText());
        settings.setFirmName(firmNameTxtFld.getText());
        settings.setPostalCode(postalTxtFld.getText());
        settings.setTaxId(idTxtFld.getText());
        settings.setBankAccNum(bankAccTxtFld.getText());

        if (numbering.getNumber() != Integer.parseInt(currInvoiceNumLbl.getText())) {
            numbering.setNumber(Integer.parseInt(currInvoiceNumLbl.getText()));
            numberingService.save(numbering);
        }

        settingsService.save(settings);
        sceneManager.switchScene(FxmlView.HOME);
    }

    @FXML
    void cancel() {
        sceneManager.switchScene(FxmlView.HOME);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        settings = settingsService.findById(1);
        numbering = numberingService.findById(1);
        nameTxtFld.setText(settings.getFirstName());
        lastNameTxtFld.setText(settings.getLastName());
        idTxtFld.setText(settings.getTaxId());
        firmNameTxtFld.setText(settings.getFirmName());
        addressTxtFld.setText(settings.getAddress());
        postalTxtFld.setText(settings.getPostalCode());
        cityTxtFld.setText(settings.getCity());
        bankAccTxtFld.setText(settings.getBankAccNum());
        currInvoiceNumLbl.setText(String.valueOf(numbering.getNumber()));
    }
}
