package com.UI.controllers;

import com.UI.FxmlView;
import com.UI.SceneManager;
import com.entity.Product;
import com.service.IProductService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class NewProductPresenter implements Initializable {

    @FXML
    private TextField nameTxtFld;

    @FXML
    private TextField symbolTxtFld;

    @FXML
    private TextField unitTxtFld;

    @FXML
    private TextField unitNetPriceTxtFld;

    @FXML
    private RadioButton VAT8RadioBtn;

    @FXML
    private RadioButton VAT23RadioBtn;

    @FXML
    private RadioButton otherVatRadioBtn;

    @FXML
    private TextField vatRateTxtFld;

    @FXML
    private Button saveBtn;

    @Autowired
    @Lazy
    private SceneManager sceneManager;

    @Autowired
    private IProductService productService;

    @FXML
    void cancel() {
        clearFields();
        sceneManager.switchScene(FxmlView.PRODUCTS);
    }

    private void clearFields() {
        nameTxtFld.setText(null);
        symbolTxtFld.setText(null);
        unitTxtFld.setText(null);
        unitNetPriceTxtFld.setText(null);
        VAT23RadioBtn.setSelected(true);
    }

    private void saveProduct() {
        Product product = new Product();
        setProductProperties(product);

        Product newProduct = productService.save(product);

        saveAlert(newProduct);
        sceneManager.switchScene(FxmlView.PRODUCTS);
    }

    private void updateProduct(Product product) {
        setProductProperties(product);

        Product updatedProduct = productService.update(product);
        updateAlert(updatedProduct);

        saveBtn.setOnAction(actionEvent -> saveProduct());
        clearFields();
        sceneManager.switchScene(FxmlView.PRODUCTS);
    }

    private void setProductProperties(Product product) {
        product.setProductName(nameTxtFld.getText());
        product.setSymbol(symbolTxtFld.getText());
        product.setUnit(unitTxtFld.getText());
        product.setUnitNetPrice(new BigDecimal(unitNetPriceTxtFld.getText()));
        if (VAT8RadioBtn.isSelected())
            product.setVatRate(new BigDecimal(8));

        else if (VAT23RadioBtn.isSelected())
            product.setVatRate(new BigDecimal(23));

        else
            product.setVatRate(new BigDecimal(otherVatRadioBtn.getText()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        saveBtn.setOnAction(actionEvent -> saveProduct());
    }

    public void initData(Product product) {
        saveBtn.setOnAction(actionEvent -> updateProduct(product));

        nameTxtFld.setText(product.getProductName());
        symbolTxtFld.setText(product.getSymbol());
        unitTxtFld.setText(product.getUnit());
        unitNetPriceTxtFld.setText(product.getUnitNetPrice().toString());

        BigDecimal vat = product.getVatRate();

        if (vat.compareTo(new BigDecimal(8.0)) == 0)
            VAT8RadioBtn.setSelected(true);

        else if (vat.compareTo(new BigDecimal(23.0)) == 0)
            VAT23RadioBtn.setSelected(true);

        else {
            otherVatRadioBtn.setSelected(true);
            vatRateTxtFld.setText(vat.toString());
        }
    }

    private void saveAlert(Product product) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("User saved successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The product " + product.getProductName() + " has been created");
        alert.showAndWait();
    }

    private void updateAlert(Product product) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Product updated successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The product " + product.getProductName() + " has been updated.");
        alert.showAndWait();
    }
}


