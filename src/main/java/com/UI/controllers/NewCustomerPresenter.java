package com.UI.controllers;

import com.entity.Customer;
import com.enums.CustomerType;
import com.enums.PaymentMethod;
import com.service.ICustomerService;
import com.utilities.BigDecimalTextField;
import com.utilities.IntegerTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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
    @FXML private IntegerTextField telTxtFld;
    @FXML private IntegerTextField cellphoneTxtFld;
    @FXML private TextField emailTxtFld;
    @FXML private IntegerTextField faxTxtFld;
    @FXML private TextField tagsTxtFld;
    @FXML private BigDecimalTextField defaultDiscountTxtFld;
    @FXML private IntegerTextField companyNumTxtFld;
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
                        getTextFromControl(aliasTxtFld),
                        getTextFromControl(companyNameTxtFld),
                        getTextFromControl(lastNameTxtFld),
                        getTextFromControl(firstNameTxtFld),
                        getTextFromControl(taxIdTxtFld),
                        getTextFromControl(emailTxtFld),
                        getTextFromControl(addressTxtFld),
                        getTextFromControl(postalCodeTxtFld),
                        getTextFromControl(cityTxtFld),
                        telTxtFld.getValue(),
                        cellphoneTxtFld.getValue(),
                        faxTxtFld.getValue(),
                        getTextFromControl(tagsTxtFld),
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
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("An error occurred while saving object to the database");
                alert.setContentText("It seems that the object with identical data already exist in the database.");

                alert.showAndWait();
                return;
            }
        });
    }
    
    private String getTextFromControl(TextField textField)
    {
        if (textField.getText().isEmpty())
            return null;
        return textField.getText();
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
