package com.application.model;

import java.util.ArrayList;
import java.util.List;

public class FixedInvestorModel {

    /*Calculates final accumulated balance using fixed annual compound interest.*/
    public double fixedInvestor(double principal, double rate, int years) {
        double balance = principal;

        for (int i = 0; i < years; i++) {
            balance = balance * (1 + rate);
        }

        return balance;
    }

    /* Generates a list of yearly balances.*/
    public List<Double> getYearlyBalances(double principal, double rate, int years) {
        List<Double> balances = new ArrayList<>();

        double balance = principal;
        for (int i = 0; i < years; i++) {
            balance = balance * (1 + rate);
            balances.add(balance);
        }

        return balances;
    }

    /* Classify markets based on rate strength.*/
    public List<String> classifyMarkets(double rate, int years) {
        List<String> labels = new ArrayList<>();

        String label;
        if (rate > 0.07)
            label = "Strong Bull";
        else if (rate > 0.03)
            label = "Moderate Growth";
        else if (rate > 0)
            label = "Weak Growth";
        else if (rate == 0)
            label = "Neutral";
        else
            label = "Recession";

        for (int i = 0; i < years; i++) {
            labels.add(label);
        }

        return labels;
    }
}
