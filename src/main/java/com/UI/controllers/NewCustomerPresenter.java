package com.UI.controllers;

import com.entity.Customer;
import com.enums.CustomerType;
import com.enums.PaymentMethod;
import com.service.ICustomerService;
import com.utilities.BigDecimalTextField;
import com.utilities.IntegerTextField;
import com.utilities.Miscellaneous;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.Currency;
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
    @FXML private IntegerTextField telTxtFld;
    @FXML private IntegerTextField cellphoneTxtFld;
    @FXML private TextField emailTxtFld;
    @FXML private IntegerTextField faxTxtFld;
    @FXML private TextField tagsTxtFld;
    @FXML private BigDecimalTextField defaultDiscountTxtFld;
    @FXML private IntegerTextField companyNumTxtFld;
    @FXML private ComboBox<String> currencyComboBox;
    @FXML private Label companyNumLbl;
    @FXML private ComboBox<String> defaultPaymentComboBox;
    @FXML private ComboBox<Integer> defaultDaysComboBox;
    @FXML private Button saveBtn;

    @Autowired
    private ICustomerService customerService;
    private Customer customer;

    @FXML
    public void initialize()
    {
        initRadioButtons();
        populateComboBoxes();
        initSaveButton();
    }

    private void initSaveButton() {
        saveBtn.setOnAction(actionEvent -> {
            if (taxIdTxtFld.getText().isEmpty())
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("An error occurred while saving object to the database");
                alert.setContentText("It seems that required field Tax identification number is empty");

                alert.showAndWait();
                return;
            }

            try {
                customer.setAll(
                        Miscellaneous.getTextFromControl(aliasTxtFld),
                        Miscellaneous.getTextFromControl(companyNameTxtFld),
                        Miscellaneous.getTextFromControl(lastNameTxtFld),
                        Miscellaneous.getTextFromControl(firstNameTxtFld),
                        Miscellaneous.getTextFromControl(taxIdTxtFld),
                        Miscellaneous.getTextFromControl(emailTxtFld),
                        Miscellaneous.getTextFromControl(addressTxtFld),
                        Miscellaneous.getTextFromControl(postalCodeTxtFld),
                        Miscellaneous.getTextFromControl(cityTxtFld),
                        telTxtFld.getValue(),
                        cellphoneTxtFld.getValue(),
                        faxTxtFld.getValue(),
                        Miscellaneous.getTextFromControl(tagsTxtFld),
                        currencyComboBox.getSelectionModel().getSelectedItem(),
                        PaymentMethod.paymentMap.get(defaultPaymentComboBox.getSelectionModel().getSelectedItem()),
                        LocalDate.now(),
                        countryComboBox.getSelectionModel().getSelectedItem(),
                        companyRadioBtn.isSelected() ? CustomerType.COMPANY : CustomerType.PERSON,
                        companyNumTxtFld.getValue(),
                        defaultDiscountTxtFld.getValue(),
                        defaultDaysComboBox.getSelectionModel().getSelectedItem()
                );
                customerService.save(customer);
            } catch (ConstraintViolationException e) {
                Miscellaneous.showConstraintAlert();
            }
        });
    }

    private void populateComboBoxes() {
        defaultPaymentComboBox.getItems().setAll("Cash", "Bank transfer", "Credit card", "Check", "Cash on delivery",
                "Paypal");
        defaultDaysComboBox.getItems().setAll(1, 3, 5, 7, 14, 21, 30, 45, 60, 75, 90);
        ObservableList<String> countries = FXCollections.observableArrayList();
        ObservableList<String> currencies = FXCollections.observableArrayList();
        for (Locale locale : Locale.getAvailableLocales())
        {
            if (!locale.getDisplayCountry().isEmpty()) {
                countries.add(locale.getDisplayCountry());
            }
        }

        Currency.getAvailableCurrencies().forEach(currency -> currencies.add(currency.getCurrencyCode()));

        countryComboBox.setItems(countries);
        currencyComboBox.setItems(currencies);
    }

    private void initRadioButtons() {
        final ToggleGroup group = new ToggleGroup();
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
        if (customer.getTelephone() != null)
            telTxtFld.setText(String.valueOf(customer.getTelephone()));
        if (customer.getCellPhone() != null)
            cellphoneTxtFld.setText(String.valueOf(customer.getCellPhone()));
        emailTxtFld.setText(customer.getEmail());
        if (customer.getFax() != null)
            faxTxtFld.setText(String.valueOf(customer.getFax()));
        tagsTxtFld.setText(customer.getTag());
        if (customer.getDefaultDiscount() != null)
            defaultDiscountTxtFld.setText(String.valueOf(customer.getDefaultDiscount()));
        if (customer.getCompanySpecialNumber() != null)
            companyNumTxtFld.setText(String.valueOf(customer.getCompanySpecialNumber()));
        defaultPaymentComboBox.getSelectionModel().select(PaymentMethod.paymentMap.inverse().get(customer.getDefaultPaymentMethod()));
        defaultDaysComboBox.getSelectionModel().select(customer.getDefaultPaymentDateDays());
    }
}
