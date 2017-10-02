package com.UI.controllers;

import com.entity.*;
import com.enums.InvoiceStatus;
import com.enums.InvoiceType;
import com.enums.PaymentMethod;
import com.service.ICustomerService;
import com.service.IInvoiceService;
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
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Currency;
import java.util.Locale;
import java.util.Optional;

@Controller
public class NewInvoicePresenter implements IInitializableFromEntity<Invoice> {

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
    @FXML private TableView<BoughtProduct> productTableView;
    @FXML private TableColumn<BoughtProduct, String> nameCol;
    @FXML private TableColumn<BoughtProduct, String> symbolCol;
    @FXML private TableColumn<BoughtProduct, Integer> quantityCol;
    @FXML private TableColumn<BoughtProduct, String> unitCol;
    @FXML private TableColumn<BoughtProduct, BigDecimal> netPriceCol;
    @FXML private TableColumn<BoughtProduct, BigDecimal> taxRateCol;
    @FXML private TableColumn<BoughtProduct, BigDecimal> netValCol;
    @FXML private TableColumn<BoughtProduct, Integer> discountCol;
    @FXML private TableColumn<BoughtProduct, BigDecimal> grossValCol;
    @FXML private TableColumn<BoughtProduct, BoughtProduct> removeCol;
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
    @FXML private ComboBox<String> calculateValsComboBox;
    @FXML private ComboBox<String> showUnitPriceComboBox;
    @FXML private DatePicker paidDatePicker;
    @FXML private ComboBox<String> calculateTotalComboBox;
    @FXML private Button saveBtn;
    //endregion

    @Autowired private ICustomerService customerService;
    @Autowired private IProductService productService;
    @Autowired private IInvoiceService invoiceService;
    private ObservableList<BoughtProduct> productsList;
    private Invoice invoice;
    private Seller seller;
    private Customer customer;
    private BigDecimal netVal;
    private BigDecimal vatVal;
    private BigDecimal grossVal;
    private BigDecimal discountVal;

    @FXML
    public void initialize()
    {
        initButtons();
        initComboBoxes();
        initProductsTable();
        initOptions();
    }

    private void initButtons() {
        buyerFromDatabaseBtn.setOnAction(event -> openSelectCustomerDialog());
        addItemBtn.setOnAction(event -> openSelectProductDialog());
        saveBtn.setOnAction(event -> {
            if (ensureFieldsFilled())
            {
                this.invoice.setInvoiceNumber(numberTxtFld.getText());
                this.invoice.setType(InvoiceType.typeMap.get(typeComboBox.getSelectionModel().getSelectedItem()));
                this.invoice.setIssueDate(issueDatePicker.getValue());
                this.invoice.setNetValue(this.netVal);
                this.invoice.setVatValue(this.vatVal);
                this.invoice.setGrossValue(this.grossVal);
                this.invoice.setDiscountValue(this.discountVal);
                this.invoice.setPaidAmount(new BigDecimal(paidAmountTxtFld.getText()));
                this.invoice.setPaymentMethod(PaymentMethod.paymentMap.get(paymentMethodComboBox.getSelectionModel()
                        .getSelectedItem()));
                this.invoice.setPaidDate(paidDatePicker.getValue());
                this.invoice.setPaymentDateDays(paymentDateComboBox.getSelectionModel().getSelectedItem());
                this.invoice.setCurrency(invoiceCurrencyComboBox.getSelectionModel().getSelectedItem());
                this.invoice.setStatus(InvoiceStatus.statusMap.get(statusComboBox.getSelectionModel().getSelectedItem()));
                this.invoice.setSaleDate(saleDatePicker.getValue());
                this.invoice.setLocation(locationTxtFld.getText());
                this.invoice.setLastModified(LocalDate.now());
                this.invoice.setRemarks(remarksTextArea.getText());
                this.invoice.getBoughtProductSet().clear();
                this.invoice.getBoughtProductSet().addAll(productsList);
                this.invoice.setCustomer(this.customer);
                this.invoice.setSeller(this.seller);

                invoiceService.save(this.invoice);
                for (BoughtProduct product : productsList)
                {
                    // maybe not the perfect solution, but it works considering that bought products table holds
                    // raw information about the product from the date of purchase and not the product as a foreign key
                    Product p = productService.findByProductName(product.getProductName());
                    p.getWarehouse().setAvailable(p.getWarehouse().getAvailable() - product.getQuantity());
                    productService.save(p);
                }
            }
        });
    }

