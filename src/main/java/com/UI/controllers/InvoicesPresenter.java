package com.UI.controllers;

import com.UI.view.NewInvoiceView;
import com.entity.Invoice;
import com.enums.InvoiceStatus;
import com.enums.InvoiceType;
import com.enums.PaymentMethod;
import com.service.IInvoiceService;
import com.utilities.ViewSwitcher;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Controller
public class InvoicesPresenter {

    private static final LocalDate[] startDates;
    private static final LocalDate[] endDates;
    //region FXML fields
    @FXML private TextField phraseTxtFld;
    @FXML private ComboBox<String> invoiceTypeComboBox;
    @FXML private ComboBox<String> periodComboBox;
    @FXML private ComboBox<String> statusComboBox;
    @FXML private ComboBox<String> paymentComboBox;
    @FXML private Button searchBtn;
    @FXML private Button newInvoiceBtn;
    @FXML private Button editBtn;
    @FXML private Button deleteBtn;
    @FXML private Label selectedTypeLabel;
    @FXML private CheckMenuItem numberCheckMenuItem;
    @FXML private CheckMenuItem netValCheckMenuItem;
    @FXML private CheckMenuItem taxValCheckMenuItem;
    @FXML private CheckMenuItem grossValCheckMenuItem;
    @FXML private CheckMenuItem sellerCheckMenuItem;
    @FXML private CheckMenuItem buyerCheckMenuItem;
    @FXML private CheckMenuItem emailCheckMenuItem;
    @FXML private CheckMenuItem saleDateCheckMenuItem;
    @FXML private CheckMenuItem issueDateCheckMenuItem;
    @FXML private CheckMenuItem paidAmountCheckMenuItem;
    @FXML private CheckMenuItem paymentMethodCheckMenuItem;
    @FXML private CheckMenuItem paidDateCheckMenuItem;
    @FXML private CheckMenuItem paymentDateCheckMenuItem;
    @FXML private CheckMenuItem statusCheckMenuItem;
    @FXML private CheckMenuItem telephoneCheckMenuItem;
    @FXML private CheckMenuItem countryCheckMenuItem;
    @FXML private CheckMenuItem creationDateCheckMenuItem;
    @FXML private CheckMenuItem lastModifiedCheckMenuItem;
    @FXML private CheckMenuItem sentDateCheckMenuItem;
    @FXML private CheckMenuItem remarksCheckMenuItem;
    @FXML private TableView<Invoice> tableView;
    @FXML private TableColumn<Invoice, Number> orderCol;
    @FXML private TableColumn<Invoice, String> numberCol;
    @FXML private TableColumn<Invoice, BigDecimal> netValCol;
    @FXML private TableColumn<Invoice, BigDecimal> taxValCol;
    @FXML private TableColumn<Invoice, BigDecimal> grossValCol;
    @FXML private TableColumn<Invoice, String> sellerCol;
    @FXML private TableColumn<Invoice, String> buyerCol;
    @FXML private TableColumn<Invoice, String> lastNameCol;
    @FXML private TableColumn<Invoice, String> firstNameCol;
    @FXML private TableColumn<Invoice, String> emailCol;
    @FXML private TableColumn<Invoice, String> saleDateCol;
    @FXML private TableColumn<Invoice, String> issueDateCol;
    @FXML private TableColumn<Invoice, BigDecimal> paidAmountCol;
    @FXML private TableColumn<Invoice, String> paymentCol;
    @FXML private TableColumn<Invoice, String> paidDateCol;
    @FXML private TableColumn<Invoice, String> paymentDateCol;
    @FXML private TableColumn<Invoice, String> statusCol;
    @FXML private TableColumn<Invoice, Integer> telephoneCol;
    @FXML private TableColumn<Invoice, String> countryCol;
    @FXML private TableColumn<Invoice, String> creationDateCol;
    @FXML private TableColumn<Invoice, String> lastModifiedCol;
    @FXML private TableColumn<Invoice, String> sentDateCol;
    @FXML private TableColumn<Invoice, String> remarksCol;
    //endregion

    static {
        LocalDate today = LocalDate.now();
        LocalDate previousMonth = today.minusMonths(1);
        LocalDate previousYear = today.minusYears(1);

        startDates = new LocalDate[]{
                previousYear.withDayOfMonth(1), // last 12 months beginning
                today.withDayOfMonth(1), // current month beginning
                previousMonth.withDayOfMonth(1), // last month beginning
                today.withDayOfYear(1), // current year beginning
                previousYear.withDayOfYear(1), // last year beginning
                null // all
        };

        endDates = new LocalDate[]{
                previousMonth.withDayOfMonth(previousMonth.lengthOfMonth()), // last 12 months end
                today, // current month end
                previousMonth.withDayOfMonth(previousMonth.lengthOfMonth()), // last month end
                today, // current year end
                previousYear.withDayOfYear(previousYear.lengthOfYear()), // last year end
                null // all
        };
    }

    @Autowired
    private IInvoiceService invoiceService;
    @Autowired
    private NewInvoiceView newInvoiceView;

    @FXML
    public void initialize() {
        initializeComboBoxes();
        initializeMenuItems();
        setSearching();
        initializeColumns();
        initializeButtons();
    }

