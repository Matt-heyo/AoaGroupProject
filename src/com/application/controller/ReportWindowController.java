package com.application.controller;

import com.application.model.VariableInvestorModel;
import com.application.view.ReportWindowView;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

public class ReportWindowController {

    private final ReportWindowView view;
    private final VariableInvestorModel model;
    private final Logger logger = com.application.model.LoggerConfig.getLogger();

    private final double principal;
    private final List<Double> rates;
    private final List<Double> balances;
    private final double finalBalance;
    private final List<String> marketLabels;

    public ReportWindowController(double principal,
                                  List<Double> rates,
                                  List<Double> balances,
                                  double finalBalance,
                                  VariableInvestorModel model) {

        this.principal = principal;
        this.rates = rates;
        this.balances = balances;
        this.finalBalance = finalBalance;
        this.model = model;
        this.marketLabels = model.classifyAllMarkets(rates);

        this.view = new ReportWindowView(balances, rates, marketLabels);

        populateTextReport();

        view.exportButton.addActionListener(e -> exportReport());
        view.closeButton.addActionListener(e -> view.dispose());
    }

    private void populateTextReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("VARIABLE INVESTOR REPORT\n");
        sb.append("Generated: ").append(LocalDateTime.now()).append("\n\n");
        sb.append("Principal: ").append(principal).append("\n");
        sb.append("Final Balance: ").append(String.format("%.2f", finalBalance)).append("\n");
        sb.append("Years: ").append(balances.size()).append("\n\n");

        sb.append(String.format("%-6s %-12s %-12s %-18s%n", "Year", "Rate", "Balance", "Market Type"));
        sb.append("----------------------------------------------------------\n");

        for (int i = 0; i < balances.size(); i++) {
            sb.append(String.format("%-6d %-12.4f %-12.2f %-18s%n",
                    (i + 1),
                    rates.get(i),
                    balances.get(i),
                    marketLabels.get(i)));
        }

        view.textArea.setText(sb.toString());
    }

    private void exportReport() {
        try {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Save Report As");

            int result = chooser.showSaveDialog(view);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                if (!file.getName().toLowerCase().endsWith(".txt")) {
                    file = new File(file.getAbsolutePath() + ".txt");
                }

                try (FileWriter writer = new FileWriter(file)) {
                    writer.write(view.textArea.getText());
                }

                logger.info("Report exported to " + file.getAbsolutePath());
                JOptionPane.showMessageDialog(view, "Report exported successfully.");
            }

        } catch (Exception e) {
            logger.severe("Error exporting report: " + e.getMessage());
            JOptionPane.showMessageDialog(view, "Failed to export report: " + e.getMessage());
        }
    }
}
