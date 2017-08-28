package com.UI.controllers;

import com.UI.view.NewInvoiceView;
import com.entity.BaseAbstractEntity;
import com.entity.Customer;
import com.entity.Invoice;
import com.entity.contant_arrays.InvoiceStatus;
import com.entity.contant_arrays.InvoiceType;
import com.entity.contant_arrays.PaymentMethod;
import com.service.IInvoiceService;
import com.utilities.ViewSwitcher;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
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
    @FXML private TableView<BaseAbstractEntity> tableView;
    @FXML private TableColumn<BaseAbstractEntity, Number> orderCol;
    @FXML private TableColumn<Invoice, String> numberCol;
    @FXML private TableColumn<Invoice, BigDecimal> netValCol;
    @FXML private TableColumn<Invoice, BigDecimal> taxValCol;
    @FXML private TableColumn<Invoice, BigDecimal> grossValCol;
    @FXML private TableColumn<Invoice, String> sellerCol;
    @FXML private TableColumn<Customer, String> buyerCol;
    @FXML private TableColumn<Customer, String> lastNameCol;
    @FXML private TableColumn<Customer, String> firstNameCol;
    @FXML private TableColumn<Customer, String> emailCol;
    @FXML private TableColumn<Invoice, String> saleDateCol;
    @FXML private TableColumn<Invoice, String> issueDateCol;
    @FXML private TableColumn<Invoice, BigDecimal> paidAmountCol;
    @FXML private TableColumn<Invoice, String> paymentCol;
    @FXML private TableColumn<Invoice, String> paidDateCol;
    @FXML private TableColumn<Invoice, String> paymentDateCol;
    @FXML private TableColumn<Invoice, String> statusCol;
    @FXML private TableColumn<Customer, String> telephoneCol;
    @FXML private TableColumn<Customer, String> countryCol;
    @FXML private TableColumn<Invoice, String> creationDateCol;
    @FXML private TableColumn<Invoice, String> lastModifiedCol;
    @FXML private TableColumn<Invoice, String> sentDateCol;
    @FXML private TableColumn<Invoice, String> remarksCol;
    //endregion

    static
    {
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
    private ObservableList<BaseAbstractEntity> listOfInvoices;

    @FXML
    public void initialize()
    {
        initializeComboBoxes();
        initializeMenuItems();
        listOfInvoices = FXCollections.observableArrayList();
        tableView.setItems(listOfInvoices);
        setSearching();
        initializeColumns();
        newInvoiceBtn.setOnAction(event -> ViewSwitcher.openView(newInvoiceBtn, newInvoiceView));
    }

    private void initializeColumns() {
        orderCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(tableView.getItems()
                .indexOf(param.getValue()) + 1));
        numberCol.setCellValueFactory(new PropertyValueFactory<>("invoiceNumber"));
        netValCol.setCellValueFactory(new PropertyValueFactory<>("netValue"));
        taxValCol.setCellValueFactory(new PropertyValueFactory<>("taxValue"));
        grossValCol.setCellValueFactory(new PropertyValueFactory<>("grossValue"));
        sellerCol.setCellValueFactory(new PropertyValueFactory<>("seller"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        saleDateCol.setCellValueFactory(new PropertyValueFactory<>("saleDate"));
        issueDateCol.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
        paidAmountCol.setCellValueFactory(new PropertyValueFactory<>("paidAmount"));
        paymentCol.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        paidDateCol.setCellValueFactory(new PropertyValueFactory<>("paidDate"));
        paymentDateCol.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        telephoneCol.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        creationDateCol.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        lastModifiedCol.setCellValueFactory(new PropertyValueFactory<>("lastModified"));
        sentDateCol.setCellValueFactory(new PropertyValueFactory<>("sentDate"));
        remarksCol.setCellValueFactory(new PropertyValueFactory<>("notes"));
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
        invoiceTypeComboBox.setOnAction(event -> selectedTypeLabel.setText(invoiceTypeComboBox.getSelectionModel().getSelectedItem()));
        periodComboBox.setItems(period);
        periodComboBox.getSelectionModel().select(0);
        statusComboBox.setItems(status);
        statusComboBox.getSelectionModel().select(0);
        paymentComboBox.setItems(payment);
        paymentComboBox.getSelectionModel().select(0);
    }

    private void initializeMenuItems()
    {
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

    private void setSearching()
    {
        phraseTxtFld.setOnKeyPressed(event -> {
            if (!phraseTxtFld.getText().isEmpty() && event.getCode().equals(KeyCode.ENTER))
            {
                search();
            }
        });

        searchBtn.setOnAction(event -> search());
    }

    private void search()
    {
        // decided to use final arrays instead of enums to be able to easily query values by index, without using
        // enum's values() which returns copied values array anyway and each time values() is called

        listOfInvoices.setAll(invoiceService.findAll(
                InvoiceType.TYPE[invoiceTypeComboBox.getSelectionModel().getSelectedIndex()],
                startDates[periodComboBox.getSelectionModel().getSelectedIndex()],
                endDates[periodComboBox.getSelectionModel().getSelectedIndex()],
                InvoiceStatus.STATUS[statusComboBox.getSelectionModel().getSelectedIndex()],
                PaymentMethod.METHOD[paymentComboBox.getSelectionModel().getSelectedIndex()]
        ));
    }
}
