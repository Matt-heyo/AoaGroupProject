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

        // Add debug message to verify button click
        view.calculateButton.addActionListener(e -> {
            System.out.println("Calculate button clicked - Maximum Withdrawal");
            calculateOptimalWithdrawal();
        });

        view.backButton.addActionListener(e -> listener.onBackToMenu());
    }

    private void calculateOptimalWithdrawal() {
        try {
            // Get user inputs
            double balance = Double.parseDouble(view.balanceField.getText().trim());
            double rate = Double.parseDouble(view.rateField.getText().trim()) / 100.0;
            int years = Integer.parseInt(view.yearsField.getText().trim());

            // Calculate optimal withdrawal using binary search
            double optimalWithdrawal = calculateWithBinarySearch(balance, rate, years);

            // Display results
            displayResults(balance, rate, years, optimalWithdrawal);

        } catch (NumberFormatException ex) {
            view.outputArea.setText("Error: Please enter valid numbers in all fields.\n\n" +
                    "Example:\n" +
                    "Retirement Fund: 500000\n" +
                    "Annual Interest Rate: 4\n" +
                    "Retirement Years: 20");
        } catch (Exception ex) {
            view.outputArea.setText("Error: " + ex.getMessage());
        }
    }

    private double calculateWithBinarySearch(double balance, double rate, int targetYears) {
        double low = 0;
        double high = balance;
        double bestWithdrawal = 0;

        // Binary search for optimal withdrawal
        for (int i = 0; i < 50; i++) {
            double mid = (low + high) / 2;
            int yearsLasts = simulateRetirement(balance, mid, rate);

            if (yearsLasts >= targetYears) {
                bestWithdrawal = mid;
                low = mid; // Try higher withdrawal
            } else {
                high = mid; // Try lower withdrawal
            }
        }

        return bestWithdrawal;
    }

    private int simulateRetirement(double balance, double withdrawal, double rate) {
        double currentBalance = balance;
        int years = 0;

        while (currentBalance > 0 && years < 100) {
            currentBalance = (currentBalance - withdrawal) * (1 + rate);
            years++;
        }

        return years;
    }

    private void displayResults(double balance, double rate, int years, double withdrawal) {
        StringBuilder result = new StringBuilder();
        result.append("=== MAXIMUM WITHDRAWAL CALCULATION ===\n\n");

        result.append("INPUT VALUES:\n");
        result.append("Retirement Fund: $").append(String.format("%,.2f", balance)).append("\n");
        result.append("Annual Interest Rate: ").append(String.format("%.1f%%", rate * 100)).append("\n");
        result.append("Retirement Period: ").append(years).append(" years\n\n");

        result.append("OPTIMAL WITHDRAWAL AMOUNTS:\n");
        result.append("Annual: $").append(String.format("%,.2f", withdrawal)).append("\n");
        result.append("Monthly: $").append(String.format("%,.2f", withdrawal / 12)).append("\n");
        result.append("Weekly: $").append(String.format("%,.2f", withdrawal / 52)).append("\n\n");

        result.append("CALCULATION METHOD:\n");
        result.append("Binary Search Algorithm\n");
        result.append("Finds maximum sustainable withdrawal\n");
        result.append("Funds should last exactly ").append(years).append(" years");

        view.outputArea.setText(result.toString());
    }
}