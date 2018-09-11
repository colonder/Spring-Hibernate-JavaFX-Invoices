package com.UI.controllers;

import com.entity.Customer;
import com.entity.Settings;
import com.entity.Templates;
import com.service.ISettingsService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class InvoicePresenter {

    @FXML
    private Label sellerAddress;

    @FXML
    private Label sellerPostalCode;

    @FXML
    private Label sellerTaxID;

    @FXML
    private Label sellerName;

    @FXML
    private Label sellerFirm;

    @FXML
    private Label buyerTaxId;

    @FXML
    private Label buyerAddress;

    @FXML
    private Label buyerPostalCode;

    @FXML
    private Label buyerName;

    @FXML
    private Label buyerFirm;

    @FXML
    private Label paymentMethod;

    @FXML
    private TableView<?> templateTable;

    @FXML
    private TableColumn<?, ?> nameCol;

    @FXML
    private TableColumn<?, ?> symbolCol;

    @FXML
    private TableColumn<?, ?> unitCol;

    @FXML
    private TableColumn<?, ?> netCol;

    @FXML
    private TableColumn<?, ?> quantityCol;

    @FXML
    private TableColumn<?, ?> netTotalCol;

    @FXML
    private TableColumn<?, ?> vatRateCol;

    @FXML
    private TableColumn<?, ?> vatValCol;

    @FXML
    private TableColumn<?, ?> grossCol;

    @FXML
    private Label sellCity;

    @FXML
    private Label sellDate;

    @FXML
    private Label totalNum;

    @FXML
    private Label totalWords;

    @FXML
    private Label bankAccNum;

    @Autowired
    private ISettingsService settingsService;

    public void initData(Customer customer, List<Templates> templates, String total, String words, String date) {
        Settings settings = settingsService.findById(1);
        sellerName.setText(settings.getFirstName() + " " + settings.getLastName());
        sellerFirm.setText(settings.getFirmName());
        sellerAddress.setText(settings.getAddress());
        sellerPostalCode.setText(settings.getPostalCode());
        sellerTaxID.setText(settings.getTaxId());

        if (customer.getPayment().equals("Bank transfer")) {
            bankAccNum.setText(settings.getBankAccNum());
        }
        paymentMethod.setText(customer.getPayment());

        buyerName.setText(customer.getFirstName() + " " + customer.getLastName());
        buyerFirm.setText(customer.getFirmName());
        buyerAddress.setText(customer.getAddress());
        buyerPostalCode.setText(customer.getPostalCode());
        buyerTaxId.setText(customer.getTaxId());

        totalNum.setText(total);
        totalWords.setText(words);
        sellDate.setText(date);
        sellCity.setText("Warszawa, " + date);
    }

}