    private boolean ensureFieldsFilled()
    {
        // FIXME: NullPointerException
        if (numberTxtFld.getText().isEmpty() ||
                typeComboBox.getSelectionModel().isEmpty() ||
                seller == null ||
                customer == null ||
                locationTxtFld.getText().isEmpty() ||
                paymentMethodComboBox.getSelectionModel().isEmpty() ||
                paymentDateComboBox.getSelectionModel().isEmpty() ||
                invoiceCurrencyComboBox.getSelectionModel().isEmpty() ||
                statusComboBox.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error in saving invoice");
            alert.setHeaderText("One or more fields are empty");
            StringBuilder builder = new StringBuilder("It seems that one or more fields that need to be filled are empty. Please, " +
                    "check whether these fields are filled properly:\n\n");
            builder.append("- Invoice number\n");
            builder.append("- Invoice type\n");
            builder.append("- Seller\n");
            builder.append("- Buyer\n");
            builder.append("- Sale location\n");
            builder.append("- Payment method\n");
            builder.append("- Payment date in days\n");
            builder.append("- Currency\n");
            builder.append("- Invoice status\n");
            alert.setContentText(builder.toString());

            alert.showAndWait();
            return false;
        }

        return true;
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
        tableView.getItems().setAll(productService.findAllByIsActiveTrue());
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

            BoughtProduct boughtProduct = new BoughtProduct(product.getProductName(), product.getSymbol(),
                    product.getUnit(), product.getNetPrice(), product.getVatRate(), 0, BigDecimal.ZERO);

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
        tableView.getItems().setAll(customerService.findAll());
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
        result.ifPresent(selectedCustomer -> {
            this.customer = selectedCustomer;
            initBuyerFields(selectedCustomer);
        });
    }

    private void initComboBoxes() {
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
        typeComboBox.getItems().setAll("Ordinary", "Pro forma", "Collective", "Expense");
        paymentMethodComboBox.getItems().setAll("Cash", "Bank transfer", "Credit card", "Check", "Cash on delivery",
                "Paypal");
        statusComboBox.getItems().setAll("Issued", "Paid", "Partially paid", "Rejected", "Unpaid", "Paid after deadline",
                "Unpaid expired");
        currencyComboBox.setItems(currencies);
        invoiceCurrencyComboBox.setItems(currencyCodes);
        invoiceCurrencyComboBox.setOnAction(event -> {
            grossCurrencyLabel.setText(invoiceCurrencyComboBox.getSelectionModel().getSelectedItem());
            taxCurrencyLabel.setText(invoiceCurrencyComboBox.getSelectionModel().getSelectedItem());
        });
        languageComboBox.setItems(languages);
        paymentDateComboBox.getItems().setAll(1, 3, 5, 7, 14, 21, 30, 45, 60, 75, 90);
        countryComboBox.setItems(countries);
    }

    private void initSellerFields(Seller seller) {
        sellerTxtFld.setText(String.format("%s %s", seller.getFirstName(), seller.getLastName()));
        sellerTaxIdTxtFld.setText(seller.getTaxIdentifier());
        sellerAddressTxtFld.setText(seller.getAddress());
        sellerPostalCodeTxtFld.setText(seller.getPostalCode());
        sellerCityTxtFld.setText(seller.getCity());
        accountTxtFld.setText(seller.getAccountNum());
        bankTxtFld.setText(seller.getBank());
        sellerEmailTxtFld.setText(seller.getEmail());
        sellerPhoneTxtFld.setText(String.valueOf(seller.getTelephone()));
        sellerFaxTxtFld.setText(String.valueOf(seller.getFax()));
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
        paymentMethodComboBox.getSelectionModel().select(PaymentMethod.paymentMap.inverse().get(customer
                .getDefaultPaymentMethod()));
        paymentDateComboBox.getSelectionModel().select(customer.getDefaultPaymentDateDays());
    }

