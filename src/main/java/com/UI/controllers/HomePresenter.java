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
import com.utilities.CurrencyHandler;
import com.utilities.Miscellaneous;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.*;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
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
    private TableColumn<Templates, Templates> nameCol;

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

    @Autowired
    private CurrencyHandler currencyHandler;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTable();
        setListViewProperties();
        loadCustomers();
        datePicker.setValue(Miscellaneous.chosenDate);
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
            calculateTotal();
        }));
    }

    private void loadCustomers() {
        customersList.getItems().clear();
        customersList.getItems().addAll(customerService.findAll());
    }

    private void initializeTable() {
        nameCol.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue()));
        nameCol.setCellFactory(c -> new TableCell<Templates, Templates>() {
            @Override
            protected void updateItem(Templates templates, boolean b) {
                super.updateItem(templates, b);

                if (b || templates == null) {
                    setGraphic(null);
                } else {
                    Text text = new Text();
                    setPrefHeight(Control.USE_COMPUTED_SIZE);
                    text.wrappingWidthProperty().bind(nameCol.widthProperty());

                    StringBuilder val = new StringBuilder(templates.getProduct().getProductName());

                    if (templates.getProduct().getPerMonth()) {

                        int month = datePicker.getValue().getMonth().getValue();
                        int year = datePicker.getValue().getYear();
                        val.append(" za m-c ").append(month).append("/").append(year);

                        datePicker.valueProperty().addListener((observableValue, oldValue, newValue) -> {

                            text.setText(templates.getProduct().getProductName() + " za m-c " +
                                    newValue.getMonth().getValue() + "/" + newValue.getYear());

                            setGraphic(text);

                        });
                    }
                    text.setText(val.toString());
                    setGraphic(text);
                }
            }
        });
        symbolCol.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getProduct().getSymbol()));
        unitCol.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getProduct().getUnit()));
        netCol.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getProduct().getUnitNetPrice()));
        netCol.setCellFactory(Miscellaneous.getCellFactory());
        vatRateCol.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getProduct().getVatRate()));
        vatRateCol.setCellFactory(Miscellaneous.getCellFactory());
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantityProp"));
        quantityCol.setCellFactory(c -> new BigDecimalEditableCell());
        quantityCol.setOnEditCommit(event -> {
            event.getRowValue().setQuantityProp(event.getNewValue());
            calculateTotal();
        });
        deleteCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
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
                    removeButton.setOnAction(event -> {
                        templateTable.getItems().remove(temp);
                        calculateTotal();
                    });
                }
            }
        });
        vatValCol.setCellValueFactory(new PropertyValueFactory<>("taxValProp"));
        vatValCol.setCellFactory(Miscellaneous.getCellFactory());
        grossCol.setCellValueFactory(new PropertyValueFactory<>("grossValProp"));
        grossCol.setCellFactory(Miscellaneous.getCellFactory());
        netTotalCol.setCellValueFactory(new PropertyValueFactory<>("netValProp"));
        netTotalCol.setCellFactory(Miscellaneous.getCellFactory());
    }

    private void calculateTotal() {
        BigDecimal total = templateTable.getItems()
                .stream()
                .map(Templates::getGrossValProp)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        totalLbl.setText(currencyHandler.formatToCurrency(total));
        wordsLbl.setText(currencyHandler.convertSumToWords(total));
    }

    @FXML
    void dateSelection() {
        Miscellaneous.chosenDate = datePicker.getValue();
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
    void switchToSettings() {
        sceneManager.switchScene(FxmlView.SETTINGS);
    }

    @FXML
    void printInvoice() {

        if (customersList.getSelectionModel().getSelectedItem() != null) {
            Parent rootNode;

            FXMLLoader loader = sceneManager.getLoader(FxmlView.INVOICE);
            try {
                rootNode = loader.load();
                loader.<InvoicePresenter>getController().initData(
                        customersList.getSelectionModel().getSelectedItem(),
                        templateTable.getItems(),
                        totalLbl.getText(),
                        wordsLbl.getText(),
                        datePicker.getValue());
                Printer printer = Printer.getDefaultPrinter();
                PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT,
                        Printer.MarginType.HARDWARE_MINIMUM);
                PrinterJob job = PrinterJob.createPrinterJob();
                double scaleX = pageLayout.getPrintableWidth() / 794;
                double scaleY = pageLayout.getPrintableHeight() / 1123;
                Scale scale = new Scale(scaleX, scaleY);
                rootNode.getTransforms().add(scale);
                if (job != null && job.showPrintDialog(sceneManager.getPrimaryStage())) {
                    boolean success = job.printPage(pageLayout, rootNode);

                    if (success)
                        job.endJob();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Błąd drukowania");
                    alert.setHeaderText("Wystąpił błąd podczas drukowania");

                    alert.showAndWait();
                }

                rootNode.getTransforms().remove(scale);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
            alert.setTitle("Błąd dodawania produktu");
            alert.setHeaderText("Nie ma wybranego kontrahenta");

            alert.showAndWait();
            return;
        }

        Dialog<Product> dialog = new Dialog<>();
        dialog.setTitle("Wybierz produkt");
        ButtonType selectBtnType = new ButtonType("Wybierz", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelBtnType = new ButtonType("Anuluj", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(selectBtnType, cancelBtnType);

        // create table view and columns
        TableView<Product> tableView = new TableView<>();
        TableColumn<Product, String> nameCol = new TableColumn<>("Nazwa");
        TableColumn<Product, String> symbolCol = new TableColumn<>("Symbol");
        TableColumn<Product, String> unitCol = new TableColumn<>("j. m.");
        TableColumn<Product, BigDecimal> cpuCol = new TableColumn<>("Cena jedn. netto");
        cpuCol.setCellFactory(Miscellaneous.getCellFactory());
        TableColumn<Product, BigDecimal> vatRateCol = new TableColumn<>("Stawka VAT");
        vatRateCol.setCellFactory(Miscellaneous.getCellFactory());
        nameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        symbolCol.setCellValueFactory(new PropertyValueFactory<>("symbol"));
        unitCol.setCellValueFactory(new PropertyValueFactory<>("unit"));
        cpuCol.setCellValueFactory(new PropertyValueFactory<>("unitNetPrice"));
        vatRateCol.setCellValueFactory(new PropertyValueFactory<>("vatRate"));
        tableView.getColumns().addAll(nameCol, symbolCol, unitCol, cpuCol, vatRateCol);
        tableView.getItems().setAll(productService.findAll());
        dialog.getDialogPane().setContent(tableView);
        dialog.getDialogPane().setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

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
                alert.setTitle("Błąd dodawania produktu");
                alert.setHeaderText("Produkt jest już na liście");
                alert.setContentText("Proszę wybrać inny produkt lub zmodyfikować już istniejący");

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

                try {
                    templatesService.saveAll(templateTable.getItems());
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Błąd zapisywania szablonu");
                    alert.setHeaderText("Wystąpił błąd zapisywania szablonu, spróbuj jeszcze raz");

                    alert.showAndWait();
                }
            }

            customersList.getItems().set(
                    customersList.getSelectionModel().getSelectedIndex(),
                    customerService.find(customersList.getSelectionModel().getSelectedItem().getId())
            );
        }
    }
}
