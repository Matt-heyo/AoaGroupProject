package com.application.model;

import java.util.logging.Logger;

public class MaximumExpensedModel {
    private static final Logger logger = LoggerConfig.getLogger();


    public double findOptimalWithdrawal(double balance, double rate, int retirementYears) {
        if (balance <= 0) throw new IllegalArgumentException("Balance must be positive");

        double low = 0;
        double high = balance;  // Users can't withdraw more than total balance
        double bestWithdrawal = 0;

        // Binary search for optimal amount
        while (high - low > 0.01) {
            double withdrawal = (low + high) / 2;
            int yearsLasts = simulateYears(balance, withdrawal, rate);

            if (yearsLasts >= retirementYears) {
                bestWithdrawal = withdrawal;
                low = withdrawal;  // Try a higher withdrawal amount
            } else {
                high = withdrawal;  // Try a lower withdrawal amount
            }
        }

        return bestWithdrawal;
    }


    private int simulateYears(double balance, double withdrawal, double rate) {
        double current = balance;
        int years = 0;

        while (current > 0 && years < 100) {
            current = (current - withdrawal) * (1 + rate);
            years++;
        }

        return years;
    }


    public double calculateFinalBalance(double balance, double withdrawal, double rate, int years) {
        double current = balance;
        for (int i = 0; i < years; i++) {
            current = (current - withdrawal) * (1 + rate);
        }
        return current;
    }
}