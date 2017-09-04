package com.UI.controllers;

import com.entity.BaseAbstractEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

@Component
public class NewInvoicePresenter implements IInitializableFromEntity {

    //region FXML fields
    @FXML private ComboBox<String> typeComboBox;
    @FXML private TextField numberTxtFld;
    @FXML private DatePicker issueDatePicker;
    @FXML private TextField locationTxtFld;
    @FXML private DatePicker saleDatePicker;
    @FXML private TextField sellerTxtFld;
    @FXML private TextField sellerTaxIdTxtFld;
    @FXML private TextField sellerAddressTxtFld;
    @FXML private TextField sellerPostalCodeTxtFld;
    @FXML private TextField sellerCityTxtFld;
    @FXML private TextField accountTxtFld;
    @FXML private TextField bankTxtFld;
    @FXML private TextField sellerEmailTxtFld;
    @FXML private TextField sellerPhoneTxtFld;
    @FXML private TextField sellerFaxTxtFld;
    @FXML private Button loadFromDatabaseBtn;
    @FXML private TextField buyerTxtFld;
    @FXML private TextField buyerTaxIdTxtFld;
    @FXML private TextField buyerAddressTxtFld;
    @FXML private TextField buyerPostalCodeTxtFld;
    @FXML private TextField buyerCityTxtFld;
    @FXML private TextField buyerEmailTxtFld;
    @FXML private TextField buyerPhoneTxtFld;
    @FXML private ComboBox<String> countryComboBox;
    @FXML private Button addItemBtn;
    @FXML private GridPane itemsGridPane;
    @FXML private ComboBox<String> paymentMethodComboBox;
    @FXML private ComboBox<String> paymentDateComboBox;
    @FXML private ComboBox<String> statusComboBox;
    @FXML private TextField paidAmountTxtFld;
    @FXML private TextField issuerNameTxtFld;
    @FXML private TextField receiverNameTxtFld;
    @FXML private TextArea remarksTextArea;
    @FXML private ComboBox<String> currencyComboBox;
    @FXML private ComboBox<String> languageComboBox;
    @FXML private TitledPane optionsTitledPane;
    @FXML private ComboBox<String> calculateValsComboBox;
    @FXML private ComboBox<String> showUnitPriceComboBox;
    @FXML private TextField paidDateTxtFld;
    @FXML private ComboBox<String> calculateTotalComboBox;
    @FXML private TextField labelTxtFld;
    //endregion

    @FXML
    public void initialize()
    {
        initComboBoxes();
        initSeller();
        initBuyer();
        initProductsPane();
        initOptions();
    }

    private void initComboBoxes() {
        ObservableList<String> typeList = FXCollections.observableArrayList("Ordinary", "Pro forma", "Corrective");
        ObservableList<String> paymentMethodList = FXCollections.observableArrayList("Cash", "Bank transfer",
                "Credit card", "Check", "Cash on delivery", "Paypal");
        ObservableList<String> statusList = FXCollections.observableArrayList("Issued", "Paid",
                "Partially paid", "Rejected", "Unpaid", "Paid after deadline",
                "Unpaid expired");
        ObservableList<String> languages = FXCollections.observableArrayList(Locale.getISOLanguages());
        List<String> currencyOptions = new ArrayList<>(Currency.getAvailableCurrencies().size());
        Currency.getAvailableCurrencies().forEach(currency -> currencyOptions.add(String.format("%s, %s",
                currency.getDisplayName(), currency.getCurrencyCode())));
        ObservableList<String> currencies = FXCollections.observableArrayList(currencyOptions);
        typeComboBox.setItems(typeList);
        typeComboBox.getSelectionModel().select(0);
        paymentMethodComboBox.setItems(paymentMethodList);
        paymentMethodComboBox.getSelectionModel().select(0);
        statusComboBox.setItems(statusList);
        statusComboBox.getSelectionModel().select(0);
        currencyComboBox.setItems(currencies);
        //currencyComboBox.getSelectionModel().select();
        languageComboBox.setItems(languages);
        languageComboBox.getSelectionModel().select("pl");
    }

    private void initSeller() {
    }

    private void initBuyer() {
    }

    private void initProductsPane() {
    }

    private void initOptions() {
    }

    @Override
    public void initializeFields(BaseAbstractEntity entity) {

    }


}