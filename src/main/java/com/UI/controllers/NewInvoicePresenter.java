package com.UI.controllers;

import com.entity.BaseAbstractEntity;
import com.entity.BoughtProducts;
import com.entity.Customer;
import com.entity.Product;
import com.service.ICustomerService;
import com.service.IProductService;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.util.converter.IntegerStringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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
    @FXML private Button buyerFromDatabaseBtn;
    @FXML private TextField buyerTxtFld;
    @FXML private TextField buyerTaxIdTxtFld;
    @FXML private TextField buyerAddressTxtFld;
    @FXML private TextField buyerPostalCodeTxtFld;
    @FXML private TextField buyerCityTxtFld;
    @FXML private TextField buyerEmailTxtFld;
    @FXML private TextField buyerPhoneTxtFld;
    @FXML private ComboBox<String> countryComboBox;
    @FXML private CheckBox discountChckBox;
    @FXML private Button addItemBtn;
    @FXML private Label totalNetValLabel;
    @FXML private TableView<BoughtProducts> productTableView;
    @FXML private TableColumn<BoughtProducts, String> nameCol;
    @FXML private TableColumn<BoughtProducts, String> symbolCol;
    @FXML private TableColumn<BoughtProducts, Integer> quantityCol;
    @FXML private TableColumn<BoughtProducts, String> unitCol;
    @FXML private TableColumn<BoughtProducts, BigDecimal> netPriceCol;
    @FXML private TableColumn<BoughtProducts, BigDecimal> taxRateCol;
    @FXML private TableColumn<BoughtProducts, BigDecimal> netValCol;
    @FXML private TableColumn<BoughtProducts, Integer> discountCol;
    @FXML private TableColumn<BoughtProducts, BigDecimal> grossValCol;
    @FXML private TableColumn<BoughtProducts, BoughtProducts> removeCol;
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

    @Autowired private ICustomerService customerService;
    @Autowired private IProductService productService;
    private ObservableList<BoughtProducts> productsList;

    @FXML
    public void initialize()
    {
        initButtons();
        initComboBoxes();
        initSellerFields();
        initProductsTable();
        initValueLabels();
        initOptions();
    }

    private void initButtons() {
        buyerFromDatabaseBtn.setOnAction(event -> openSelectCustomerDialog());
        addItemBtn.setOnAction(event -> openSelectProductDialog());
    }

    private void openSelectProductDialog() {
        Dialog<Product> dialog = new Dialog<>();
        dialog.setTitle("Choose a product");
        dialog.setHeaderText("Select a product from the database");
        ButtonType selectBtnType = new ButtonType("Select", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelBtnType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(selectBtnType, cancelBtnType);
        dialog.setGraphic(new ImageView(this.getClass().getResource("/images/icons8-Product-96.png")
                .toString()));

        // create table view and columns
        TableView<Product> tableView = new TableView<>();
        TableColumn<Product, String> nameCol = new TableColumn<>("Product name");
        TableColumn<Product, String> symbolCol = new TableColumn<>("Symbol");
        TableColumn<Product, String> unitCol = new TableColumn<>("Unit");
        TableColumn<Product, BigDecimal> cpuCol = new TableColumn<>("CPU");
        TableColumn<Product, BigDecimal> vatRateCol = new TableColumn<>("VAT rate");
        TableColumn<Product, Boolean> onlineCol = new TableColumn<>("Online sale");
        TableColumn<Product, Boolean> serviceCol = new TableColumn<>("Is service");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        symbolCol.setCellValueFactory(new PropertyValueFactory<>("symbol"));
        unitCol.setCellValueFactory(new PropertyValueFactory<>("unit"));
        cpuCol.setCellValueFactory(new PropertyValueFactory<>("netPrice"));
        vatRateCol.setCellValueFactory(new PropertyValueFactory<>("taxRate"));
        onlineCol.setCellValueFactory(new PropertyValueFactory<>("onlineSale"));
        serviceCol.setCellValueFactory(new PropertyValueFactory<>("isService"));
        tableView.getColumns().addAll(nameCol, symbolCol, unitCol, cpuCol, vatRateCol, onlineCol, serviceCol);
        tableView.setItems(FXCollections.observableArrayList(productService.findAllByIsActiveTrue()));
        dialog.getDialogPane().setContent(tableView);
        dialog.getDialogPane().setPrefHeight(Region.USE_COMPUTED_SIZE);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == selectBtnType)
            {
                return tableView.getSelectionModel().getSelectedItem();
            }
            return null;
        });

        Optional<Product> result = dialog.showAndWait();
        result.ifPresent(product -> {

                BoughtProducts boughtProduct = new BoughtProducts(product.getProductName(), product.getSymbol(),
                        product.getUnit(), product.getNetPrice(), product.getTaxRate(), 0);

                if (productsList.contains(boughtProduct))
                {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error in adding product");
                    alert.setHeaderText("Selected product is already on the list");
                    alert.setContentText("It seems that selected product is already on the products lit. Please, " +
                            "modify existing one instead of adding a new one.");

                    alert.showAndWait();
                    return;
                }

                productsList.add(boughtProduct);
        });
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
        // TODO: load from settings table in the database
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

    private void initProductsTable() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        symbolCol.setCellValueFactory(new PropertyValueFactory<>("symbol"));
        unitCol.setCellValueFactory(new PropertyValueFactory<>("unit"));
        netPriceCol.setCellValueFactory(new PropertyValueFactory<>("priceProp"));
        taxRateCol.setCellValueFactory(new PropertyValueFactory<>("taxRateProp"));
        removeCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        removeCol.setCellFactory(param -> new TableCell<BoughtProducts, BoughtProducts>(){
            private Button removeButton = new Button("", new ImageView(this.getClass()
                    .getResource("/images/icons8-Minus-24.png").toString()));

            @Override
            protected void updateItem(BoughtProducts product, boolean empty)
            {
                super.updateItem(product, empty);

                if (product == null)
                {
                    setGraphic(null);
                    return;
                }

                setGraphic(removeButton);
                removeButton.setOnAction(event -> productsList.remove(product));
                removeButton.getStylesheets().add("com/UI/view/css/new-invoice-stylesheet.css");
                removeButton.getStyleClass().add("removeProductButton");
            }
        });

        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantityProp"));
        quantityCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        quantityCol.setOnEditCommit(event -> {

            // TODO: check if typed quantity does not exceed quantity available at the warehouse

            event.getTableView().getItems().get(event.getTablePosition().getRow())
                    .setQuantityProp(event.getNewValue());

        });
        netValCol.setCellValueFactory(new PropertyValueFactory<>("netValProp"));
        discountCol.setCellValueFactory(new PropertyValueFactory<>("discountProp"));
        discountCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        discountCol.setOnEditCommit(event -> {

            // TODO: check if typed quantity does not exceed quantity available at the warehouse

            event.getTableView().getItems().get(event.getTablePosition().getRow())
                    .setDiscountProp(event.getNewValue());
        });
        grossValCol.setCellValueFactory(new PropertyValueFactory<>("grossValProp"));

        productTableView.setItems(productsList);
        discountChckBox.selectedProperty().addListener((observable, oldValue, newValue) ->
                discountCol.setVisible(newValue));
    }

    private void initValueLabels() {

    }

    private void initOptions() {
    }

    @Override
    public void initializeFields(BaseAbstractEntity entity) {
        // TODO: initialize fields with data of a buyer
        // TODO: initialize products table of a particular invoice
    }
}