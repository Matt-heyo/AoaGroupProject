package com.application.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class FixedInvestorView extends JPanel {

    private static final long serialVersionUID = 1L;

    public JTextField principalField;
    public JTextField rateField;
    public JTextField yearsField;

    public JButton calculateButton;
    public JButton backButton;
    public JButton viewReportButton;

    public JTextArea outputArea;

    // Shared Color Scheme (Matches All Modules)
    private final Color navyBlue = new Color(10, 61, 98);
    private final Color hoverBlue = new Color(27, 95, 140);
    private final Color gold = new Color(218, 165, 32);
    private final Color lightBackground = new Color(244, 246, 249);

    public FixedInvestorView() {

        setLayout(new BorderLayout());
        setBackground(lightBackground);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // ----------------------------
        // Title
        // ----------------------------
        JLabel title = new JLabel("Fixed Investment Growth Calculator", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 22));
        title.setForeground(navyBlue);
        add(title, BorderLayout.NORTH);

        // ----------------------------
        // Card Panel (White Card Box)
        // ----------------------------
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setOpaque(true);

        // Input Labels + Fields
        JLabel principalLabel = new JLabel("Principal Amount:");
        principalLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        principalField = createInputField();

        JLabel rateLabel = new JLabel("Annual Interest Rate (e.g. 0.05):");
        rateLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        rateField = createInputField();

        JLabel yearsLabel = new JLabel("Investment Duration (Years):");
        yearsLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        yearsField = createInputField();

        // ----------------------------
        // Buttons
        // ----------------------------
        calculateButton = createStyledButton("Calculate");
        viewReportButton = createStyledButton("View Report");
        viewReportButton.setEnabled(false); // enabled after a calculation
        backButton = createStyledButton("Back to Menu");

        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonContainer.setBackground(Color.WHITE);
        buttonContainer.add(calculateButton);
        buttonContainer.add(viewReportButton);
        buttonContainer.add(backButton);

        // Add items to card panel
        cardPanel.add(principalLabel);
        cardPanel.add(principalField);
        cardPanel.add(Box.createVerticalStrut(10));

        cardPanel.add(rateLabel);
        cardPanel.add(rateField);
        cardPanel.add(Box.createVerticalStrut(10));

        cardPanel.add(yearsLabel);
        cardPanel.add(yearsField);
        cardPanel.add(Box.createVerticalStrut(20));

        cardPanel.add(buttonContainer);

        add(cardPanel, BorderLayout.CENTER);

        // ----------------------------
        // Output (Styled Text Area)
        // ----------------------------
        outputArea = new JTextArea(8, 40);
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        outputArea.setBorder(BorderFactory.createLineBorder(navyBlue, 1));

        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(new EmptyBorder(10, 0, 0, 0));

        add(scrollPane, BorderLayout.SOUTH);
    }

    // ----------------------------
    // Helpers (Matches Other GUIs)
    // ----------------------------
    private JTextField createInputField() {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(250, 28));
        field.setMaximumSize(new Dimension(300, 28));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(160, 160, 160)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return field;
    }

    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);

        btn.setFocusPainted(false);
        btn.setBackground(navyBlue);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setPreferredSize(new Dimension(150, 35));

        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(navyBlue, 1),
                new EmptyBorder(6, 12, 6, 12)
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

    public Color getGold() {
        return gold;
    }
}
