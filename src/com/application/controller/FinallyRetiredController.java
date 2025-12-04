package com.application.controller;

import com.application.model.FinallyRetiredModel;
import com.application.model.FinallyRetiredModel.Result;
import com.application.model.VariableInvestorModel;
import com.application.model.LoggerConfig;
import com.application.view.FinallyRetiredView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class FinallyRetiredController {

    private final FinallyRetiredView view;
    private final FinallyRetiredModel model;
    private final VariableInvestorModel variableModel; // for market classification + report window
    private final Logger logger = LoggerConfig.getLogger();

    // Stored after successful calculation (used for report window)
    private double lastPrincipal = 0;
    private List<Double> lastRates = null;
    private List<Double> lastYearBalances = null;
    private double lastFinalBalance = 0;
    private int lastEarliestYearIndex = -1;

    public interface NavigationListener {
        void onBackToMenu();
    }

    public FinallyRetiredController(FinallyRetiredView view,
                                    FinallyRetiredModel model,
                                    VariableInvestorModel variableModel,
                                    NavigationListener listener) {
        this.view = view;
        this.model = model;
        this.variableModel = variableModel;

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

            // Run finallyRetired model
            Result result = model.calculate(principal, rates);

            // Store for report window
            lastPrincipal = principal;
            lastRates = rates;
            lastYearBalances = result.getYearlyBalances();
            lastFinalBalance = result.getFinalBalance();
            lastEarliestYearIndex = result.getEarliestRetirementYearIndex();

            // Enable "View Report"
            view.viewReportButton.setEnabled(true);

            // Build nice table output
            StringBuilder sb = new StringBuilder();
            sb.append("FINALLY RETIRED SUMMARY\n");
            sb.append("------------------------\n");
            sb.append("Principal: ").append(String.format("%.2f", lastPrincipal)).append("\n");
            sb.append("Final Balance: ").append(String.format("%.2f", lastFinalBalance)).append("\n");
            sb.append("Years Simulated: ").append(lastYearBalances.size()).append("\n");

            if (lastEarliestYearIndex == -1) {
                sb.append("Earliest Safe Retirement: NOT POSSIBLE in this timeframe.\n");
            } else {
                sb.append("Earliest Safe Retirement (relative): Year ")
                  .append(lastEarliestYearIndex)
                  .append(" (after ")
                  .append(lastEarliestYearIndex)
                  .append(" year(s))\n");
            }

            sb.append("\nYEAR-BY-YEAR DETAILS\n");
            sb.append(String.format(
                    "%-6s %-12s %-12s %-18s %-12s%n",
                    "Year", "Rate", "Balance", "Market Type", "SafeFromHere"
            ));
            sb.append("---------------------------------------------------------------------\n");

            for (int i = 0; i < lastYearBalances.size(); i++) {
                int yearIndex = i + 1;
                double rate = lastRates.get(i);
                double balance = lastYearBalances.get(i);

                String marketType = variableModel.classifyMarket(rate);
                boolean safeFromHere = (lastEarliestYearIndex != -1) && (yearIndex >= lastEarliestYearIndex);
                String safeLabel = safeFromHere ? "YES" : "";

                sb.append(String.format(
                        "%-6d %-12.4f %-12.2f %-18s %-12s%n",
                        yearIndex, rate, balance, marketType, safeLabel
                ));
            }

            view.outputArea.setText(sb.toString());

        } catch (NumberFormatException ex) {
            logger.warning("Invalid numeric input (FinallyRetired): " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Please enter valid numeric values for principal and rates.");
        } catch (Exception ex) {
            logger.severe("FinallyRetired calculation error: " + ex.getMessage());
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    private void openReportWindow() {
        if (lastRates == null || lastYearBalances == null) {
            JOptionPane.showMessageDialog(null, "Please perform a calculation first.");
            return;
        }

        // Reuse your existing Variable Investor report window
        new ReportWindowController(
                lastPrincipal,
                lastRates,
                lastYearBalances,
                lastFinalBalance,
                variableModel     // used for classifyAllMarkets + chart
        );
    }
}
