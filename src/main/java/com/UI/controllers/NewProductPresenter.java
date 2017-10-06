package com.UI.controllers;

import com.entity.Product;
import com.utilities.BigDecimalTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Controller;

@Controller
public class NewProductPresenter implements IInitializableFromEntity<Product> {

    @FXML CheckBox serviceChckBox;
    @FXML CheckBox onlineSaleChckBox;
    @FXML TextField productNameTxtFld;
    @FXML TextField codeTxtFld;
    @FXML BigDecimalTextField netPriceTxtFld;
    @FXML BigDecimalTextField vatTxtFld;
    @FXML BigDecimalTextField grossPriceTxtFld;
    @FXML TextField unitTxtFld;
    @FXML TextField tagsTxtFld;
    @FXML TextField symbolTxtFld;
    @FXML CheckBox inactiveChckBox;
    @FXML Button saveBtn;

    private Product product;

    @FXML
    public void initialize()
    {
        saveBtn.setOnAction(actionEvent -> {
            // TODO: implement
        });
    }

    @Override
    public void initializeFields(Product product) {
        if (product == null)
        {
            this.product = new Product();
            return;
        }

        this.product = product;
        serviceChckBox.setSelected(product.isService());
        onlineSaleChckBox.setSelected(product.isOnlineSale());
        inactiveChckBox.setSelected(!product.isActive());
        productNameTxtFld.setText(product.getProductName());
        codeTxtFld.setText(product.getWarehouse().getProductCode());
        netPriceTxtFld.setValue(product.getNetPrice());
        vatTxtFld.setValue(product.getVatRate());
        grossPriceTxtFld.setValue(product.getGrossPrice());
        unitTxtFld.setText(product.getUnit());
        tagsTxtFld.setText(product.getTag());
        symbolTxtFld.setText(product.getSymbol());
    }
}
