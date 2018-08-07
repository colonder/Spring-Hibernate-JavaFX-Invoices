package com.UI.controllers;

import com.UI.FxmlView;
import com.entity.Customer;
import com.service.ICustomerService;
import com.utilities.BigDecimalTextField;
import com.utilities.IntegerTextField;
import com.utilities.Miscellaneous;
import com.UI.SceneManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.Currency;
import java.util.Locale;

@Controller
public class NewCustomerPresenter implements IInitializableFromEntity<Customer>{

    @FXML private TextField companyNameTxtFld;
    @FXML private TextField taxIdTxtFld;
    @FXML private TextField addressTxtFld;
    @FXML private TextField postalCodeTxtFld;
    @FXML private TextField cityTxtFld;
    @FXML private TextField firstNameTxtFld;
    @FXML private TextField lastNameTxtFld;
    @FXML private TextField aliasTxtFld;
    @FXML private IntegerTextField companyNumTxtFld;
    @FXML private ComboBox<String> defaultPaymentComboBox;
    @FXML private Button saveBtn;

    @Autowired private ICustomerService customerService;
    private Customer customer;

    @Lazy
    @Autowired
    private SceneManager sceneManager;

    @FXML
    public void initialize()
    {
        populateComboBoxes();
        initSaveButton();
    }

    private void initSaveButton() {
        saveBtn.setOnAction(actionEvent -> {
            if (taxIdTxtFld.getText() == null || taxIdTxtFld.getText().isEmpty())
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("An error occurred while saving object to the database");
                alert.setContentText("It seems that required field Tax identification number is empty");

                alert.showAndWait();
                return;
            }

//            try {
//                customer.setAll(
//                        Miscellaneous.getTextFromControl(aliasTxtFld),
//                        Miscellaneous.getTextFromControl(companyNameTxtFld),
//                        Miscellaneous.getTextFromControl(lastNameTxtFld),
//                        Miscellaneous.getTextFromControl(firstNameTxtFld),
//                        Miscellaneous.getTextFromControl(taxIdTxtFld),
//                        Miscellaneous.getTextFromControl(addressTxtFld),
//                        Miscellaneous.getTextFromControl(postalCodeTxtFld),
//                        Miscellaneous.getTextFromControl(cityTxtFld),
//                        PaymentMethod.paymentMap.get(defaultPaymentComboBox.getSelectionModel().getSelectedItem())
//                );
//                customerService.save(customer);
//                customer = null; // save memory, ready for garbage collection
//                sceneManager.switchScene(FxmlView.CUSTOMERS);
//            } catch (DataIntegrityViolationException e) {
//                Miscellaneous.showConstraintAlert();
//            }
        });
    }

    private void populateComboBoxes() {
        defaultPaymentComboBox.getItems().setAll("Cash", "Bank transfer", "Credit card", "Check", "Cash on delivery",
                "Paypal");
    }

        @Override
    public void initializeFields(Customer customer) {
        this.customer = customer;
        companyNameTxtFld.setText(customer.getCompanyName());
        taxIdTxtFld.setText(customer.getTaxIdentifier());
        addressTxtFld.setText(customer.getAddress());
        postalCodeTxtFld.setText(customer.getPostalCode());
        cityTxtFld.setText(customer.getCity());
        firstNameTxtFld.setText(customer.getFirstName());
        lastNameTxtFld.setText(customer.getLastName());
        aliasTxtFld.setText(customer.getAlias());
        if (customer.getCompanySpecialNumber() != null)
            companyNumTxtFld.setText(String.valueOf(customer.getCompanySpecialNumber()));
//        if (customer.getDefaultPaymentMethod() != null)
//        defaultPaymentComboBox.getSelectionModel().select(PaymentMethod.paymentMap.inverse().get(customer.getDefaultPaymentMethod()));
    }
}
