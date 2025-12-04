package com.application.controller;

import com.application.model.FinallyRetiredModel;
import com.application.model.VariableInvestorModel;          // NEW
import com.application.view.FinallyRetiredView;
import com.application.view.MainMenuView;
import com.application.view.VariableInvestorView;            // NEW
import java.awt.*;
import javax.swing.*;

public class MainController {

    private JFrame frame;
    private JPanel cards;

    public MainController() {
        frame = new JFrame("AOA Retirement Optimizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null);

        // Card container
        cards = new JPanel(new CardLayout());

        // -----------------------------------------
        // VIEWS
        // -----------------------------------------
        MainMenuView menuView = new MainMenuView();
        VariableInvestorView variableView = new VariableInvestorView();
        FinallyRetiredView finallyRetiredView = new FinallyRetiredView();     

        // -----------------------------------------
        // MODELS
        // -----------------------------------------
        VariableInvestorModel variableModel = new VariableInvestorModel();
        FinallyRetiredModel finallyRetiredModel = new FinallyRetiredModel(); 

        // -----------------------------------------
        // CONTROLLERS
        // -----------------------------------------
        // Variable Investor
        new VariableInvestorController(
                variableView,
                variableModel,
                () -> showCard("menu")
        );

        // Finally Retired  
       new FinallyRetiredController(
        finallyRetiredView,
        finallyRetiredModel,
        variableModel,          // so it can reuse market classifications + report window
        () -> showCard("menu")
);

        // -----------------------------------------
        // ADD CARDS
        // -----------------------------------------
        cards.add(menuView, "menu");
        cards.add(variableView, "variable");
        cards.add(finallyRetiredView, "finallyRetired");  
        // -----------------------------------------
        // MENU BUTTON LISTENERS
        // -----------------------------------------
        // Existing: goes to Variable Investor screen
        menuView.variableBtn.addActionListener(e -> showCard("variable"));

        // NEW: goes to Finally Retired screen
        menuView.retiredBtn.addActionListener(e -> showCard("finallyRetired"));


        // -----------------------------------------
        // FINAL FRAME SETUP
        // -----------------------------------------
        frame.add(cards);
        frame.setVisible(true);

        showCard("menu");
    }

    private void showCard(String name) {
        CardLayout layout = (CardLayout) cards.getLayout();
        layout.show(cards, name);
    }
}
