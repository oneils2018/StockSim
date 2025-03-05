package view;

import model.IPortfolio;
import model.IStockModel;

import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * View used to give outputs. Will display desired messages for text based interface.
 */
public interface IStockView {
  void start();

  String getInitialInput();

  boolean inputCreatePortfolio(Set<String> portfolioNames);

  boolean inputExaminePortfolio(Set<String> portfolioNames);

  boolean inputTotalValue(Set<String> portfolioNames) throws Exception;

  boolean inputPersistPortfolio(Set<String> portfolioNames);

  boolean inputLoadPortfolio(Set<String> portfolioNames);

  boolean inputAddStockPortfolio(Map<String, IPortfolio> portfolioList) throws Exception;

  boolean inputSellStockPortfolio(Map<String, IPortfolio> portfolioList) throws Exception;

  boolean inputCostBasis(Map<String, IPortfolio> portfolioList) throws Exception;

  boolean inputPerformanceGraph(Map<String, IPortfolio> portfolioList) throws Exception;

  boolean inputFixedAmountStrategy(Map<String, IPortfolio> portfolioList) throws Exception;

  boolean inputDollarCostAverageStrategy(Map<String, IPortfolio> portfolioList) throws Exception;

  boolean isModify();

  Date getCreationDate();

  String getPortfolioName();

  Map<String, Integer> getStocks();

  double getCommission();

  Date getInputDate();

  String getTickerSymbol();

  int getQuantity();

  Date getStart();

  Date getEnd();

  String getStrategyName();

  double getTotalCost();

  Map<String, Double> getStockWeights();

  String getIntervalType();

  int getInterval();

  void displayTotalValue(String portfolioName, double price);

  void displayPortfolioInfo(IPortfolio portfolio);

  void display(String input);

  void displayCostBasis(Double cost, Date date);

  void displayPerformanceGraph(Map<String, Double> performanceGraph, String portfolioName,
                               String start, String end, Long base, Long asterisksValue);

  void displayPortfolioCreateComplete();

  boolean validateInput(String input);

  void setModel(IStockModel model);
}
