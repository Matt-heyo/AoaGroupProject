package com.application.model;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class VariableInvestorModel {

    private static final Logger logger = LoggerConfig.getLogger();

    /**
     * Core calculation: final balance after applying all rates.
     */
    public double calculate(double principal, List<Double> rateList) throws Exception {

        if (principal <= 0)
            throw new IllegalArgumentException("Principal must be greater than 0.");

        if (rateList == null || rateList.isEmpty())
            throw new IllegalArgumentException("Rate list cannot be empty.");

        double balance = principal;

        for (double rate : rateList) {
            balance *= (1 + rate);
        }

        logger.info("VariableInvestor calculation successful: Final balance = " + balance);
        return balance;
    }

    /**
     * Returns the balance after each year (used for chart + report).
     */
    public List<Double> calculateYearlyBalances(double principal, List<Double> rateList) throws Exception {

        if (principal <= 0)
            throw new IllegalArgumentException("Principal must be greater than 0.");

        if (rateList == null || rateList.isEmpty())
            throw new IllegalArgumentException("Rate list cannot be empty.");

        List<Double> balances = new ArrayList<>();
        double balance = principal;

        for (double rate : rateList) {
            balance *= (1 + rate);
            balances.add(balance);
        }

        logger.info("Yearly balances computed for " + rateList.size() + " years.");
        return balances;
    }

    /**
     * Classify market condition based on rate.
     * >  0.05  → Bull Market
     * 0.01–0.05 → Growth
     * -0.01–0.01 → Stable
     * -0.05–-0.01 → Correction
     * < -0.05 → Crash
     */
    public String classifyMarket(double rate) {
        if (rate > 0.05) return "Bull Market";
        if (rate >= 0.01) return "Growth";
        if (rate > -0.01) return "Stable";
        if (rate >= -0.05) return "Correction";
        return "Crash";
    }

    /**
     * Map list of rates to list of market condition labels.
     */
    public List<String> classifyAllMarkets(List<Double> rateList) {
        List<String> labels = new ArrayList<>();
        for (double r : rateList) {
            labels.add(classifyMarket(r));
        }
        return labels;
    }
}
