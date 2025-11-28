package com.application.controller;

import com.application.model.MaximumExpensedModel;
import com.application.view.MaximumExpensedView;

import javax.swing.*;

public class MaximumExpensedController {
    private final MaximumExpensedView view;
    private final MaximumExpensedModel model;

    public interface NavigationListener {
        void onBackToMenu();
    }

    public MaximumExpensedController(MaximumExpensedView view, MaximumExpensedModel model, NavigationListener listener) {
        this.view = view;
        this.model = model;

        // Handle calculate button
        view.calculateButton.addActionListener(e -> calculateEverything());

        // Handle back button
        view.backButton.addActionListener(e -> listener.onBackToMenu());
    }

    private void calculateEverything() {
        try {
            double balance = Double.parseDouble(view.balanceField.getText());
            double rate = Double.parseDouble(view.rateField.getText()) / 100.0; // Convert % to decimal
            int years = Integer.parseInt(view.yearsField.getText());

            double optimalWithdrawal = model.findOptimalWithdrawal(balance, rate, years);

            double finalBalance = model.calculateFinalBalance(balance, optimalWithdrawal, rate, years);

            displayResults(balance, rate, years, optimalWithdrawal, finalBalance);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Please enter valid numbers in all fields.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }

    private void displayResults(double balance, double rate, int years,
                                double withdrawal, double finalBalance) {

        StringBuilder result = new StringBuilder();
        result.append("=== RETIREMENT PLANNING RESULTS ===\n\n");

        result.append("YOUR INPUTS:\n");
        result.append("• Retirement Fund: $").append(String.format("%,.2f", balance)).append("\n");
        result.append("• Annual Interest Rate: ").append(String.format("%.1f%%", rate * 100)).append("\n");
        result.append("• Retirement Period: ").append(years).append(" years\n\n");

        result.append("OPTIMAL WITHDRAWAL STRATEGY:\n");
        result.append("• Maximum Annual Withdrawal: $").append(String.format("%,.2f", withdrawal)).append("\n");
        result.append("• Monthly Equivalent: $").append(String.format("%,.2f", withdrawal / 12)).append("\n\n");

        result.append("PROJECTION:\n");
        result.append("• Starting Balance: $").append(String.format("%,.2f", balance)).append("\n");
        result.append("• Annual Withdrawal: $").append(String.format("%,.2f", withdrawal)).append("\n");
        result.append("• Annual Interest Earned: ").append(String.format("%.1f%%", rate * 100)).append("\n");
        result.append("• Final Balance after ").append(years).append(" years: $").append(String.format("%,.2f", finalBalance)).append("\n\n");

        result.append("METHOD USED:\n");
        result.append("• Binary Search (Successive Approximation)\n");
        result.append("• Finds the perfect withdrawal amount\n");
        result.append("• Ensures funds last exactly ").append(years).append(" years\n");

        view.resultArea.setText(result.toString());
    }
}