package model;

import java.util.Date;
import java.util.Map;

/**
 * StockModel is the main model to be used by the program. Can Create portfolios, retrieve
 * portfolios after creation. Can get total value of portfolios and validate portfolios/stocks.
 */
public interface IStockModel {

  /**
   * Generates a Portfolio Object.
   *
   * @param portfolioName desired name of portfolio
   * @param stocks        Map consisting of the ticker symbols and number of shares for all stocks.
   * @throws Exception throws if name is null or ""
   */
  void createPortfolio(String portfolioName, Map<String, Integer> stocks, Double commission)
      throws Exception;


  /**
   * Used for creation a modifiable portfolio.
   *
   * @param portfolioName name of desired portfolio
   * @param stocks        stocks to be added
   * @throws Exception if name is null or ""
   */
  void createFlexiblePortfolio(String portfolioName, Map<String, Integer> stocks, Double commission)
      throws Exception;

  /**
   * Retreives a given portfolio after creation.
   *
   * @param portfolioName name of desired portfolio
   * @return portfolio object
   * @throws Exception thrown if name is null or ""
   */
  IPortfolio getPortfolio(String portfolioName) throws Exception;

  /**
   * Validates if a given portfolio with the name currently exists in the StockModel object.
   *
   * @param portfolioName name of portfolio to validate
   * @return true or false based on if portfolio already exists (true if it does).
   */
  boolean isPortfolioPresent(String portfolioName);

  /**
   * Adds the desired portfolio if it is not already present in object.
   *
   * @param portfolio portfolio to be added.
   * @throws Exception thrown if portfolio is null.
   */
  void addPortfolioIfNotPresent(IPortfolio portfolio) throws Exception;

  /**
   * Gets the total value of a single portfolio on a given date.
   *
   * @param portfolioName name of portfolio to be used in calculation
   * @param date          date of prices to be retrieved
   * @return double value of the total price of given portfolio
   * @throws Exception thrown if name is null or ""
   */
  Double getTotalValue(String portfolioName, Date date) throws Exception;

  /**
   * Valiates if a given stock exists (valid ticker symbol).
   *
   * @param tickerSymbol ticker symbol to test
   * @return true or false (true if valid)
   * @throws Exception thrown if name is null or ""
   */
  boolean validateStock(String tickerSymbol, Date date) throws Exception;

  /**
   * Creates a performance graph of the desired portfolio.
   * @param portfolioName Name of portfolio to be examined.
   * @param start start of examination timeline.
   * @param end end of examination timeline
   * @return Map of tickers and their values
   * @throws Exception in case of null pointer.
   */
  Map<String, Double> generatePerformanceGraph(String portfolioName, Date start, Date end)
      throws Exception;

  Map<String, IPortfolio> getPortfoliosList();

  void investFixedAmountStrategy(String portfolioName, String strategyName, Double totalCost, Date date, Map<String, Double> stockWeights,
                                 double commission) throws Exception;

  void investDollarCostAverageStrategy(String portfolioName, String strategyName, Double totalCost, Date date,
                                       Date endDate, String intervalType, int interval,
                                       Map<String, Double> stockWeights, double commission) throws Exception;
}
