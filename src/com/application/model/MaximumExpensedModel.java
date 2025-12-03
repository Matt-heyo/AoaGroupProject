package com.application.model;

import java.util.logging.Logger;

public class MaximumExpensedModel {
    private static final Logger logger = LoggerConfig.getLogger();

    public double findOptimalWithdrawal(double balance, double rate, int retirementYears) {
        double low = 0;
        double high = balance;
        double bestWithdrawal = 0;

        // Binary search for optimal amount
        for (int i = 0; i < 100; i++) {
            double withdrawal = (low + high) / 2;
            int yearsLasts = simulateYears(balance, withdrawal, rate);

            if (yearsLasts >= retirementYears) {
                bestWithdrawal = withdrawal;
                low = withdrawal;  // Try a higher withdrawal amount
            } else {
                high = withdrawal;  // Try a lower withdrawal amount
            }

            if (Math.abs(yearsLasts - retirementYears) <= 1) {
                break;
            }
        }

        logger.info("Optimal withdrawal amount calculated: $" + bestWithdrawal);
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

    public String getYearByYearBreakdown(double balance, double withdrawal, double rate, int years) {
        StringBuilder breakdown = new StringBuilder();
        breakdown.append("YEAR-BY-YEAR BREAKDOWN:\n");
        breakdown.append(String.format("%-10s %-15s %-15s %-15s%n",
                "Year", "Start Balance", "Withdrawal", "End Balance"));

        double current = balance;
        for (int year = 1; year <= years && current > 0; year++) {
            double start = current;
            double afterWithdrawal = current - withdrawal;
            double end = afterWithdrawal * (1 + rate);

            breakdown.append(String.format("%-10d $%-14.2f $%-14.2f $%-14.2f%n",
                    year, start, withdrawal, Math.max(end, 0)));

            current = end;
        }

        return breakdown.toString();
    }

    public double calculateFinalBalance(double balance, double withdrawal, double rate, int years) {
        double current = balance;
        for (int i = 0; i < years; i++) {
            current = (current - withdrawal) * (1 + rate);
        }
        return Math.max(current, 0);
    }
}