    private void initProductsTable() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        symbolCol.setCellValueFactory(new PropertyValueFactory<>("symbol"));
        unitCol.setCellValueFactory(new PropertyValueFactory<>("unit"));
        netPriceCol.setCellValueFactory(new PropertyValueFactory<>("priceProp"));
        taxRateCol.setCellValueFactory(new PropertyValueFactory<>("taxRateProp"));
        removeCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        removeCol.setCellFactory(param -> new TableCell<BoughtProduct, BoughtProduct>(){
            private Button removeButton = new Button("", new ImageView(this.getClass()
                    .getResource("/images/icons8-Minus-24.png").toString()));

            @Override
            protected void updateItem(BoughtProduct product, boolean empty)
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

            if (event.getNewValue() < 0 || event.getNewValue() > productService.findByProductName(event.getRowValue()
                    .getProductName()).getWarehouse().getAvailable())
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error adding a product");
                alert.setHeaderText("Not enough items in the warehouse");
                alert.setContentText("Selected product amount exceeds amount in the warehouse");

                alert.showAndWait();
                return;
            }

            BigDecimal tmpNetVal = event.getRowValue().getNetValProp();
            BigDecimal tmpVatVal = event.getRowValue().getTaxValProp();
            BigDecimal tmpGrossVal = event.getRowValue().getGrossValProp();
            BigDecimal tmpDiscountVal = event.getRowValue().getDiscountValProp();
            event.getRowValue().setQuantityProp(event.getNewValue());
            this.netVal = this.netVal.subtract(tmpNetVal);
            this.vatVal = this.vatVal.subtract(tmpVatVal);
            this.grossVal = this.grossVal.subtract(tmpGrossVal);
            this.discountVal = this.discountVal.subtract(tmpDiscountVal);
            setValueLabels();
        });
        netValCol.setCellValueFactory(new PropertyValueFactory<>("netValProp"));
        discountCol.setCellValueFactory(new PropertyValueFactory<>("discountProp"));
        discountCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        discountCol.setOnEditCommit(event -> event.getTableView().getItems().get(event.getTablePosition().getRow())
                .setDiscountProp(event.getNewValue()));
        grossValCol.setCellValueFactory(new PropertyValueFactory<>("grossValProp"));

        productTableView.setItems(productsList);
        discountChckBox.selectedProperty().addListener((observable, oldValue, newValue) ->
                discountCol.setVisible(newValue));
    }

    private void setValueLabels() {
        totalNetValLabel.setText(netVal.toString());
        totalTaxValLabel.setText(vatVal.toString());
        totalGrossValLabel.setText(grossVal.toString());
    }

    private void initOptions() {
        calculateValsComboBox.getItems().setAll("Total value",
                "Unit value (consistent with cash register)");
        calculateTotalComboBox.getItems().setAll(
                "Sum of all values from all records (preserves gross and net value)",
                "Sum of the gross values and calculate net and tax values (preserves gross value, consistent with cash register)",
                "Sum of the net values and calculate gross and tax values (preserves gross value)");
        showUnitPriceComboBox.getItems().setAll("Net value",
                "Gross value (consistent with cash register)");
        calculateValsComboBox.getSelectionModel().select(0);
        calculateTotalComboBox.getSelectionModel().select(0);
        showUnitPriceComboBox.getSelectionModel().select(0);

        // TODO: get back to this
    }

    @Override
    public void initializeFields(Invoice invoice) {
        this.invoice = invoice;
        productsList = FXCollections.observableArrayList(invoice.getBoughtProductSet());
        if (invoice.getStatus() != null)
            statusComboBox.getSelectionModel().clearSelection();
        if (invoice.getType() != null)
            typeComboBox.getSelectionModel().select(InvoiceType.typeMap.inverse().get(invoice.getType()));
        if (invoice.getPaymentMethod() != null)
            paymentMethodComboBox.getSelectionModel().select(PaymentMethod.paymentMap.inverse().get(invoice
                    .getPaymentMethod()));
        paymentDateComboBox.getSelectionModel().select(invoice.getPaymentDateDays());
        invoiceCurrencyComboBox.getSelectionModel().select(invoice.getCurrency());
        issueDatePicker.setValue(invoice.getIssueDate());
        saleDatePicker.setValue(invoice.getSaleDate());
        locationTxtFld.setText(invoice.getLocation());
        remarksTextArea.setText(invoice.getRemarks());
        paidDatePicker.setValue(invoice.getPaidDate());
        paidAmountTxtFld.setText(invoice.getPaidAmount().toString());
        numberTxtFld.setText(invoice.getInvoiceNumber());
        taxCurrencyLabel.setText(invoiceCurrencyComboBox.getSelectionModel().getSelectedItem());
        grossCurrencyLabel.setText(invoiceCurrencyComboBox.getSelectionModel().getSelectedItem());
        this.netVal = invoice.getNetValue();
        this.vatVal = invoice.getVatValue();
        this.grossVal = invoice.getGrossValue();
        this.discountVal = invoice.getDiscountValue();

        // TODO: load these two fields from settings
        //currencyComboBox.getSelectionModel().select(settings.getDefaultCurrency());
        //languageComboBox.getSelectionModel().select(settings.getDefaultLanguage());
        setValueLabels();
        if (invoice.getCustomer() != null) {
            this.customer = invoice.getCustomer();
            initBuyerFields(this.customer);
        }
        if (invoice.getSeller() != null) {
            this.seller = invoice.getSeller();
            initSellerFields(this.seller);
        }
    }
}