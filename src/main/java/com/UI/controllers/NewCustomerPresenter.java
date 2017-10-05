package com.UI.controllers;

import com.entity.Customer;
import com.enums.CustomerType;
import com.enums.PaymentMethod;
import com.service.ICustomerService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.converter.BigDecimalStringConverter;
import javafx.util.converter.IntegerStringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Locale;

@Controller
public class NewCustomerPresenter implements IInitializableFromEntity<Customer>{

    @FXML private RadioButton companyRadioBtn;
    @FXML private RadioButton personRadioBtn;
    @FXML private TextField companyNameTxtFld;
    @FXML private Label companyNameLbl;
    @FXML private TextField taxIdTxtFld;
    @FXML private TextField addressTxtFld;
    @FXML private TextField postalCodeTxtFld;
    @FXML private TextField cityTxtFld;
    @FXML private ComboBox<String> countryComboBox;
    @FXML private TextField firstNameTxtFld;
    @FXML private TextField lastNameTxtFld;
    @FXML private TextField aliasTxtFld;
    @FXML private TextField telTxtFld;
    @FXML private TextField cellphoneTxtFld;
    @FXML private TextField emailTxtFld;
    @FXML private TextField faxTxtFld;
    @FXML private TextField tagsTxtFld;
    @FXML private TextField defaultDiscountTxtFld;
    @FXML private TextField companyNumTxtFld;
    @FXML private Label companyNumLbl;
    @FXML private ComboBox<String> defaultPaymentComboBox;
    @FXML private ComboBox<Integer> defaultDaysComboBox;
    @FXML private Button saveBtn;

    @Autowired
    private ICustomerService customerService;
    private Customer customer;
    private final ToggleGroup group;

    public NewCustomerPresenter()
    {
        group = new ToggleGroup();
    }

    @FXML
    public void initialize()
    {
        initRadioButtons();
        populateComboBoxes();
        initSaveButton();
    }

    // FIXME: saving not working
    private void initSaveButton() {
        saveBtn.setOnAction(actionEvent -> {
            customer.setAll(
                    aliasTxtFld.getText(),
                    companyNameTxtFld.getText(),
                    lastNameTxtFld.getText(),
                    firstNameTxtFld.getText(),
                    taxIdTxtFld.getText(),
                    emailTxtFld.getText(),
                    addressTxtFld.getText(),
                    postalCodeTxtFld.getText(),
                    cityTxtFld.getText(),
                    Integer.parseInt(telTxtFld.getText()),
                    Integer.parseInt(cellphoneTxtFld.getText()),
                    Integer.parseInt(faxTxtFld.getText()),
                    tagsTxtFld.getText(),
                    PaymentMethod.paymentMap.get(defaultPaymentComboBox.getSelectionModel().getSelectedItem()),
                    LocalDate.now(),
                    countryComboBox.getSelectionModel().getSelectedItem(),
                    companyRadioBtn.isSelected() ? CustomerType.COMPANY : CustomerType.PERSON,
                    Integer.parseInt(companyNumTxtFld.getText()),
                    new BigDecimal(defaultDiscountTxtFld.getText()),
                    defaultDaysComboBox.getSelectionModel().getSelectedItem()
            );
            customerService.save(customer);
        });
    }

    private void populateComboBoxes() {
        defaultPaymentComboBox.getItems().setAll("Cash", "Bank transfer", "Credit card", "Check", "Cash on delivery",
                "Paypal");
        defaultDaysComboBox.getItems().setAll(1, 3, 5, 7, 14, 21, 30, 45, 60, 75, 90);
        ObservableList<String> countries = FXCollections.observableArrayList();
        for (Locale locale : Locale.getAvailableLocales())
        {
            if (!locale.getDisplayCountry().isEmpty()) {
                countries.add(locale.getDisplayCountry());
            }
        }
        countryComboBox.setItems(countries);
    }

    private void initRadioButtons() {
        companyRadioBtn.setToggleGroup(group);
        personRadioBtn.setToggleGroup(group);

        group.selectedToggleProperty().addListener(observable -> {
            if (group.getSelectedToggle() == companyRadioBtn)
            {
                companyNameTxtFld.setDisable(false);
                companyNameLbl.setDisable(false);
                companyNumTxtFld.setDisable(false);
                companyNumLbl.setDisable(false);
            }

            else
            {
                companyNameTxtFld.setDisable(true);
                companyNameLbl.setDisable(true);
                companyNumTxtFld.setDisable(true);
                companyNumLbl.setDisable(true);
            }
        });
    }

        @Override
    public void initializeFields(Customer customer) {

        if (customer == null) {
            this.customer = new Customer();
            return;
        }

        this.customer = customer;
        if (customer.getCustomerType() == CustomerType.COMPANY)
            companyRadioBtn.setSelected(true);
        else
            personRadioBtn.setSelected(true);
        companyNameTxtFld.setText(customer.getCompanyName());
        taxIdTxtFld.setText(customer.getTaxIdentifier());
        addressTxtFld.setText(customer.getAddress());
        postalCodeTxtFld.setText(customer.getPostalCode());
        cityTxtFld.setText(customer.getCity());
        countryComboBox.getSelectionModel().select(customer.getCountry());
        firstNameTxtFld.setText(customer.getFirstName());
        lastNameTxtFld.setText(customer.getLastName());
        aliasTxtFld.setText(customer.getAlias());
        telTxtFld.setText(String.valueOf(customer.getTelephone()));
        cellphoneTxtFld.setText(String.valueOf(customer.getCellPhone()));
        emailTxtFld.setText(customer.getEmail());
        faxTxtFld.setText(String.valueOf(customer.getFax()));
        tagsTxtFld.setText(customer.getTag());
        defaultDiscountTxtFld.setText(String.valueOf(customer.getDefaultDiscount()));
        companyNumTxtFld.setText(String.valueOf(customer.getCompanySpecialNumber()));
        defaultPaymentComboBox.getSelectionModel().select(PaymentMethod.paymentMap.inverse().get(customer.getDefaultPaymentMethod()));
        defaultDaysComboBox.getSelectionModel().select(customer.getDefaultPaymentDateDays());
    }
}
