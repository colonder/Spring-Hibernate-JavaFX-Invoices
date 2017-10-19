package com.UI.controllers;

import com.UI.view.ProductsView;
import com.entity.Product;
import com.entity.Warehouse;
import com.service.IProductService;
import com.utilities.BigDecimalTextField;
import com.utilities.IntegerTextField;
import com.utilities.Miscellaneous;
import com.utilities.ViewSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.time.LocalDate;

@Controller
public class NewProductPresenter implements IInitializableFromEntity<Product> {

    @FXML private CheckBox serviceChckBox;
    @FXML private CheckBox onlineSaleChckBox;
    @FXML private TextField productNameTxtFld;
    @FXML private TextField codeTxtFld;
    @FXML private BigDecimalTextField netPriceTxtFld;
    @FXML private BigDecimalTextField vatTxtFld;
    @FXML private BigDecimalTextField grossPriceTxtFld;
    @FXML private TextField unitTxtFld;
    @FXML private TextField tagsTxtFld;
    @FXML private TextField symbolTxtFld;
    @FXML private CheckBox inactiveChckBox;
    @FXML private Button saveBtn;
    @FXML private Label codeLabel;
    @FXML private IntegerTextField amountTextFld;
    @FXML private Label amountLbl;

    private Product product;
    @Autowired private IProductService productService;
    @Autowired private ProductsView productsView;

    @FXML
    public void initialize()
    {
        saveBtn.setOnAction(actionEvent -> {
            if (productNameTxtFld.getText().isEmpty() ||
                    unitTxtFld.getText().isEmpty() ||
                    netPriceTxtFld.getText().isEmpty() ||
                    grossPriceTxtFld.getText().isEmpty() ||
                    vatTxtFld.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("An error occurred while saving object to the database");
                alert.setContentText("It seems that some fields are empty while required");
                alert.showAndWait();
            }

            try {
                if (!serviceChckBox.isSelected())
                {
                    if (product.getWarehouse() == null)
                    {
                        product.setWarehouse(new Warehouse(amountTextFld.getValue()));
                    }

                    product.getWarehouse().setProductCode(codeTxtFld.getText());
                    product.getWarehouse().setLastModified(LocalDate.now());
                }

                product.setAll(
                        productNameTxtFld.getText(),
                        Miscellaneous.getTextFromControl(symbolTxtFld),
                        unitTxtFld.getText(),
                        Miscellaneous.getTextFromControl(tagsTxtFld),
                        netPriceTxtFld.getValue(),
                        grossPriceTxtFld.getValue(),
                        vatTxtFld.getValue(),
                        onlineSaleChckBox.isSelected(),
                        serviceChckBox.isSelected(),
                        !inactiveChckBox.isSelected(),
                        LocalDate.now()
                );
                productService.save(product);
                ViewSwitcher.openView(productsView);
                System.out.println(product.getWarehouse().getAvailable());
            } catch (ConstraintViolationException e) {
                Miscellaneous.showConstraintAlert();
            }
        });
        
        serviceChckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            codeLabel.setDisable(newValue);
            codeTxtFld.setDisable(newValue);
            amountLbl.setDisable(newValue);
            amountTextFld.setDisable(newValue);
        });

        netPriceTxtFld.valueProperty().addListener((observable, oldValue, newValue) -> {
            try {
                grossPriceTxtFld.setValue(
                        newValue.add(newValue.multiply(vatTxtFld.getValue().multiply(Miscellaneous.ONE_HUNDREDTH)))
                                .setScale(2, BigDecimal.ROUND_HALF_DOWN));
            } catch (NullPointerException e) {
                grossPriceTxtFld.setValue(BigDecimal.ZERO);
            }
        });

        vatTxtFld.valueProperty().addListener((observable, oldValue, newValue) ->
        {
            try {
                grossPriceTxtFld.setValue(
                        netPriceTxtFld.getValue().add(netPriceTxtFld.getValue().multiply(newValue)
                                .multiply(Miscellaneous.ONE_HUNDREDTH)).setScale(2, BigDecimal.ROUND_HALF_DOWN));
            } catch (NullPointerException e) {
                grossPriceTxtFld.setValue(BigDecimal.ZERO);
            }
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
        if (product.getWarehouse() != null)
            codeTxtFld.setText(product.getWarehouse().getProductCode());
        netPriceTxtFld.setValue(product.getNetPrice());
        vatTxtFld.setValue(product.getVatRate());
        grossPriceTxtFld.setValue(product.getGrossPrice());
        unitTxtFld.setText(product.getUnit());
        tagsTxtFld.setText(product.getTag());
        symbolTxtFld.setText(product.getSymbol());
    }
}
