package com.application.controller;

import com.application.model.VariableInvestorModel;
import com.application.view.VariableInvestorView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class VariableInvestorController {

    private final VariableInvestorView view;
    private final VariableInvestorModel model;
    private final Logger logger = com.application.model.LoggerConfig.getLogger();

    // stored after successful calculation
    private double lastPrincipal = 0;
    private List<Double> lastRates = null;
    private List<Double> lastYearBalances = null;
    private double lastFinalBalance = 0;

    public interface NavigationListener {
        void onBackToMenu();
    }

    public VariableInvestorController(VariableInvestorView view, VariableInvestorModel model, NavigationListener listener) {
        this.view = view;
        this.model = model;

        view.calculateButton.addActionListener(e -> calculate());
        view.backButton.addActionListener(e -> listener.onBackToMenu());
        view.viewReportButton.addActionListener(e -> openReportWindow());
    }

    private void calculate() {
        try {
            double principal = Double.parseDouble(view.principalField.getText().trim());

            String[] rateStrings = view.ratesField.getText().trim().split(",");
            List<Double> rates = new ArrayList<>();

            for (String s : rateStrings) {
                if (!s.trim().isEmpty()) {
                    rates.add(Double.parseDouble(s.trim()));
                }
            }

            double result = model.calculate(principal, rates);
            List<Double> yearlyBalances = model.calculateYearlyBalances(principal, rates);

            // store for report window
            lastPrincipal = principal;
            lastRates = rates;
            lastYearBalances = yearlyBalances;
            lastFinalBalance = result;

            // enable view report button
            view.viewReportButton.setEnabled(true);

            // show concise result
            StringBuilder sb = new StringBuilder();
            sb.append("Final Balance: ").append(String.format("%.2f", result)).append("\n");
            sb.append("Years Simulated: ").append(yearlyBalances.size()).append("\n");

            view.outputArea.setText(sb.toString());

        } catch (NumberFormatException ex) {
            logger.warning("Invalid numeric input: " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Please enter valid numeric values for principal and rates.");
        } catch (Exception ex) {
            logger.severe("Calculation error: " + ex.getMessage());
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    private void openReportWindow() {
        if (lastRates == null || lastYearBalances == null) {
            JOptionPane.showMessageDialog(null, "Please perform a calculation first.");
            return;
        }

        // create and show report window
        new ReportWindowController(
                lastPrincipal,
                lastRates,
                lastYearBalances,
                lastFinalBalance,
                model
        );
    }
}
