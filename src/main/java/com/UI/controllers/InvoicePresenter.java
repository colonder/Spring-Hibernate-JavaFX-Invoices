package com.UI.controllers;

import com.entity.Customer;
import com.entity.Settings;
import com.entity.Templates;
import com.service.ISettingsService;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;

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
    private Label bankLbl;

    @FXML
    private Label paymentRequireLbl;

    @FXML
    private TableView<Templates> templateTable;

    @FXML
    private TableColumn<Templates, String> nameCol;

    @FXML
    private TableColumn<Templates, String> symbolCol;

    @FXML
    private TableColumn<Templates, BigDecimal> quantityCol;

    @FXML
    private TableColumn<Templates, String> unitCol;

    @FXML
    private TableColumn<Templates, BigDecimal> netCol;

    @FXML
    private TableColumn<Templates, BigDecimal> vatRateCol;

    @FXML
    private TableColumn<Templates, BigDecimal> vatValCol;

    @FXML
    private TableColumn<Templates, BigDecimal> grossCol;

    @FXML
    private TableColumn<Templates, BigDecimal> netTotalCol;

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

    @FXML
    private TableView<BigDecimal> taxTable;

    @Autowired
    private ISettingsService settingsService;

    public void initData(Customer customer, ObservableList<Templates> templates, String total,
                         String words, String date) {
        Settings settings = settingsService.findById(1);
        sellerName.setText(settings.getFirstName() + " " + settings.getLastName());
        sellerFirm.setText(settings.getFirmName());
        sellerAddress.setText(settings.getAddress());
        sellerPostalCode.setText(settings.getPostalCode());
        sellerTaxID.setText(settings.getTaxId());

        if (customer.getPayment().equals("Bank transfer")) {
            bankAccNum.setText(settings.getBankAccNum());
            bankLbl.setVisible(true);
            paymentRequireLbl.setVisible(true);
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

        templateTable.setItems(templates);

        nameCol.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getProduct().getProductName()));
        symbolCol.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getProduct().getSymbol()));
        unitCol.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getProduct().getUnit()));
        netCol.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getProduct().getUnitNetPrice()));
        vatRateCol.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getProduct().getVatRate()));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantityProp"));
        vatValCol.setCellValueFactory(new PropertyValueFactory<>("taxValProp"));
        grossCol.setCellValueFactory(new PropertyValueFactory<>("grossValProp"));
        netTotalCol.setCellValueFactory(new PropertyValueFactory<>("netValProp"));

        BigDecimal vat8Tax = BigDecimal.ZERO;
        BigDecimal vat8Net = BigDecimal.ZERO;
        BigDecimal vat8Gross = BigDecimal.ZERO;
        BigDecimal vat23Tax = BigDecimal.ZERO;
        BigDecimal vat23Net = BigDecimal.ZERO;
        BigDecimal vat23Gross = BigDecimal.ZERO;

        for (Templates t : templates) {

            if (t.getProduct().getVatRate().compareTo(new BigDecimal(8)) == 0) {
                vat8Tax = vat8Tax.add(t.getTaxValProp());
                vat8Net = vat8Net.add(t.getNetValProp());
                vat8Gross = vat8Gross.add(t.getGrossValProp());
            } else {
                vat23Tax = vat23Tax.add(t.getTaxValProp());
                vat23Net = vat23Net.add(t.getNetValProp());
                vat23Gross = vat23Gross.add(t.getGrossValProp());
            }
        }
    }
}
