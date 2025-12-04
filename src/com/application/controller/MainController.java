package com.application.controller;

import com.application.model.MaximumExpensedModel;
import com.application.model.VariableInvestorModel;
import com.application.view.MainMenuView;
import com.application.view.MaximumExpensedView;
import com.application.view.VariableInvestorView;

import javax.swing.*;
import java.awt.*;

public class MainController {

    private JFrame frame;
    private JPanel cards;

    public MainController() {
        frame = new JFrame("AOA Retirement Optimizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);

        cards = new JPanel(new CardLayout());

        // Views
        MainMenuView menuView = new MainMenuView();
        VariableInvestorView variableView = new VariableInvestorView();
        MaximumExpensedView maxExpenseView = new MaximumExpensedView();

        // Controllers
        new VariableInvestorController(
                variableView,
                new VariableInvestorModel(),
                () -> showCard("menu")
        );

        new MaximumExpensedController(
                maxExpenseView,
                new MaximumExpensedModel(),
                () -> showCard("menu")
        );

        // Add views to cards
        cards.add(menuView, "menu");
        cards.add(variableView, "variable");
        cards.add(maxExpenseView, "maxExpense");

        // Menu listeners
        menuView.variableBtn.addActionListener(e -> showCard("variable"));
        menuView.maxExpenseBtn.addActionListener(e -> showCard("maxExpense"));

        frame.add(cards);
        frame.setVisible(true);

        showCard("menu");
    }

    private void showCard(String name) {
        CardLayout layout = (CardLayout) cards.getLayout();
        layout.show(cards, name);
    }
}
