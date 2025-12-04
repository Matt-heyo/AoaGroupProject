package com.application.controller;

import com.application.model.FinallyRetiredModel;
import com.application.view.FinallyRetiredView;

import javax.swing.*;

public class FinallyRetiredController {

    private final FinallyRetiredView view;
    private final FinallyRetiredModel model;
    private final Runnable backCallback;

    public FinallyRetiredController(FinallyRetiredView view,
                                    FinallyRetiredModel model,
                                    Runnable backCallback) {

        this.view = view;
        this.model = model;
        this.backCallback = backCallback;

        view.calculateButton.addActionListener(e -> calculate());
        view.backButton.addActionListener(e -> backCallback.run());
    }

    private void calculate() {
        try {
            double principal = Double.parseDouble(view.principalField.getText().trim());
            double withdrawal = Double.parseDouble(view.withdrawalField.getText().trim());
            double rate = Double.parseDouble(view.rateField.getText().trim());

            int years = model.calculate(principal, withdrawal, rate);

            StringBuilder sb = new StringBuilder();
            sb.append("RETIREMENT DURATION REPORT\n");
            sb.append("----------------------------\n");
            sb.append("Initial Fund: ").append(principal).append("\n");
            sb.append("Yearly Withdrawal: ").append(withdrawal).append("\n");
            sb.append("Interest Rate: ").append(rate).append("\n\n");
            sb.append("Your retirement funds will last: ")
              .append(years)
              .append(" year(s).");

            view.outputArea.setText(sb.toString());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage());
        }
    }
}
