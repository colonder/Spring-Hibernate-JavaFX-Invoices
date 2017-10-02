package com.UI.controllers;

import com.entity.Customer;
import com.enums.CustomerType;
import com.service.ICustomerService;
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
    @FXML private CheckMenuItem tagCheckMenuItem;
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

    @FXML
    public void initialize()
    {
        initComboBoxes();
        setSearching();
        initCheckMenuItems();
    }

    private void initCheckMenuItems() {
        aliasCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                aliasCol.setVisible(newValue));
        lastNameCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                lastNameCol.setVisible(newValue));
        firstNameCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                firstNameCol.setVisible(newValue));
        personalIdCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                personalIdCol.setVisible(newValue));
        taxIdCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                taxIdCol.setVisible(newValue));
        emailCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                emailCol.setVisible(newValue));
        addressCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                addressCol.setVisible(newValue));
        postalCodeCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                postalCodeCol.setVisible(newValue));
        cityCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                cityCol.setVisible(newValue));
        telephoneCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                telephoneCol.setVisible(newValue));
        cellphoneCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                cellphoneCol.setVisible(newValue));
        faxCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                faxCol.setVisible(newValue));
        companyNumCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                companyNumCol.setVisible(newValue));
        paymentCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                paymentCol.setVisible(newValue));
        creationDateCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                creationDateCol.setVisible(newValue));
        lastPurchaseCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                purchaseCol.setVisible(newValue));
        countryCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                countryCol.setVisible(newValue));
        currencyCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                currencyCol.setVisible(newValue));
        discountCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                discountCol.setVisible(newValue));
        paymentDateCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                paymentDateCol.setVisible(newValue));
        tagCheckMenuItem.selectedProperty().addListener((observable, oldValue, newValue) ->
                tagCol.setVisible(newValue));
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
        customersTableView.getItems().setAll(customerService.findAll(CustomerType.customerMap.get(customerTypeComboBox
                .getSelectionModel().getSelectedItem())));
    }

    private void initComboBoxes()
    {
        customerTypeComboBox.getItems().setAll(CustomerType.customerMap.keySet());
        customerTypeComboBox.getSelectionModel().selectFirst();
        // TODO: initialize filter variant combo box
    }
}
