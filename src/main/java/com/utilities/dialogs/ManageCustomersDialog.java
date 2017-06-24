package com.utilities.dialogs;

import com.entity.Customer;
import com.entity.Customer.CustomerProps;
import com.service.ICustomerService;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ManageCustomersDialog
{
    @Autowired
    private ICustomerService customerService;

    public void showDialog() {
        Dialog dialog = new Dialog();
        ButtonType okBtn = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType editBtn = new ButtonType("Edytuj");
        dialog.setTitle("Wybierz usługę do dodania");
        dialog.getDialogPane().getButtonTypes().addAll(okBtn, editBtn);

        TableView<CustomerProps> table = new TableView<>();
        TableColumn<CustomerProps, String> lastNameCol = new TableColumn<>("Nazwisko");
        TableColumn<CustomerProps, String> firstNameCol = new TableColumn<>("Imię");
        TableColumn<CustomerProps, String> companyNameCol = new TableColumn<>("Nazwa firmy");
        TableColumn<CustomerProps, String> IdNumberCol = new TableColumn<>("NIP/PESEL");
        TableColumn<CustomerProps, String> nameCol = new TableColumn<>("Adres");
        TableColumn<CustomerProps, String> postalCodeCol = new TableColumn<>("Kod pocztowy");
        TableColumn<CustomerProps, String> cityCol = new TableColumn<>("Miejscowość");
        TableColumn<CustomerProps, String> considerCountingCol = new TableColumn<>("Uwzględnij nr faktury");
        TableColumn<CustomerProps, String> paymentMethodCol = new TableColumn<>("Sposób zapłaty");
        TableColumn<CustomerProps, String> aliasCol = new TableColumn<>("Alias");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastNameProp"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("firstNameProp"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("companyNameProp"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("taxIdProp"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("addressProp"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("postalCodeProp"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("cityProp"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("paymentProp")); //TODO: change to boolean or sth
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("countProp")); //TODO: finally do the enumerate
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("aliasProp"));
        table.setEditable(false);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getColumns().addAll(lastNameCol, firstNameCol, companyNameCol, IdNumberCol, nameCol, postalCodeCol,
                cityCol, paymentMethodCol, considerCountingCol, aliasCol);

        //TODO: table showing up only alias

        ObservableList<CustomerProps> data = FXCollections.observableArrayList();
        for(Customer customer : customerService.findAll())
        {
            data.add(customer.new CustomerProps());
        }
        table.setItems(data);

        Label label = new Label("Wyszukaj po:");
        ObservableList<String> items = FXCollections.observableArrayList("Imię", "Nazwisko", "Alias",
                "Nazwa firmy", "PESEL/NIP", "Adres", "Kod pocztowy", "Miejscowość", "Sposób zapłaty",
                "Uwzględnij nr faktury");
        ComboBox<String> comboBox = new ComboBox<>(items);
        comboBox.getSelectionModel().select(0);

        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        TextField searchTextBox = new TextField();
        searchTextBox.setPromptText("Wyszukaj kontrahenta");
        searchTextBox.textProperty().addListener((observable, oldValue, newValue) -> {

            // to decrease numbers of database queries
            pause.playFromStart();
            data.clear();
            /*switch (comboBox.getSelectionModel().getSelectedItem())
            {
                case "Imię":
                    data.addAll(customerService.findAllByFirstNameContaining(newValue));
                    break;

                case "Nazwisko":
                    data.addAll(customerService.findAllByLastNameContaining(newValue));
                    break;

                case "Alias":
                    data.addAll(customerService.findAllByAliasContaining(newValue));
                    break;

                case "Nazwa firmy":
                    data.addAll(customerService.findAllByCompanyNameContaining(newValue));
                    break;

                case "PESEL/NIP":
                    data.addAll(customerService.findAllByTaxIdentifierContaining(newValue));
                    break;

                case "Adres":
                    data.addAll(customerService.findAllByAddressContaining(newValue));
                    break;

                case "Kod pocztowy":
                    data.addAll(customerService.findAllByPostalCodeContaining(newValue));
                    break;

                case "Miejscowość":
                    data.addAll(customerService.findAllByCityContaining(newValue));
                    break;

                case "Sposób zapłaty":
                    data.addAll(customerService.findAllByPaymentMethod(newValue));
                    break;

                case "Uwzględnij nr faktury":
                    data.addAll(customerService.findAllByIncludeInCount(newValue));
                    break;
            }*/

        });

        HBox hBox = new HBox(label, comboBox, searchTextBox);
        hBox.setSpacing(5d);
        hBox.setPadding(new Insets(5, 5, 5, 5));
        VBox vBox = new VBox(hBox, table);
        vBox.setSpacing(5d);
        vBox.setPadding(new Insets(5, 5, 5, 5));
        dialog.getDialogPane().setContent(vBox);

        Optional result = dialog.showAndWait();
        //TODO: do something with the result!
    }
}