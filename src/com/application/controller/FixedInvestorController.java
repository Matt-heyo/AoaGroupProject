package com.application.controller;

import com.application.model.FixedInvestor;
import com.application.view.FixedInvestorView;

import javax.swing.*;
import java.util.List;

public class FixedInvestorController {

    private final FixedInvestorView view;
    private final FixedInvestor model;
    private final Runnable backCallback;

    
    private String reportData = "";

    public FixedInvestorController(FixedInvestorView view,
                                   FixedInvestor model,
                                   Runnable backCallback) {

        this.view = view;
        this.model = model;
        this.backCallback = backCallback;

        
        view.calculateButton.addActionListener(e -> calculate());
        view.backButton.addActionListener(e -> backCallback.run());
        view.viewReportButton.addActionListener(e -> showReportPopup());
    }

    private void calculate() {
        try {
            double principal = Double.parseDouble(view.principalField.getText());
            double rate = Double.parseDouble(view.rateField.getText());
            int years = Integer.parseInt(view.yearsField.getText());

            if (principal <= 0)
                throw new IllegalArgumentException("Principal must be greater than 0.");

            if (years <= 0)
                throw new IllegalArgumentException("Years must be greater than 0.");

            double finalBalance = model.fixedInvestor(principal, rate, years);
            List<Double> yearlyBalances = model.getYearlyBalances(principal, rate, years);
            List<String> labels = model.classifyMarkets(rate, years);

            StringBuilder sb = new StringBuilder();

            sb.append("FIXED INVESTOR REPORT\n\n");
            sb.append("Principal: ").append(principal).append("\n");
            sb.append("Rate: ").append(rate).append("\n");
            sb.append("Years: ").append(years).append("\n");
            sb.append("Final Balance: ").append(String.format("%.2f", finalBalance)).append("\n\n");

            sb.append("Year   Balance        Market Condition\n");
            sb.append("----------------------------------------------\n");

            for (int i = 0; i < years; i++) {
                sb.append(String.format("%-6d %-14.2f %s\n",
                        (i + 1),
                        yearlyBalances.get(i),
                        labels.get(i)));
            }

            
            view.outputArea.setText(sb.toString());

            
            reportData = sb.toString();

            
            view.viewReportButton.setEnabled(true);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Please enter valid numbers.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage());
        }
    }

    private void showReportPopup() {
        JTextArea reportArea = new JTextArea(reportData);
        reportArea.setEditable(false);
        reportArea.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 13));

        JScrollPane scrollPane = new JScrollPane(reportArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(500, 400));

        JOptionPane.showMessageDialog(view, scrollPane, "Detailed Report", JOptionPane.PLAIN_MESSAGE);
    }
}
