package com.application.model;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class FinallyRetiredModel {

    private static final Logger logger = LoggerConfig.getLogger();

    /**
     * Simple result object to return all relevant information.
     */
    public static class Result {
        private final double finalBalance;
        private final List<Double> yearlyBalances;
        private final int earliestRetirementYearIndex; // 1-based; -1 if none

        public Result(double finalBalance, List<Double> yearlyBalances, int earliestRetirementYearIndex) {
            this.finalBalance = finalBalance;
            this.yearlyBalances = yearlyBalances;
            this.earliestRetirementYearIndex = earliestRetirementYearIndex;
        }

        public double getFinalBalance() {
            return finalBalance;
        }

        public List<Double> getYearlyBalances() {
            return yearlyBalances;
        }

        public int getEarliestRetirementYearIndex() {
            return earliestRetirementYearIndex;
        }
    }

    /**
     * Full calculation:
     *  - validates inputs
     *  - computes yearly balances (savings)
     *  - finds earliest retirement year index
     *
     * @param principal starting amount (must be > 0)
     * @param rateList  yearly rates (e.g. 0.05, 0.03, -0.02, ...)
     * @return Result object with final balance, yearly balances, and earliest year index
     * @throws Exception if invalid input
     */
    public Result calculate(double principal, List<Double> rateList) throws Exception {

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

        int earliestIndex = findEarliestRetirementYearIndex(balances);

        logger.info("FinallyRetired calculation successful. Final balance = " + balance
                + ", earliestRetirementYearIndex = " + earliestIndex);

        return new Result(balance, balances, earliestIndex);
    }

    /**
     * Implements the algorithm from your write-up:
     *  - Find the earliest i such that balance[i] > 0 and all balance[j] > 0 for j >= i.
     *
     * @param yearlyBalances list of balances after each year (treated as savings)
     * @return 1-based index of earliest safe year, or -1 if none
     */
    public int findEarliestRetirementYearIndex(List<Double> yearlyBalances) {

        if (yearlyBalances == null || yearlyBalances.isEmpty()) {
            return -1;
        }

        int n = yearlyBalances.size();

        for (int i = 0; i < n; i++) {

            if (yearlyBalances.get(i) <= 0.0) {
                continue;  // cannot retire at a year with non-positive savings
            }

            boolean retirePossible = true;

            for (int j = i; j < n; j++) {
                if (yearlyBalances.get(j) <= 0.0) {
                    retirePossible = false;
                    break;
                }
            }

            if (retirePossible) {
                return i + 1;  // convert 0-based index to 1-based year number
            }
        }

        return -1;  // no valid retirement year
    }
}
