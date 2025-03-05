package controller;

import java.util.Date;
import java.util.Map;

/**
 * Interface used for the Implementation in StockController. It contains main features of the
 * application.
 */
public interface IFeatures {

  /**
   * Creates a portfolio.
   *
   * @param portfolioName Name of portfolio to be stored
   * @param stocks        Stocks to be added to portfolio
   * @param commission    Commission for initial portfolio
   */
  void createPortfolio(String portfolioName, Map<String, Integer> stocks, Double commission);

  /**
   * Creates a modifiable portfolio.
   *
   * @param portfolioName name of portfolio to be made
   * @param stocks        stocks and their purchase quantities
   * @param commission    Commission for initial portfolio
   */
  void createFlexiblePortfolio(String portfolioName, Map<String,
      Integer> stocks, Double commission);

  /**
   * Retrieves a portfolio.
   *
   * @param portfolioName name of model to be retrieved
   */
  void getPortfolio(String portfolioName);

  /**
   * Gets the total value of the desired portfolio on a given date.
   *
   * @param portfolioName Name of desired portfolio
   * @param date          date to be used for calculation(yyyy-MM-dd)
   */
  void getTotalValue(String portfolioName, Date date);

  /**
   * Saves the portfolio using FileStorage class in data directory.
   *
   * @param portfolioName name of portfolio to be saved
   * @param fileName      desired name of file for portfolio (can be different than portfolio name)
   */
  void savePortfolio(String portfolioName, String fileName);

  /**
   * Retrieves and loads a portfolio from a file.
   *
   * @param fileName name of file to be added (give path)
   */
  void getPortfolioFromFile(String fileName);

  /**
   * Initial loop that will run until 0 is entered as a command (EXIT).
   */
  void start() throws Exception;

  /**
   * Adds stock to existing flexible portfolio.
   * @param portfolioName name of portfolio
   * @param stockName name of stock to be added
   * @param num quantity of stock to be added
   * @param date date of addition
   * @throws Exception thrown incase of null pointer
   */
  void addStock(String portfolioName, String stockName, int num, Date date) throws Exception;

  /**
   * Add a stock including commission to a portfolio.
   * @param portfolioName name of the portfolio
   * @param stockName name of stock to be added
   * @param num quantity of stock to be added
   * @param date date of addition
   * @param commission comission fee of transaction
   * @throws Exception in case of null pointer
   */
  void addStockWithCommission(String portfolioName, String stockName, int num, Date date,
      Double commission) throws Exception;

  /**
   * Sells a stock from a flexible portfolio.
   * @param portfolioName name of portfolio
   * @param stockName name of stock to be sold
   * @param num quantity of sell
   * @param date date of sell
   * @throws Exception in case of null pointer
   */
  void sellStock(String portfolioName, String stockName, int num, Date date) throws Exception;

  /**
   * Sells a stock using a commission fee.
   * @param portfolioName name of portfolio
   * @param stockName name of stock to be sold
   * @param num quantity of sell
   * @param date date of sell
   * @param commission fee for transaction
   * @throws Exception in case of null pointer
   */
  void sellStockWithCommission(String portfolioName, String stockName, int num, Date date,
      Double commission) throws Exception;

  /**
   * Gets total cost basis of provided portfolio.
   * @param portfolioName name of portfolio
   * @param date date of cost basis
   * @throws Exception in case of null pointer
   */
  void getCostBasis(String portfolioName, Date date);

  /**
   * Creates a performance chart for a given portfolio.
   * @param portfolioName name of portfolio
   * @param start start date of graph
   * @param end end date of graph
   * @throws Exception in case of null pointer
   */
  void generatePerformanceGraph(String portfolioName, Date start, Date end);

  void investFixedAmountStrategy(String portfolioName, String strategyName, double totalCost, Date date, Map<String, Double> stockWeights,
                                 double commission);

  void investDollarCostAverageStrategy(String portfolioName, String strategyName, double totalCost, Date start,
                                       Date end, String intervalType, int interval,
                                       Map<String, Double> stockWeights, double commission) throws Exception;
}