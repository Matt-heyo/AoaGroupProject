package com.application.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class FinallyRetiredView extends JPanel {
    private static final long serialVersionUID = 1L;

    public JTextField principalField;
    public JTextField ratesField;
    public JButton calculateButton;
    public JButton backButton;
    public JButton viewReportButton; 
    public JTextArea outputArea;


    private final Color navyBlue = new Color(10, 61, 98);
    private final Color hoverBlue = new Color(27, 95, 140);
    private final Color lightBackground = new Color(244, 246, 249);
    private final Color gold = new Color(218, 165, 32);   // optional if you want to reuse

    public FinallyRetiredView() {
        setLayout(new BorderLayout());
        setBackground(lightBackground);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Title
        JLabel title = new JLabel("Finally Retired – Earliest Safe Retirement Year", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 22));
        title.setForeground(navyBlue);
        add(title, BorderLayout.NORTH);

        // Card panel
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        cardPanel.setBackground(Color.WHITE);

        // Principal row
        JLabel principalLabel = new JLabel("Principal Amount:");
        principalLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        principalField = createInputField();

        // Rates row
        JLabel ratesLabel = new JLabel("Rates (comma-separated):");
        ratesLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        ratesField = createInputField();
        ratesField.setToolTipText("Example: 0.05, 0.03, -0.02, 0.04");

        // Buttons
        calculateButton = createStyledButton("Calculate");
        backButton = createStyledButton("Back to Menu");
        viewReportButton = createStyledButton("View Report");
        viewReportButton.setEnabled(false);

        JPanel buttonContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonContainer.setBackground(Color.WHITE);
        buttonContainer.add(calculateButton);
        buttonContainer.add(viewReportButton);                  
        buttonContainer.add(backButton);
        

        // Add to card
        cardPanel.add(principalLabel);
        cardPanel.add(principalField);
        cardPanel.add(Box.createVerticalStrut(10));
        cardPanel.add(ratesLabel);
        cardPanel.add(ratesField);
        cardPanel.add(Box.createVerticalStrut(20));
        cardPanel.add(buttonContainer);

        add(cardPanel, BorderLayout.CENTER);

        // Output area
        outputArea = new JTextArea(10, 40);
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        outputArea.setBorder(BorderFactory.createLineBorder(navyBlue, 1));

        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(new EmptyBorder(10, 0, 0, 0));
        add(scrollPane, BorderLayout.SOUTH);
    }

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
