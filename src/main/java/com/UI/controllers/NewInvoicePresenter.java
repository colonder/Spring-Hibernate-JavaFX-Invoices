package com.UI.controllers;

import com.entity.BaseAbstractEntity;
import com.entity.Customer;
import com.service.ICustomerService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.event.ActionEvent;
import java.util.Comparator;
import java.util.Currency;
import java.util.Locale;
import java.util.Optional;

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
    @FXML private Label totalNetValLabel;
    @FXML private Label totalTaxValLabel;
    @FXML private Label taxCurrencyLabel;
    @FXML private Label totalGrossValLabel;
    @FXML private Label grossCurrencyLabel;
    @FXML private ComboBox<String> invoiceCurrencyComboBox;
    @FXML private ComboBox<String> paymentMethodComboBox;
    @FXML private ComboBox<Integer> paymentDateComboBox;
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
    @FXML private Button saveBtn;
    //endregion

    @Autowired
    private ICustomerService customerService;

    @FXML
    public void initialize()
    {
        initButtons();
        initComboBoxes();
        initSellerFields();
        initProductsPane();
        initOptions();
    }

    @FXML
    private void removeProduct(ActionEvent event)
    {

    }

    private void initButtons() {
        loadFromDatabaseBtn.setOnAction(event -> openSelectCustomerDialog());
        addItemBtn.setOnAction(event -> addNewEmptyRow());
    }

    private void addNewEmptyRow()
    {
        TextField productName = new TextField();
        TextField amount = new TextField();
        ComboBox<String> unit = new ComboBox<>();
        unit.setPrefWidth(150);
        Label netPrice = new Label();
        ComboBox<String> taxRate = new ComboBox<>();
        taxRate.setPrefWidth(150);
        Label netValue = new Label();
        Label grossValue = new Label();
        GridPane.setMargin(productName, new Insets(0, 0, 0, 0));
        GridPane.setColumnSpan(productName, 4);
        GridPane.setMargin(amount, new Insets(0, 5, 0, 5));
        GridPane.setMargin(unit, new Insets(0, 5, 0, 5));
        GridPane.setMargin(netPrice, new Insets(0, 5, 0, 5));
        GridPane.setMargin(taxRate, new Insets(0, 5, 0, 5));
        GridPane.setMargin(netValue, new Insets(0, 5, 0, 5));
        GridPane.setMargin(grossValue, new Insets(0, 5, 0, 5));
        Button removeItem = new Button();
        removeItem.getStylesheets().add("css/new-invoice-stylesheet.css");
        removeItem.getStyleClass().add("productButton"); // FIXME: button is not loading css

        int lastRow = findLastRowIndex();
        itemsGridPane.add(productName, 0, lastRow);
        itemsGridPane.add(amount, 4, lastRow);
        itemsGridPane.add(unit, 5, lastRow);
        itemsGridPane.add(netPrice, 6, lastRow);
        itemsGridPane.add(taxRate, 7, lastRow);
        itemsGridPane.add(netValue, 8, lastRow);
        itemsGridPane.add(grossValue, 9, lastRow);
        itemsGridPane.add(removeItem, 10, lastRow);
        GridPane.setRowIndex(addItemBtn, lastRow+1);
    }

    private int findLastRowIndex()
    {
        return itemsGridPane.getChildren().stream().mapToInt(n -> {
            Integer row = GridPane.getRowIndex(n);
            Integer rowSpan = GridPane.getRowSpan(n);

            // default values are 0 / 1 respecively
            return (row == null ? 0 : row) + (rowSpan == null ? 0 : rowSpan - 1);
        }).max().orElse(-1);
    }

    private void openSelectCustomerDialog() {
        Dialog<Customer> dialog = new Dialog<>();
        dialog.setTitle("Choose a customer");
        dialog.setHeaderText("Select a customer from the database");
        ButtonType selectBtnType = new ButtonType("Select", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelBtnType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(selectBtnType, cancelBtnType);
        dialog.setGraphic(new ImageView(this.getClass().getResource("/images/icons8-Checked User Male-96.png").toString()));

        // create table view and columns
        TableView<Customer> tableView = new TableView<>();
        TableColumn<Customer, String> aliasCol = new TableColumn<>("Alias");
        TableColumn<Customer, String> lastNameCol = new TableColumn<>("Last name");
        TableColumn<Customer, String> firstName = new TableColumn<>("First name");
        TableColumn<Customer, String> companyName = new TableColumn<>("Company name");
        TableColumn<Customer, String> taxIdentifierCol = new TableColumn<>("Tax identifier number");
        aliasCol.setCellValueFactory(new PropertyValueFactory<>("alias"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        companyName.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        taxIdentifierCol.setCellValueFactory(new PropertyValueFactory<>("taxIdentifier"));
        tableView.getColumns().addAll(aliasCol, lastNameCol, firstName, companyName, taxIdentifierCol);
        tableView.setItems(FXCollections.observableArrayList(customerService.findAll()));
        dialog.getDialogPane().setContent(tableView);
        dialog.getDialogPane().setPrefHeight(Region.USE_COMPUTED_SIZE);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == selectBtnType)
            {
                return tableView.getSelectionModel().getSelectedItem();
            }
            return null;
        });

        Optional<Customer> result = dialog.showAndWait();
        result.ifPresent(this::initBuyerFields);
    }

    private void initComboBoxes() {
        ObservableList<String> typeList = FXCollections.observableArrayList("Ordinary", "Pro forma", "Corrective");
        ObservableList<String> paymentMethodList = FXCollections.observableArrayList("Cash", "Bank transfer",
                "Credit card", "Check", "Cash on delivery", "Paypal");
        ObservableList<String> statusList = FXCollections.observableArrayList("Issued", "Paid",
                "Partially paid", "Rejected", "Unpaid", "Paid after deadline",
                "Unpaid expired");
        ObservableList<String> languages = FXCollections.observableArrayList();
        ObservableList<String> countries = FXCollections.observableArrayList();
        for (Locale locale : Locale.getAvailableLocales())
        {
            if (!locale.getDisplayCountry().isEmpty()) {
                countries.add(locale.getDisplayCountry());
                languages.add(String.format("%s, %s", locale.getDisplayLanguage(), locale.getDisplayCountry()));
            }
        }
        ObservableList<String> currencies = FXCollections.observableArrayList();
        ObservableList<String> currencyCodes = FXCollections.observableArrayList();
        Currency.getAvailableCurrencies().forEach(currency -> {
            currencies.add(String.format("%s, %s", currency.getDisplayName(), currency.getCurrencyCode()));
            currencyCodes.add(currency.getCurrencyCode());
        });

        Comparator<String> comparator = Comparator.naturalOrder();
        languages.sort(comparator);
        countries.sort(comparator);
        currencies.sort(comparator);
        currencyCodes.sort(comparator);
        typeComboBox.setItems(typeList);
        typeComboBox.getSelectionModel().select(0);
        paymentMethodComboBox.setItems(paymentMethodList);
        paymentMethodComboBox.getSelectionModel().select(0);
        statusComboBox.setItems(statusList);
        statusComboBox.getSelectionModel().select(0);
        currencyComboBox.setItems(currencies);
        Locale defaultLocale = Locale.getDefault();
        Currency defaultCurr = Currency.getInstance(defaultLocale);
        currencyComboBox.getSelectionModel().select(String.format("%s, %s",
                defaultCurr.getDisplayName(), defaultCurr.getCurrencyCode()));
        invoiceCurrencyComboBox.setItems(currencyCodes);
        invoiceCurrencyComboBox.getSelectionModel().select(defaultCurr.getCurrencyCode());
        languageComboBox.setItems(languages);
        languageComboBox.getSelectionModel().select(defaultLocale.getDisplayLanguage());
        paymentDateComboBox.setItems(FXCollections.observableArrayList(1, 3, 5, 7, 14, 21, 30, 45, 60, 75, 90));
        paymentDateComboBox.getSelectionModel().select(0);
        calculateValsComboBox.setItems(FXCollections.observableArrayList("Total value",
                "Unit value (consistent with cash register)"));
        calculateValsComboBox.getSelectionModel().select(0);
        calculateTotalComboBox.setItems(FXCollections.observableArrayList(
                "Sum of all values from all records (preserves gross and net value)",
                "Sum of the gross values and calculate net and tax values (preserves gross value, consistent with cash register)",
                "Sum of the net values and calculate gross and tax values (preserves gross value)"));
        calculateTotalComboBox.getSelectionModel().select(0);
        showUnitPriceComboBox.setItems(FXCollections.observableArrayList("Net value",
                "Gross value (consistent with cash register)"));
        showUnitPriceComboBox.getSelectionModel().select(0);
        countryComboBox.setItems(countries);
        countryComboBox.getSelectionModel().select(defaultLocale.getDisplayCountry());
    }

    private void initSellerFields() {

    }

    private void initBuyerFields(Customer customer) {
        buyerTxtFld.setText(String.format("%s %s", customer.getFirstName(), customer.getLastName()));
        buyerTaxIdTxtFld.setText(customer.getTaxIdentifier());
        buyerAddressTxtFld.setText(customer.getAddress());
        buyerPostalCodeTxtFld.setText(customer.getPostalCode());
        buyerCityTxtFld.setText(customer.getCity());
        buyerEmailTxtFld.setText(customer.getEmail());
        buyerPhoneTxtFld.setText(String.valueOf(customer.getCellPhone()));
        countryComboBox.getSelectionModel().select(customer.getCountry());
    }

    private void initProductsPane() {

    }

    private void initOptions() {
    }

    @Override
    public void initializeFields(BaseAbstractEntity entity) {

    }
}