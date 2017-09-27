package com.UI.controllers;

import com.entity.Customer;
import com.enums.CustomerType;
import com.service.ICustomerService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.time.LocalDate;

@Controller
public class CustomersPresenter {
    //region fxml fields
    @FXML private Button addCustomerBtn;
    @FXML private TextField phraseTxtFld;
    @FXML private ComboBox<String> customerTypeComboBox;
    @FXML private TextArea tagTxtArea;
    @FXML private ComboBox<String> filterComboBox;
    @FXML private Button searchBtn;
    @FXML private CheckMenuItem aliasCheckMenuItem;
    @FXML private CheckMenuItem lastNameCheckMenuItem;
    @FXML private CheckMenuItem firstNameCheckMenuItem;
    @FXML private CheckMenuItem personalIdCheckMenuItem;
    @FXML private CheckMenuItem taxIdCheckMenuItem;
    @FXML private CheckMenuItem emailCheckMenuItem;
    @FXML private CheckMenuItem addressCheckMenuItem;
    @FXML private CheckMenuItem postalCodeCheckMenuItem;
    @FXML private CheckMenuItem cityCheckMenuItem;
    @FXML private CheckMenuItem telephoneCheckMenuItem;
    @FXML private CheckMenuItem cellphoneCheckMenuItem;
    @FXML private CheckMenuItem faxCheckMenuItem;
    @FXML private CheckMenuItem companyNumCheckMenuItem;
    @FXML private CheckMenuItem paymentCheckMenuItem;
    @FXML private CheckMenuItem creationDateCheckMenuItem;
    @FXML private CheckMenuItem lastPurchaseCheckMenuItem;
    @FXML private CheckMenuItem countryCheckMenuItem;
    @FXML private CheckMenuItem currencyCheckMenuItem;
    @FXML private CheckMenuItem discountCheckMenuItem;
    @FXML private CheckMenuItem paymentDateCheckMenuItem;
    @FXML private TableView<Customer> customersTableView;
    @FXML private TableColumn<Customer, String> aliasCol;
    @FXML private TableColumn<Customer, String> lastNameCol;
    @FXML private TableColumn<Customer, String> firstNameCol;
    @FXML private TableColumn<Customer, Long> personalIdCol;
    @FXML private TableColumn<Customer, String> taxIdCol;
    @FXML private TableColumn<Customer, String> emailCol;
    @FXML private TableColumn<Customer, String> addressCol;
    @FXML private TableColumn<Customer, String> postalCodeCol;
    @FXML private TableColumn<Customer, String> cityCol;
    @FXML private TableColumn<Customer, Integer> telephoneCol;
    @FXML private TableColumn<Customer, Integer> cellphoneCol;
    @FXML private TableColumn<Customer, Integer> faxCol;
    @FXML private TableColumn<Customer, Integer> companyNumCol;
    @FXML private TableColumn<Customer, String> tagCol;
    @FXML private TableColumn<Customer, String> paymentCol;
    @FXML private TableColumn<Customer, LocalDate> creationDateCol;
    @FXML private TableColumn<Customer, LocalDate> purchaseCol;
    @FXML private TableColumn<Customer, String> countryCol;
    @FXML private TableColumn<Customer, String> currencyCol;
    @FXML private TableColumn<Customer, BigDecimal> discountCol;
    @FXML private TableColumn<Customer, String> paymentDateCol;
    //endregion

    @Autowired
    private ICustomerService customerService;
    private ObservableList<Customer> listOfCustomers;

    @FXML
    public void initialize()
    {
        initComboBoxes();
        setSearching();
    }

    private void setSearching() {
        phraseTxtFld.setOnKeyPressed(event -> {
            if (!phraseTxtFld.getText().isEmpty() && event.getCode().equals(KeyCode.ENTER)) {
                search();
            }
        });

        searchBtn.setOnAction(event -> search());
    }

    private void search() {
        // TODO: finish implementing
        listOfCustomers.setAll(customerService.findAll());
    }

    private void initComboBoxes()
    {
        customerTypeComboBox.getItems().setAll(CustomerType.customerMap.keySet());
        // TODO: initialize filter variant combo box
    }
}
