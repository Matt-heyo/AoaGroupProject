package com.application.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainMenuView extends JPanel {

    public JButton fixedBtn;
    public JButton variableBtn;
    public JButton retiredBtn;
    public JButton maxExpenseBtn;

    private final Color navyBlue = new Color(10, 61, 98);
    private final Color hoverBlue = new Color(27, 95, 140);
    private final Color gold = new Color(218, 165, 32);
    private final Color lightBackground = new Color(244, 246, 249);

    public MainMenuView() {

    
        setLayout(new BorderLayout());
        setBackground(lightBackground);
        setBorder(new EmptyBorder(30, 40, 30, 40));

    
        JLabel title = new JLabel("Retirement Optimization Suite", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 26));
        title.setForeground(navyBlue);
        add(title, BorderLayout.NORTH);


       
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(new EmptyBorder(25, 25, 25, 25));
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setOpaque(true);


        JLabel menuLabel = new JLabel("Select a Module:");
        menuLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        menuLabel.setForeground(navyBlue);
        menuLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

      
        fixedBtn = createStyledButton("Fixed Growth Simulation");
        variableBtn = createStyledButton("Variable Growth Simulation");
        retiredBtn = createStyledButton("Retirement Duration Simulation");
        maxExpenseBtn = createStyledButton("Maximum Withdrawal");

       
        cardPanel.add(menuLabel);
        cardPanel.add(Box.createVerticalStrut(20));
        cardPanel.add(fixedBtn);
        cardPanel.add(Box.createVerticalStrut(15));
        cardPanel.add(variableBtn);
        cardPanel.add(Box.createVerticalStrut(15));
        cardPanel.add(retiredBtn);
        cardPanel.add(Box.createVerticalStrut(15));
        cardPanel.add(maxExpenseBtn);

        add(cardPanel, BorderLayout.CENTER);
    }


   
    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);

        btn.setFocusPainted(false);
        btn.setBackground(navyBlue);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 15));
        btn.setPreferredSize(new Dimension(350, 45));
        btn.setMaximumSize(new Dimension(350, 45));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);

        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(navyBlue, 1),
                new EmptyBorder(8, 16, 8, 16)
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
