package com.utilities.dialogs;

import com.entity.BoughtServices;
import com.entity.Customer.CustomerProps;
import com.entity.ServiceEntity;
import com.service.IBoughtServicesService;
import com.service.IServicesEntityService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

@Component
public class ChoiceServiceDialog {
    @Autowired
    private IServicesEntityService servicesEntityService;

    @Autowired
    private IBoughtServicesService boughtServicesService;
    private ObservableList<ServiceEntity> servicesList;

    public void showDialog(CustomerProps props)
    {
        servicesList = FXCollections.observableArrayList();
        new Thread(() -> servicesList.addAll(servicesEntityService.findAll())).start();
        Dialog<ArrayList<ServiceEntity>> dialog = new Dialog<>();
        initGUI(dialog);
        Optional<ArrayList<ServiceEntity>> result = dialog.showAndWait();
        result.ifPresent(list -> {
            for (ServiceEntity service : list) {
                BoughtServices bs = new BoughtServices(props.getCustomer(), service, BigDecimal.ZERO);
                boughtServicesService.save(bs);
                props.addBoughtServicesProps(bs.getBoughtServicesProps());
                //FIXME: why DB is making PK suddenly 0 and then refuses to add rest of the services?
            }
        });
    }

    private void initGUI(Dialog dialog)
    {
        ButtonType cancelButton = new ButtonType("Anuluj", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType acceptButton = new ButtonType("Dodaj wybrane", ButtonBar.ButtonData.OK_DONE);
        dialog.setTitle("Wybierz usługę do dodania");
        dialog.getDialogPane().getButtonTypes().addAll(acceptButton, cancelButton);
        TableView<ServiceEntity> table = new TableView<>();
        table.setEditable(false);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        TableColumn<ServiceEntity, String> column = new TableColumn<>("Nazwa usługi");
        column.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        table.getColumns().add(column);
        table.setItems(servicesList);

        VBox vBox = new VBox(table);
        vBox.setSpacing(5d);
        vBox.setPadding(new Insets(5, 5, 5, 5));
        dialog.getDialogPane().setContent(vBox);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == acceptButton)
                return new ArrayList<>(table.getSelectionModel().getSelectedItems());
            return null;
        });
    }
}