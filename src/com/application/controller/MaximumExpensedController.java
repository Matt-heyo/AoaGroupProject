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

        view.calculateButton.addActionListener(e -> calculateOptimalWithdrawal());
        view.backButton.addActionListener(e -> listener.onBackToMenu());
    }

    private void calculateOptimalWithdrawal() {
        try {
            String balanceText = view.balanceField.getText().trim();
            String rateText = view.rateField.getText().trim();
            String yearsText = view.yearsField.getText().trim();

            if (balanceText.isEmpty() || rateText.isEmpty() || yearsText.isEmpty()) {
                view.outputArea.setText("ERROR: All fields are required!\n\n" +
                        "Please enter:\n" +
                        "1. Retirement Fund (e.g., 500000)\n" +
                        "2. Annual Interest Rate (e.g., 4.5)\n" +
                        "3. Retirement Years (e.g., 20)");
                return;
            }


            double balance = Double.parseDouble(balanceText);
            double rate = Double.parseDouble(rateText) / 100.0;
            int years = Integer.parseInt(yearsText);

            if (balance < 0) {
                view.outputArea.setText("ERROR: Negative values not accepted for Retirement Fund!\n\n" +
                        "Please enter a positive amount.\n" +
                        "Example: 500000\n\n" +
                        "Note: Minimum required is $500,000");
                return;
            }

            if (rate < 0) {
                view.outputArea.setText("ERROR: Negative values not accepted for Interest Rate!\n\n" +
                        "Please enter a positive percentage.\n" +
                        "Example: 4.5 for 4.5%");
                return;
            }

            if (years < 0) {
                view.outputArea.setText("ERROR: Negative values not accepted for Retirement Years!\n\n" +
                        "Please enter a positive number of years.\n" +
                        "Example: 20\n\n" +
                        "Note: Minimum required is 10 years");
                return;
            }

            if (years < 10) {
                view.outputArea.setText("ERROR: Retirement Years must be at least 10 years!\n\n" +
                        "You entered: " + years + " years\n" +
                        "Minimum required: 10 years\n\n" +
                        "Please enter 10 or more years.");
                return;
            }

            if (balance < 500000) {
                view.outputArea.setText("ERROR: Retirement Fund must be at least $500,000!\n\n" +
                        "You entered: $" + String.format("%,.2f", balance) + "\n" +
                        "Minimum required: $500,000\n\n" +
                        "Please enter $500,000 or more.");
                return;
            }


            double optimalWithdrawal = model.findOptimalWithdrawal(balance, rate, years);
            displayResults(balance, rate, years, optimalWithdrawal);

        } catch (NumberFormatException ex) {
            view.outputArea.setText("ERROR: Invalid input detected!\n\n" +
                    "Please enter valid numbers in all fields.\n\n" +
                    "Examples:\n" +
                    "Retirement Fund: 500000 (no commas, no symbols)\n" +
                    "Annual Interest Rate: 4.5 (just the number, no % sign)\n" +
                    "Retirement Years: 20 (whole number only)\n\n" +
                    "Do NOT use:\n" +
                    "Letters: abc, xyz\n" +
                    "Symbols: $, %, @\n" +
                    "Special characters: commas, spaces");
        } catch (Exception ex) {
            view.outputArea.setText("ERROR: " + ex.getMessage());
        }
    }

    private void displayResults(double balance, double rate, int years, double withdrawal) {
        StringBuilder result = new StringBuilder();

        result.append("YOUR RETIREMENT PLAN:\n");
        result.append("Current retirement fund: $").append(String.format("%,.2f", balance)).append("\n");
        result.append("Annual interest rate: ").append(String.format("%.1f%%", rate * 100)).append("\n");
        result.append("Retirement period: ").append(years).append(" years\n\n");

        result.append("OPTIMAL WITHDRAWAL AMOUNTS:\n");
        result.append("Annual withdrawal amount: $").append(String.format("%,.2f", withdrawal)).append("\n");
        result.append("Monthly withdrawal amount: $").append(String.format("%,.2f", withdrawal / 12)).append("\n");
        result.append("Weekly withdrawal amount: $").append(String.format("%,.2f", withdrawal / 52)).append("\n");
        result.append("Daily withdrawal amount: $").append(String.format("%,.2f", withdrawal / 365)).append("\n\n");

        view.outputArea.setText(result.toString());
    }
}