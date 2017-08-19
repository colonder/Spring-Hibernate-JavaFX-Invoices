package com.UI.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

@Component
public class InvoicesPresenter {

    @FXML private Button newInvoiceBtn;
    @FXML private TextField phraseTxtFld;
    @FXML private ComboBox<String> invoiceTypeComboBox;
    @FXML private ComboBox<String> periodComboBox;
    @FXML private ComboBox<String> statusComboBox;
    @FXML private ComboBox<String> paymentComboBox;
    @FXML private Button searchBtn;

    @FXML
    public void initialize()
    {
        initializeComboBoxes();
    }

    private void initializeComboBoxes()
    {
        ObservableList<String> type = FXCollections.observableArrayList();
        ObservableList<String> period = FXCollections.observableArrayList();
        ObservableList<String> status = FXCollections.observableArrayList();
        ObservableList<String> payment = FXCollections.observableArrayList();

        type.addAll("All", "Ordinary", "Pro forma", "Corrective");
        period.addAll("Last 12 months", "Current month", "Last month", "Current year", "Last year", "All");
        status.addAll("All", "Issued", "Paid", "Partially paid", "Rejected", "Unpaid", "Paid after deadline",
                "Unpaid expired");
        payment.addAll("All", "Cash", "Bank transfer", "Credit card", "Check", "Cash on delivery", "Paypal");

        invoiceTypeComboBox.setItems(type);
        invoiceTypeComboBox.getSelectionModel().select(0);
        periodComboBox.setItems(period);
        periodComboBox.getSelectionModel().select(0);
        statusComboBox.setItems(status);
        statusComboBox.getSelectionModel().select(0);
        paymentComboBox.setItems(payment);
        paymentComboBox.getSelectionModel().select(0);
    }
}
