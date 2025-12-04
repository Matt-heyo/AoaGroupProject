package com.application.model;

import java.util.logging.Logger;

public class FinallyRetiredModel {

    private static final Logger logger = LoggerConfig.getLogger();

    /**
     * Computes how many years retirement funds will last.
     *
     * Logic:
     * - Each year balance grows by interest
     * - Then withdrawal is subtracted
     * - Loop ends when balance <= 0
     */
    public int calculate(double principal, double withdrawal, double rate) throws Exception {

        // Defensive programming
        if (principal <= 0)
            throw new IllegalArgumentException("Principal must be greater than zero.");

        if (withdrawal <= 0)
            throw new IllegalArgumentException("Withdrawal must be greater than zero.");

        if (rate < 0)
            throw new IllegalArgumentException("Rate cannot be negative.");

        int years = 0;
        double balance = principal;

        // Main simulation loop
        while (balance > 0) {
            balance = balance * (1 + rate);  // interest applied
            balance = balance - withdrawal;  // yearly withdrawal
            years++;

            if (years > 200) {
                logger.warning("Calculation exceeded 200 years — forcing exit.");
                break;
            }
        }

        logger.info("finallyRetired(): Funds lasted " + years + " years.");
        return years;
    }
}
