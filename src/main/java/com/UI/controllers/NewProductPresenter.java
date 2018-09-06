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
            product.setVatRate(new BigDecimal(vatRateTxtFld.getText()));
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