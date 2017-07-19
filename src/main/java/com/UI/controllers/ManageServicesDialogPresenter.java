package com.UI.controllers;

import com.entity.ServiceEntity;
import com.entity.ServiceEntity.ServiceEntityProps;
import com.service.IServicesEntityService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class ManageServicesDialogPresenter {

    @Autowired
    IServicesEntityService servicesEntityService;
    @FXML ComboBox<String> filterComboBox;
    @FXML TextField filterTextFld;
    @FXML Button filterBtn;
    @FXML Button newServiceBtn;
    @FXML Button editServiceBtn;
    @FXML Button deleteServiceBtn;
    @FXML TableView<ServiceEntityProps> serviceTableView;
    @FXML TableColumn<ServiceEntityProps, String> serviceNameCol;
    @FXML TableColumn<ServiceEntityProps, String> symbolCol;
    @FXML TableColumn<ServiceEntityProps, String> unitCol;
    @FXML TableColumn<ServiceEntityProps, BigDecimal> unitPriceCol;
    @FXML TableColumn<ServiceEntityProps, Integer> vatCol;

    private ObservableList<String> filterCriteria = FXCollections.observableArrayList("Nazwa", "Symbol PKWIU/PKOB",
            "Jednostka", "Stawka VAT");
    private FilteredList<ServiceEntityProps> filteredList;
    private ObservableList<ServiceEntityProps> servicesList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // TODO: write that in a thread to prevent program freezing
        for(ServiceEntity serviceEntity : servicesEntityService.findAll())
        {
            servicesList.add(serviceEntity.getServiceEntityProps());
        }
        serviceNameCol.setCellValueFactory(new PropertyValueFactory<>("serviceNameProp"));
        symbolCol.setCellValueFactory(new PropertyValueFactory<>("symbolProp"));
        unitCol.setCellValueFactory(new PropertyValueFactory<>("unitProp"));
        unitPriceCol.setCellValueFactory(new PropertyValueFactory<>("netUnitPriceProp"));
        vatCol.setCellValueFactory(new PropertyValueFactory<>("vatProp"));

        //initially display all tha data
        filteredList = new FilteredList<>(servicesList, p -> true);
        serviceTableView.setItems(filteredList);
        serviceTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        filterComboBox.setItems(filterCriteria);
        filterComboBox.getSelectionModel().select(0);
        initializeButtons();
    }

    private void initializeButtons() {
        filterBtn.setOnAction(event -> {
            filteredList.setPredicate(serviceProps -> {
                if (filterTextFld.getText().isEmpty())
                    return true;

                else {
                    String selected = filterTextFld.getText();
                    switch (filterComboBox.getSelectionModel().getSelectedItem()) {
                        case "Nazwa":
                            if (serviceProps.getServiceNameProp().contains(selected))
                                return true;
                            break;

                        case "Symbol PKWIU/PKOB":
                            if (serviceProps.getSymbolProp().contains(selected))
                                return true;
                            break;

                        case "Jednostka":
                            if (serviceProps.getUnitProp().contains(selected))
                                return true;
                            break;

                        case "Stawka VAT":
                            if (String.valueOf(serviceProps.getVatProp()).contains(selected))
                                return true;
                            break;
                    }
                }

                return false;
            });
        });
        newServiceBtn.setOnAction(event -> {
            ServiceEntity c = new ServiceEntity();
            showServiceWindow(c.getServiceEntityProps(), newServiceBtn);
        });
        editServiceBtn.setOnAction(event -> {
            if (serviceTableView.getSelectionModel().getSelectedItems().size() != 1) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Edytowanie usługi");
                alert.setHeaderText("Nie można edytować kilku usług na raz lub żadnej");
                alert.setContentText("Edytować usługi można tylko pojedynczo");
                alert.show();
            } else {
                showServiceWindow(serviceTableView.getSelectionModel().getSelectedItem(), editServiceBtn);
            }
        });

        deleteServiceBtn.setOnAction(event -> {
            if (serviceTableView.getSelectionModel().getSelectedItems().size() > 0) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Usunięcie usługi");
                alert.setHeaderText("Czy na pewno chcesz usunąć wybranych kontrahentów?");
                Optional<ButtonType> result = alert.showAndWait();
                result.ifPresent(e -> {
                    Alert lastStand = new Alert(Alert.AlertType.CONFIRMATION);
                    lastStand.setTitle("Usunięcie usługi");
                    lastStand.setHeaderText("To jest ostatni moment kiedy możesz się wycofać!");

                    Optional<ButtonType> finalResult = lastStand.showAndWait();
                    finalResult.ifPresent(c -> {
                        for (ServiceEntityProps props : serviceTableView.getSelectionModel().getSelectedItems())
                        {
                            servicesList.remove(props);
                            servicesEntityService.delete(props.getServiceEntity());
                        }
                        //FIXME: the last customer from those that are selected is not being deleted
                    });
                });
            }
        });
    }

    private void showServiceWindow(ServiceEntityProps props, Button source) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edycja uslugi");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        VBox box = new VBox(5);
        RadioButton vat23RadioButton = new RadioButton("23%");
        RadioButton vat8RadioButton = new RadioButton("8%");
        ToggleGroup vatGroup = new ToggleGroup();
        vat23RadioButton.setToggleGroup(vatGroup);
        vat8RadioButton.setToggleGroup(vatGroup);
        try {
            if (props.getVatProp() == 23)
                vat23RadioButton.setSelected(true);
            else
                vat8RadioButton.setSelected(true);
        } catch (NullPointerException e) {
            vat23RadioButton.setSelected(true); // select default value
        }

        TextField serviceNameTxtFld = new TextField(props.getServiceNameProp());
        TextField symbolTxtFld = new TextField(props.getSymbolProp());
        TextField unitTxtFld = new TextField(props.getUnitProp());
        TextField netUnitPriceTxtFld = new TextField(props.getNetUnitPriceProp().toString());
        TextField vatTxtFld = new TextField(String.valueOf(props.getVatProp()));

        box.getChildren().addAll(new Label("Nazwa usługi"), serviceNameTxtFld, new Label("Symbol PKWIU/PKOB"),
                symbolTxtFld, new Label("Jednostka"), unitTxtFld, new Label("Cena jednostkowa netto"),
                netUnitPriceTxtFld, new Label("Stawka VAT"), vat23RadioButton, vat8RadioButton);
        dialog.getDialogPane().setContent(box);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get().equals(ButtonType.OK)) {
            props.setServiceNameProp(serviceNameTxtFld.getText());
            props.setSymbolProp(symbolTxtFld.getText());
            props.setUnitProp(unitTxtFld.getText());
            props.setNetUnitPriceProp(new BigDecimal(netUnitPriceTxtFld.getText()));
            props.setVatProp(Integer.parseInt(vatTxtFld.getText()));

            if (source.equals(newServiceBtn)) {
                servicesList.add(props);
                servicesEntityService.save(props.getServiceEntity());
            } else {
                ServiceEntity tmp = props.getServiceEntity();
                /*servicesEntityService.update(tmp.getServiceName(), tmp.getFirstName(), tmp.getCompanyName(), tmp.getTaxIdentifier(),
                        tmp.getAddress(), tmp.getPostalCode(), tmp.getCity(), tmp.getPaymentMethod(), tmp.isIncludeInCount(),
                        tmp.getAlias(), tmp.getId());*/
                // TODO: implement, change these methods to values from text fields since @PreUpdate
            }
        }
    }
}
