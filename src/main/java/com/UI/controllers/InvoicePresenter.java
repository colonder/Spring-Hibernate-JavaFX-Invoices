package com.UI.controllers;

import com.entity.Customer;
import com.entity.Settings;
import com.entity.Templates;
import com.service.ISettingsService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.time.LocalDate;

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
    private GridPane productsGrid;

    @FXML
    private Label sellCity;

    @FXML
    private Label sellDate;

    @FXML
    private Label totalNum;

    @FXML
    private Label totalWords;

    @FXML
    private Label net8Val;

    @FXML
    private Label tax8Val;

    @FXML
    private Label gross8Val;

    @FXML
    private Label net23Val;

    @FXML
    private Label tax23Val;

    @FXML
    private Label gross23Val;

    @FXML
    private Label netTotVal;

    @FXML
    private Label taxTotVal;

    @FXML
    private Label grossTotVal;

    @FXML
    private Label bankAccNum;

    @Autowired
    private ISettingsService settingsService;

    public void initData(Customer customer, ObservableList<Templates> templates, String total,
                         String words, LocalDate date) {
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
        sellDate.setText(date.toString());
        sellCity.setText("Warszawa, " + date.toString());

        BigDecimal totTax = BigDecimal.ZERO;
        BigDecimal totNet = BigDecimal.ZERO;
        BigDecimal totGross = BigDecimal.ZERO;

        BigDecimal vat8Tax = BigDecimal.ZERO;
        BigDecimal vat8Net = BigDecimal.ZERO;
        BigDecimal vat8Gross = BigDecimal.ZERO;

        BigDecimal vat23Tax = BigDecimal.ZERO;
        BigDecimal vat23Net = BigDecimal.ZERO;
        BigDecimal vat23Gross = BigDecimal.ZERO;

        for (int i = 0; i < templates.size(); i++) {

            addRow(i + 1, templates.get(i), date);
            totNet = totNet.add(templates.get(i).getNetValProp());
            totTax = totTax.add(templates.get(i).getTaxValProp());
            totGross = totGross.add(templates.get(i).getGrossValProp());

            if (templates.get(i).getProduct().getVatRate().compareTo(new BigDecimal(8)) == 0) {
                vat8Tax = vat8Tax.add(templates.get(i).getTaxValProp());
                vat8Net = vat8Net.add(templates.get(i).getNetValProp());
                vat8Gross = vat8Gross.add(templates.get(i).getGrossValProp());
            } else {
                vat23Tax = vat23Tax.add(templates.get(i).getTaxValProp());
                vat23Net = vat23Net.add(templates.get(i).getNetValProp());
                vat23Gross = vat23Gross.add(templates.get(i).getGrossValProp());
            }
        }

        netTotVal.setText(formatNumber(totNet));
        taxTotVal.setText(formatNumber(totTax));
        grossTotVal.setText(formatNumber(totGross));

        net8Val.setText(formatNumber(vat8Net));
        tax8Val.setText(formatNumber(vat8Tax));
        gross8Val.setText(formatNumber(vat8Gross));

        net23Val.setText(formatNumber(vat23Net));
        tax23Val.setText(formatNumber(vat23Tax));
        gross23Val.setText(formatNumber(vat23Gross));
    }

    private void addRow(int i, Templates template, LocalDate date) {

        Text name = new Text();
        name.setWrappingWidth(230);
        name.setText(template.getProduct().getPerMonth() ?
                template.getProduct().getProductName() + " za m-c "
                        + date.getMonth().getValue() + "/" + date.getYear()
                : template.getProduct().getProductName());

        productsGrid.addRow(i,
                name,
                new Label(template.getProduct().getSymbol()),
                new Label(template.getProduct().getUnit()),
                new Label(formatNumber(template.getProduct().getUnitNetPrice())),
                new Label(formatNumber(template.getQuantity())),
                new Label(formatNumber(template.getNetValProp())),
                new Label(formatNumber(template.getProduct().getVatRate())),
                new Label(formatNumber(template.getTaxValProp())),
                new Label(formatNumber(template.getGrossValProp()))
        );
    }

    private String formatNumber(BigDecimal val) {
        return String.format("%,.2f", val);
    }
}
