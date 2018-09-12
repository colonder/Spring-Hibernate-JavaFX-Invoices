package com.UI.controllers;

import com.UI.FxmlView;
import com.UI.SceneManager;
import com.entity.Product;
import com.service.IProductService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
    private RadioButton monthNoRadioBtn;

    @Autowired
    @Lazy
    private SceneManager sceneManager;

    @Autowired
    private IProductService productService;

    private Product product;

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
        monthNoRadioBtn.setSelected(true);
    }

    @FXML
    private void saveProduct() {
        if (this.product == null) {
            this.product = new Product();
        }
        setProductProperties(this.product);

        Product newProduct = productService.save(this.product);

        saveAlert(newProduct);
        clearFields();
        sceneManager.switchScene(FxmlView.PRODUCTS);
    }

    private void setProductProperties(Product product) {
        product.setProductName(nameTxtFld.getText());
        product.setSymbol(symbolTxtFld.getText());
        product.setUnit(unitTxtFld.getText());
        product.setUnitNetPrice(new BigDecimal(unitNetPriceTxtFld.getText()));
        product.setPerMonth(!monthNoRadioBtn.isSelected());
        if (VAT8RadioBtn.isSelected())
            product.setVatRate(new BigDecimal(8));

        else
            product.setVatRate(new BigDecimal(23));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.product = null;
    }

    public void initData(Product product) {
        this.product = product;

        nameTxtFld.setText(product.getProductName());
        symbolTxtFld.setText(product.getSymbol());
        unitTxtFld.setText(product.getUnit());
        unitNetPriceTxtFld.setText(product.getUnitNetPrice().toString());
        monthNoRadioBtn.setSelected(!product.getPerMonth());

        BigDecimal vat = product.getVatRate();

        if (vat.compareTo(new BigDecimal(8.0)) == 0)
            VAT8RadioBtn.setSelected(true);

        else
            VAT23RadioBtn.setSelected(true);
    }

    private void saveAlert(Product product) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Operation successful.");
        alert.setHeaderText(null);
        alert.setContentText("The product " + product.getProductName() + " has been updated");
        alert.showAndWait();
    }
}