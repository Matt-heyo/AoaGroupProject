package com.application.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class FinallyRetiredView extends JPanel {

    private static final long serialVersionUID = 1L;

    public JTextField principalField;
    public JTextField withdrawalField;
    public JTextField rateField;

    public JButton calculateButton;
    public JButton backButton;

    public JTextArea outputArea;

    private final Color navyBlue = new Color(10, 61, 98);
    private final Color hoverBlue = new Color(27, 95, 140);
    private final Color lightBackground = new Color(244, 246, 249);

    public FinallyRetiredView() {

        setLayout(new BorderLayout());
        setBackground(lightBackground);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        //-----------------------------------------------
        // TITLE
        //-----------------------------------------------
        JLabel title = new JLabel("Retirement Duration Simulator", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 26));
        title.setForeground(navyBlue);
        add(title, BorderLayout.NORTH);

        //-----------------------------------------------
        // CENTER FORM PANEL USING GRIDBAGLAYOUT
        //-----------------------------------------------
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(new EmptyBorder(25, 25, 25, 25));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //-----------------------------------------------
        // INPUT: PRINCIPAL
        //-----------------------------------------------
        JLabel principalLabel = new JLabel("Initial Retirement Fund:");
        principalLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));

        principalField = new JTextField();
        principalField.setPreferredSize(new Dimension(250, 28));

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(principalLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(principalField, gbc);

        //-----------------------------------------------
        // INPUT: WITHDRAWAL
        //-----------------------------------------------
        JLabel withdrawLabel = new JLabel("Annual Withdrawal Amount:");
        withdrawLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));

        withdrawalField = new JTextField();
        withdrawalField.setPreferredSize(new Dimension(250, 28));

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(withdrawLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(withdrawalField, gbc);

        //-----------------------------------------------
        // INPUT: INTEREST RATE
        //-----------------------------------------------
        JLabel rateLabel = new JLabel("Annual Interest Rate (0.05 = 5%):");
        rateLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));

        rateField = new JTextField();
        rateField.setPreferredSize(new Dimension(250, 28));

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(rateLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(rateField, gbc);

        //-----------------------------------------------
        // BUTTONS
        //-----------------------------------------------
        calculateButton = createStyledButton("Calculate");
        backButton = createStyledButton("Back to Menu");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(calculateButton);
        buttonPanel.add(backButton);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(buttonPanel, gbc);

        add(formPanel, BorderLayout.CENTER);

        //-----------------------------------------------
        // OUTPUT AREA
        //-----------------------------------------------
        outputArea = new JTextArea(10, 40);
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        outputArea.setBorder(BorderFactory.createLineBorder(navyBlue, 1));

        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(new EmptyBorder(15, 0, 0, 0));

        add(scrollPane, BorderLayout.SOUTH);
    }

    //-----------------------------------------------
    // BUTTON STYLING
    //-----------------------------------------------
    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBackground(navyBlue);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setPreferredSize(new Dimension(160, 38));

        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(navyBlue, 1),
                new EmptyBorder(6, 15, 6, 15)
        ));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(hoverBlue);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(navyBlue);
            }
        });

        return btn;
    }
}
