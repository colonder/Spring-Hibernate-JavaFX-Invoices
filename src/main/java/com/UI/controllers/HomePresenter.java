package com.UI.controllers;

import com.UI.view.AbstractFxmlView;
import com.UI.view.InvoicesView;
import com.service.IInvoiceService;
import com.utilities.CurrencyHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

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

        initLabels();
    }

    private void openView(Button button, AbstractFxmlView view)
    {
        BorderPane parent = (BorderPane) button.getScene().getRoot();
        parent.setCenter(view.getView());
    }

    private void initLabels()
    {
        LocalDate today = LocalDate.now();

        try {
            todayRevenueLabel.setText(CurrencyHandler.formatToCurrency(invoiceService.sumByPeriod(today, today)));
        } catch (NullPointerException e) {
            todayRevenueLabel.setText(CurrencyHandler.formatToCurrency(BigDecimal.ZERO));
        }

        try {
            lastSevenDaysRevenueLabel.setText(CurrencyHandler.formatToCurrency(invoiceService
                    .sumByPeriod(today.minusDays(7), today)));
        } catch (NullPointerException e) {
            lastSevenDaysRevenueLabel.setText(CurrencyHandler.formatToCurrency(BigDecimal.ZERO));
        }

        try {
            curMonthRevenueLabel.setText(CurrencyHandler.formatToCurrency(invoiceService
                    .sumByPeriod(today.withDayOfMonth(1), today)));
        } catch (NullPointerException e) {
            curMonthRevenueLabel.setText(CurrencyHandler.formatToCurrency(BigDecimal.ZERO));
        }

        try {
            curYearRevenueLabel.setText(CurrencyHandler.formatToCurrency(invoiceService
                    .sumByPeriod(today.withDayOfYear(1), today)));
        } catch (NullPointerException e) {
            curYearRevenueLabel.setText(CurrencyHandler.formatToCurrency(BigDecimal.ZERO));
        }
    }
}
