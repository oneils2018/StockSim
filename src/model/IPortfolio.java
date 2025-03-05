package model;

import java.util.Date;
import java.util.Map;

/**
 * Interface used to implement two types of portfolios, inflexible(unmodifiable) and modifiable.
 */
public interface IPortfolio {

  /**
   * get name of portfolio.
   * @return name
   */
  String getPortfolioName();

  /**
   * Get map of stocks and their values.
   * @return map of stocks
   */
  Map<String, Integer> getStocks();

  /**
   * Get creation date of portfolio.
   * @return creation date as string
   */
  String getCreationDate();

  /**
   * Get total value of a portfolio on a specified date.
   * @param date date of which total value is being retrieved
   * @return double of total value
   * @throws Exception in case of null pointer
   */
  Double getTotalValue(Date date) throws Exception;

  /**
   * Get the commission fee of the specified portfolio.
   * @return double of portfolio commission fee
   */
  Double getCommission();

  /**
   * Creates a performance graph of a portfolio over a given time span.
   * @param portfolioName name of portfolio
   * @param start start of portfolio graph timeline
   * @param end end of portfolio graph timeline
   * @return Map of specified string and performance of a stock
   * @throws Exception in case of null pointer.
   */
  Map<String, Double> generatePerformanceGraph(String portfolioName, Date start, Date end)
      throws Exception;

  void investFixedAmountStrategy(String strategyName, Double totalCost, Date date, Map<String, Double> stockWeights, double commission)
          throws Exception;

  void investDollarCostAverageStrategy(String strategyName, Double totalCost, Date date, Date endDate,
                                       String intervalType, int interval, Map<String, Double> stockWeights,
                                       double commission) throws Exception;
}