//package com.UI.controllers;
//
//import com.UI.FxmlView;
//import com.entity.Product;
//import com.service.IProductService;
//import com.utilities.BigDecimalTextField;
//import com.utilities.IntegerTextField;
//import com.utilities.Miscellaneous;
//import com.UI.SceneManager;
//import javafx.fxml.FXML;
//import javafx.scene.control.*;
//import org.hibernate.exception.ConstraintViolationException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.stereotype.Controller;
//
//import java.math.BigDecimal;
//
//@Controller
//public class NewProductPresenter implements IInitializableFromEntity<Product> {
//
//    @FXML private CheckBox serviceChckBox;
//    @FXML private TextField productNameTxtFld;
//    @FXML private TextField codeTxtFld;
//    @FXML private BigDecimalTextField netPriceTxtFld;
//    @FXML private BigDecimalTextField vatTxtFld;
//    @FXML private BigDecimalTextField unitNetPriceTxtFld;
//    @FXML private TextField unitTxtFld;
//    @FXML private TextField symbolTxtFld;
//    @FXML private CheckBox inactiveChckBox;
//    @FXML private Button saveBtn;
//    @FXML private Label codeLabel;
//    @FXML private IntegerTextField amountTextFld;
//    @FXML private Label amountLbl;
//
//    private Product product;
//    @Autowired private IProductService productService;
//
//    @Lazy
//    @Autowired
//    private SceneManager sceneManager;
//
//    @FXML
//    public void initialize()
//    {
//        saveBtn.setOnAction(actionEvent -> {
//            if (productNameTxtFld.getText().isEmpty() ||
//                    unitTxtFld.getText().isEmpty() ||
//                    netPriceTxtFld.getText().isEmpty() ||
//                    unitNetPriceTxtFld.getText().isEmpty() ||
//                    vatTxtFld.getText().isEmpty()) {
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Error");
//                alert.setHeaderText("An error occurred while saving object to the database");
//                alert.setContentText("It seems that some fields are empty while required");
//                alert.showAndWait();
//            }
//
//            try {
//
//                product.setAll(
//                        productNameTxtFld.getText(),
//                        Miscellaneous.getTextFromControl(symbolTxtFld),
//                        unitTxtFld.getText(),
//                        netPriceTxtFld.getValue(),
//                        vatTxtFld.getValue(),
//                        !inactiveChckBox.isSelected()
//                );
//                productService.save(product);
//                product = null; // save memory, ready for garbage collection
//                sceneManager.switchScene(FxmlView.PRODUCTS);
//            } catch (ConstraintViolationException e) {
//                Miscellaneous.showConstraintAlert();
//            }
//        });
//
//        serviceChckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
//            codeLabel.setDisable(newValue);
//            codeTxtFld.setDisable(newValue);
//            amountLbl.setDisable(newValue);
//            amountTextFld.setDisable(newValue);
//        });
//
//        netPriceTxtFld.valueProperty().addListener((observable, oldValue, newValue) -> {
//            try {
//                unitNetPriceTxtFld.setValue(
//                        newValue.add(newValue.multiply(vatTxtFld.getValue().multiply(Miscellaneous.ONE_HUNDREDTH)))
//                                .setScale(2, BigDecimal.ROUND_HALF_DOWN));
//            } catch (NullPointerException e) {
//                unitNetPriceTxtFld.setValue(BigDecimal.ZERO);
//            }
//        });
//
//        vatTxtFld.valueProperty().addListener((observable, oldValue, newValue) ->
//        {
//            try {
//                unitNetPriceTxtFld.setValue(
//                        netPriceTxtFld.getValue().add(netPriceTxtFld.getValue().multiply(newValue)
//                                .multiply(Miscellaneous.ONE_HUNDREDTH)).setScale(2, BigDecimal.ROUND_HALF_DOWN));
//            } catch (NullPointerException e) {
//                unitNetPriceTxtFld.setValue(BigDecimal.ZERO);
//            }
//        });
//    }
//
//    @Override
//    public void initializeFields(Product product) {
//        this.product = product;
//        inactiveChckBox.setSelected(product.isActive());
//        productNameTxtFld.setText(product.getProductName());
//        netPriceTxtFld.setValue(product.getUnitNetPrice());
//        vatTxtFld.setValue(product.getVatRate());
//        unitTxtFld.setText(product.getUnit());
//        symbolTxtFld.setText(product.getSymbol());
//    }
//}
