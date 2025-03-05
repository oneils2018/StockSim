package strategy;

import java.util.Date;
import java.util.Map;

public interface IInvestmentStrategy {
    String getStrategyName();

    double getTotalCost();

    Map<String, Double> getStockWeights();

    String getDate();

    String getEndDate();

    String getIntervalType();

    Integer getInterval();

    double getCommission();

    double calculateCostBasis();

    boolean checkIfStrategyStarted();

    int calculateNumberOfInvestments();

    Map<String, Double> currentStocks(Date date);
}
