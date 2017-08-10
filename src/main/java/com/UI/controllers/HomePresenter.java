package com.UI.controllers;

import com.UI.view.AbstractFxmlView;
import com.UI.view.InvoicesView;
import com.service.IInvoiceService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HomePresenter {
    @FXML private Label todayRevenueLabel;
    @FXML private Label lastSevenDaysRevenueLabel;
    @FXML private Label curMonthRevenueLabel;
    @FXML private Label curYearRevenueLabel;
    @FXML private Label todaySalesLabel;
    @FXML private Label lastSevenDaysLabel;
    @FXML private Label curMonthSalesLabel;
    @FXML private Label curYearSalesLabel;
    @FXML private Button standardBtn;
    @FXML private Button proFormaBtn;
    @FXML private Button correctiveBtn;
    @FXML private Button expensesBtn;

    @Autowired private InvoicesView invoicesView;
    @Autowired private IInvoiceService invoiceService;

    @FXML
    public void initialize()
    {
        standardBtn.setOnAction(event -> openView(standardBtn, invoicesView));
        proFormaBtn.setOnAction(event -> openView(proFormaBtn, invoicesView));
        correctiveBtn.setOnAction(event -> openView(correctiveBtn, invoicesView));
        expensesBtn.setOnAction(event -> openView(expensesBtn, invoicesView));

        //todayRevenueLabel.setText(CurrencyHandler.formatToCurrency(invoiceService.sumByPeriod(LocalDate.now())));
    }

    private void openView(Button button, AbstractFxmlView view)
    {
        BorderPane parent = (BorderPane) button.getScene().getRoot();
        parent.setCenter(view.getView());
    }
}
