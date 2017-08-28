package com.UI.controllers;

import com.UI.view.NewInvoiceView;
import com.service.IInvoiceService;
import com.utilities.CurrencyHandler;
import com.utilities.ViewSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

    @Autowired private NewInvoiceView newInvoiceView;
    @Autowired private IInvoiceService invoiceService;

    @FXML
    public void initialize()
    {
        standardBtn.setOnAction(event -> ViewSwitcher.openView(standardBtn, newInvoiceView));
        proFormaBtn.setOnAction(event -> ViewSwitcher.openView(proFormaBtn, newInvoiceView));
        correctiveBtn.setOnAction(event -> ViewSwitcher.openView(correctiveBtn, newInvoiceView));
        expensesBtn.setOnAction(event -> ViewSwitcher.openView(expensesBtn, newInvoiceView));

        initRevenueLabels();
        initSalesLabels();
    }

    private void initSalesLabels()
    {
        LocalDate today = LocalDate.now();

        todaySalesLabel.setText(String.valueOf(invoiceService.countAllByPaidDateBetween(today, today)));
        lastSevenDaysLabel.setText(String.valueOf(invoiceService.countAllByPaidDateBetween(today.minusDays(7), today)));
        curMonthSalesLabel.setText(String.valueOf(invoiceService.countAllByPaidDateBetween(today.withDayOfMonth(1), today)));
        curYearSalesLabel.setText(String.valueOf(invoiceService.countAllByPaidDateBetween(today.withDayOfYear(1), today)));
    }

    private void initRevenueLabels()
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
