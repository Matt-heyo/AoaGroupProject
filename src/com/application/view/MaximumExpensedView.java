package com.application.view;

import javax.swing.*;
import java.awt.*;

public class MaximumExpensedView extends JPanel {
    public JTextField balanceField;
    public JTextField rateField;
    public JTextField yearsField;
    public JButton calculateButton;
    public JButton backButton;
    public JTextArea resultArea;

    public MaximumExpensedView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);


        JLabel title = new JLabel("Maximum Withdrawal Calculator", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title, BorderLayout.NORTH);


        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        inputPanel.setBackground(Color.WHITE);

        inputPanel.add(new JLabel("Retirement Fund:"));
        balanceField = new JTextField();
        inputPanel.add(balanceField);

        inputPanel.add(new JLabel("Annual Interest Rate (%):"));
        rateField = new JTextField();
        inputPanel.add(rateField);

        inputPanel.add(new JLabel("Retirement Years:"));
        yearsField = new JTextField("20"); // Default 20 years
        inputPanel.add(yearsField);

        inputPanel.add(new JLabel("")); // Empty cell
        calculateButton = new JButton("Calculate");
        inputPanel.add(calculateButton);

        add(inputPanel, BorderLayout.CENTER);


        resultArea = new JTextArea(10, 40);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        add(scrollPane, BorderLayout.SOUTH);


        backButton = new JButton("Back to Menu");
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}