package com.UI.controllers;

import com.UI.FxmlView;
import com.UI.SceneManager;
import com.entity.Customer;
import com.entity.Product;
import com.entity.Templates;
import com.service.ICustomerService;
import com.service.IProductService;
import com.service.ITemplatesService;
import com.utilities.BigDecimalEditableCell;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class HomePresenter implements Initializable {

    //region fxmls

    @FXML
    private Label customerLbl;

    @FXML
    private TableColumn<Templates, String> nameCol;

    @FXML
    private TableColumn<Templates, String> symbolCol;

    @FXML
    private TableColumn<Templates, BigDecimal> quantityCol;

    @FXML
    private TableColumn<Templates, String> unitCol;

    @FXML
    private TableColumn<Templates, BigDecimal> netCol;

    @FXML
    private TableColumn<Templates, BigDecimal> vatRateCol;

    @FXML
    private TableColumn<Templates, BigDecimal> vatValCol;

    @FXML
    private TableColumn<Templates, BigDecimal> grossCol;

    @FXML
    private TableColumn<Templates, Templates> deleteCol;

    @FXML
    private TableColumn<Templates, BigDecimal> netTotalCol;

    @FXML
    private Label totalLbl;

    @FXML
    private Label wordsLbl;

    @FXML
    private ListView<Customer> customersList;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableView<Templates> templateTable;
    //endregion

    @Lazy
    @Autowired
    private SceneManager sceneManager;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IProductService productService;

    @Autowired
    private ITemplatesService templatesService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTable();
        setListViewProperties();
        loadCustomers();
        datePicker.setValue(LocalDate.now());
    }

    private void setListViewProperties() {
        customersList.setCellFactory(param -> new ListCell<Customer>() {
            @Override
            protected void updateItem(Customer item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getAlias() == null) {
                    setText(null);
                } else {
                    setText(item.getAlias());
                }
            }
        });
        customersList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        customersList.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldVal, newVal) -> {
            customerLbl.setText(newVal.getAlias());
            templateTable.getItems().clear();
            templateTable.getItems().addAll(newVal.getTemplates());
        }));
    }

    private void loadCustomers() {
        customersList.getItems().clear();
        customersList.getItems().addAll(customerService.findAll());
    }

    private void initializeTable() {
        nameCol.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getProduct().getProductName()));
        symbolCol.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getProduct().getSymbol()));
        unitCol.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getProduct().getUnit()));
        netCol.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getProduct().getUnitNetPrice()));
        vatRateCol.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getProduct().getVatRate()));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantityProp"));
        quantityCol.setCellFactory(c -> new BigDecimalEditableCell());
        quantityCol.setOnEditCommit(event -> event.getRowValue().setQuantityProp(event.getNewValue()));
        deleteCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue())); // looks weird, fix it
        deleteCol.setCellFactory(param -> new TableCell<Templates, Templates>() {
            private Button removeButton = new Button("", new ImageView(new Image(getClass()
                    .getResourceAsStream("/images/icons8-clear-symbol-24.png"))));

            @Override
            protected void updateItem(Templates temp, boolean empty) {
                super.updateItem(temp, empty);

                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setGraphic(removeButton);
                    removeButton.setOnAction(event -> templateTable.getItems().remove(temp));
                }
            }
        });
        vatValCol.setCellValueFactory(new PropertyValueFactory<>("taxValProp"));
        grossCol.setCellValueFactory(new PropertyValueFactory<>("grossValProp"));
        netTotalCol.setCellValueFactory(new PropertyValueFactory<>("netValProp"));
    }

    @FXML
    void switchToCustomers() {
        sceneManager.switchScene(FxmlView.CUSTOMERS);
    }

    @FXML
    void switchToProducts() {
        sceneManager.switchScene(FxmlView.PRODUCTS);
    }

    @FXML
    void firstCustomer() {
        customersList.getSelectionModel().selectFirst();
    }

    @FXML
    void lastCustomer() {
        customersList.getSelectionModel().selectLast();
    }

    @FXML
    void prevCustomer() {
        customersList.getSelectionModel().selectPrevious();
    }

    @FXML
    void nextCustomer() {
        customersList.getSelectionModel().selectNext();
    }

    @FXML
    void addProduct() {

        if (customersList.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error in adding product");
            alert.setHeaderText("There is no customer selected");

            alert.showAndWait();
            return;
        }

        Dialog<Product> dialog = new Dialog<>();
        dialog.setTitle("Choose a product");
        dialog.setHeaderText("Select a product from the database");
        ButtonType selectBtnType = new ButtonType("Select", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelBtnType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(selectBtnType, cancelBtnType);
//        dialog.setGraphic(new ImageView(this.getClass().getResource("/images/icons8-Product-96.png")
//                .toString()));

        // create table view and columns
        TableView<Product> tableView = new TableView<>();
        TableColumn<Product, String> nameCol = new TableColumn<>("Product name");
        TableColumn<Product, String> symbolCol = new TableColumn<>("Symbol");
        TableColumn<Product, String> unitCol = new TableColumn<>("Unit");
        TableColumn<Product, BigDecimal> cpuCol = new TableColumn<>("CPU");
        TableColumn<Product, BigDecimal> vatRateCol = new TableColumn<>("VAT rate");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        symbolCol.setCellValueFactory(new PropertyValueFactory<>("symbol"));
        unitCol.setCellValueFactory(new PropertyValueFactory<>("unit"));
        cpuCol.setCellValueFactory(new PropertyValueFactory<>("unitNetPrice"));
        vatRateCol.setCellValueFactory(new PropertyValueFactory<>("vatRate"));
        tableView.getColumns().addAll(nameCol, symbolCol, unitCol, cpuCol, vatRateCol);
        tableView.getItems().setAll(productService.findAll());
        dialog.getDialogPane().setContent(tableView);
        dialog.getDialogPane().setPrefHeight(Region.USE_COMPUTED_SIZE);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == selectBtnType) {
                return tableView.getSelectionModel().getSelectedItem();
            }
            return null;
        });

        Optional<Product> result = dialog.showAndWait();
        result.ifPresent(product -> {

            Templates template = new Templates(customersList.getSelectionModel().getSelectedItem(), product);

            if (templateTable.getItems().contains(template)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error in adding product");
                alert.setHeaderText("Selected product is already on the list");
                alert.setContentText("It seems that selected product is already on the products lit. Please, " +
                        "modify existing one instead of adding a new one.");

                alert.showAndWait();
                return;
            }

            templateTable.getItems().add(template);
        });
    }

    @FXML
    void saveTemplate() {
        // if there is anything to compare with...
        if (customersList.getSelectionModel().getSelectedItem() != null) {
            if (!customersList.getSelectionModel().getSelectedItem().getTemplates().isEmpty()) {
                // ...find out if there is any difference between templates in Customer object itself
                // and what is currently in the table view
                Set<Integer> ids = templateTable.getItems().stream()
                        .map(Templates::getId)
                        .collect(Collectors.toSet());
                List<Templates> deletedTemplates = customersList.getSelectionModel().getSelectedItem()
                        .getTemplates().stream()
                        .filter(temp -> !ids.contains(temp.getId()))
                        .collect(Collectors.toList());
                if (!deletedTemplates.isEmpty()) {
                    templatesService.deleteInBatch(deletedTemplates);
                }
            }

            if (!templateTable.getItems().isEmpty()) {
                //update those that left, if any
                templatesService.saveAll(templateTable.getItems());
            }
            // seems to not working when saving as template and switching to another customer and back
            customersList.getSelectionModel().getSelectedItem().setTemplates(templateTable.getItems());
        }
    }
}