    private void initializeButtons() {
        newInvoiceBtn.setOnAction(event -> ViewSwitcher.openAndInitialize(newInvoiceView, new Invoice(InvoiceType.ORDINARY)));
        editBtn.setOnAction(event -> {
            Invoice invoiceToEdit = tableView.getSelectionModel().getSelectedItem();
            if (invoiceToEdit != null)
                ViewSwitcher.openAndInitialize(newInvoiceView, invoiceToEdit);
        });
        deleteBtn.setOnAction(event -> {
            if (tableView.getSelectionModel().getSelectedItems().size() != 0) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Invoices deletion");
                alert.setHeaderText("Are you sure you want to delete selected invoices?");

                Optional<ButtonType> result = alert.showAndWait();
                result.ifPresent(choice -> {
                    if (choice.equals(ButtonType.OK)) {
                        for (Invoice invoice : tableView.getSelectionModel().getSelectedItems()) {
                            invoiceService.delete(invoice);
                        }
                    }
                });
            }
        });
    }

    private void initializeColumns() {
        orderCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(tableView.getItems()
                .indexOf(param.getValue()) + 1));
        numberCol.setCellValueFactory(new PropertyValueFactory<>("invoiceNumber"));
        netValCol.setCellValueFactory(new PropertyValueFactory<>("netValue"));
        taxValCol.setCellValueFactory(new PropertyValueFactory<>("taxValue"));
        grossValCol.setCellValueFactory(new PropertyValueFactory<>("grossValue"));
        sellerCol.setCellValueFactory(new PropertyValueFactory<>("seller"));
        lastNameCol.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getCustomer().getLastName()));
        firstNameCol.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getCustomer().getFirstName()));
        emailCol.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getCustomer().getEmail()));
        saleDateCol.setCellValueFactory(new PropertyValueFactory<>("saleDate"));
        issueDateCol.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
        paidAmountCol.setCellValueFactory(new PropertyValueFactory<>("paidAmount"));
        paymentCol.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        paidDateCol.setCellValueFactory(new PropertyValueFactory<>("paidDate"));
        paymentDateCol.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        telephoneCol.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getCustomer().getTelephone()));
        countryCol.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getCustomer().getCountry()));
        creationDateCol.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        lastModifiedCol.setCellValueFactory(new PropertyValueFactory<>("lastModified"));
        sentDateCol.setCellValueFactory(new PropertyValueFactory<>("sentDate"));
        remarksCol.setCellValueFactory(new PropertyValueFactory<>("notes"));
    }

    private void initializeComboBoxes() {
        invoiceTypeComboBox.getItems().setAll(InvoiceType.typeMap.keySet());
        invoiceTypeComboBox.getSelectionModel().select(0);
        invoiceTypeComboBox.setOnAction(event -> {
            selectedTypeLabel.setText(invoiceTypeComboBox.getSelectionModel()
                    .getSelectedItem());
            search();
        });
        periodComboBox.getItems().setAll("Last 12 months", "Current month", "Last month", "Current year", "Last year",
                "All");
        periodComboBox.getSelectionModel().select(0);
        statusComboBox.getItems().setAll(InvoiceStatus.statusMap.keySet());
        statusComboBox.getSelectionModel().select(0);
        paymentComboBox.getItems().setAll(PaymentMethod.paymentMap.keySet());
        paymentComboBox.getSelectionModel().select(0);
    }

    private void initializeMenuItems() {
        numberCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                numberCol.setVisible(newValue));
        netValCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                netValCol.setVisible(newValue));
        taxValCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                taxValCol.setVisible(newValue));
        grossValCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                grossValCol.setVisible(newValue));
        sellerCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                sellerCol.setVisible(newValue));
        buyerCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                buyerCol.setVisible(newValue));
        emailCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                emailCol.setVisible(newValue));
        saleDateCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                saleDateCol.setVisible(newValue));
        issueDateCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                issueDateCol.setVisible(newValue));
        paidAmountCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                paidAmountCol.setVisible(newValue));
        paymentMethodCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                paymentCol.setVisible(newValue));
        paidDateCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                paidDateCol.setVisible(newValue));
        paymentDateCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                paymentDateCol.setVisible(newValue));
        statusCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                statusCol.setVisible(newValue));
        telephoneCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                telephoneCol.setVisible(newValue));
        countryCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                countryCol.setVisible(newValue));
        creationDateCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                creationDateCol.setVisible(newValue));
        lastModifiedCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                lastModifiedCol.setVisible(newValue));
        sentDateCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                sentDateCol.setVisible(newValue));
        remarksCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                remarksCol.setVisible(newValue));
    }

    private void setSearching() {
        phraseTxtFld.setOnKeyPressed(event -> {
            if (!phraseTxtFld.getText().isEmpty() && event.getCode().equals(KeyCode.ENTER)) {
                search();
            }
        });

        // TODO: create specifications for invoice

        searchBtn.setOnAction(event -> search());
    }

    private void search() {
        tableView.getItems().setAll(invoiceService.findAll(
                InvoiceType.typeMap.get(invoiceTypeComboBox.getSelectionModel().getSelectedItem()),
                startDates[periodComboBox.getSelectionModel().getSelectedIndex()],
                endDates[periodComboBox.getSelectionModel().getSelectedIndex()],
                InvoiceStatus.statusMap.get(statusComboBox.getSelectionModel().getSelectedItem()),
                PaymentMethod.paymentMap.get(paymentComboBox.getSelectionModel().getSelectedItem())
        ));
    }
}
