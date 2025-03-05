package model;

import util.StockUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Main model to be used for implementation. Can create portfolios, validate inputs, and handle file
 * I/O.
 */
public class StockModel implements IStockModel {
  //modified to take type IPortfolio
  private final Map<String, IPortfolio> portfolios;
  private final IStockSource stockSource;
  private final IStockDB stockDB;

  /**
   * Creates stock model object.
   *
   * @param stockSource source of stock values (from API)
   * @param stockDB     database of all stocks with data stored
   */
  public StockModel(IStockSource stockSource, IStockDB stockDB) {
    portfolios = new HashMap<>();
    this.stockSource = stockSource;
    this.stockDB = stockDB;
    StockUtil.setStockDBInstance(stockDB);
    StockUtil.setStockSourceInstance(stockSource);
  }

  public StockModel() {
    portfolios = new HashMap<>();
    this.stockSource = StockUtil.getStockSourceInstance();
    this.stockDB = StockUtil.getStockDBInstance();
  }

  public Map<String, IPortfolio> getPortfoliosList() {
    return portfolios;
  }

  /**
   * Will add a portfolio to the stock model if it does not already exist in said object.
   *
   * @param portfolio portfolio to be added.
   * @throws Exception if portfolio does not exist
   */
  public void addPortfolioIfNotPresent(IPortfolio portfolio) throws Exception {
    if (portfolio == null) {
      throw new Exception("Portfolio cannot be null.");
    }

    if (!portfolios.containsKey(portfolio.getPortfolioName())) {
      portfolios.put(portfolio.getPortfolioName(), portfolio);
    }
  }

  @Override
  public boolean isPortfolioPresent(String portfolioName) {
    if (portfolioName == null || portfolioName.equals("")) {
      return false;
    }

    return portfolios.containsKey(portfolioName);
  }

  @Override
  public void createPortfolio(String portfolioName, Map<String, Integer> stocks, Double commission)
      throws Exception {
    if (portfolioName == null || portfolioName.equals("")) {
      throw new Exception("Portfolio cannot be null or empty.");
    }

    if (isPortfolioPresent(portfolioName)) {
      throw new Exception("Name already used.");
    }

    if (stocks.size() == 0) {
      throw new Exception("Please add atleast one stock in the portfolio.");
    }

    portfolios.put(portfolioName, new Portfolio(portfolioName, stocks, commission));
  }

  //new
  @Override
  public void createFlexiblePortfolio(String portfolioName, Map<String, Integer> stocks,
      Double commission)
      throws Exception {
    if (portfolioName == null || portfolioName.equals("")) {
      throw new Exception("Portfolio cannot be null or empty.");
    }

    if (isPortfolioPresent(portfolioName)) {
      throw new Exception("Name already used.");
    }

    if (stocks.size() == 0) {
      throw new Exception("Please add at least one stock in the portfolio.");
    }

    portfolios.put(portfolioName, new FlexiblePortfolio(portfolioName, stocks, commission));
  }

  @Override
  public IPortfolio getPortfolio(String portfolioName) throws Exception {
    if (!portfolios.containsKey(portfolioName)) {
      throw new Exception("Portfolio not found.");
    }
    return portfolios.get(portfolioName);
  }

  @Override
  public Double getTotalValue(String portfolioName, Date date) throws Exception {
    IPortfolio portfolio = getPortfolio(portfolioName);
    double result = portfolio.getTotalValue(date);
    return result;
  }

  @Override
  public boolean validateStock(String tickerSymbol, Date date) throws Exception {
    Stock stock = stockDB.getStock(tickerSymbol);
    if (stock == null) {
      stock = stockSource.getStock(tickerSymbol, date);
      stockDB.addStock(stock);
    }
    System.out.println(stock != null);
    System.out.println(stock.getPriceList().size() > 0);
    return stock != null && stock.getPriceList().size() > 0;
  }

  @Override
  public Map<String, Double> generatePerformanceGraph(String portfolioName, Date start, Date end)
      throws Exception {
    IPortfolio portfolio = getPortfolio(portfolioName);
    return portfolio.generatePerformanceGraph(portfolioName, start, end);
  }

  @Override
  public void investFixedAmountStrategy(String portfolioName, String strategyName, Double totalCost, Date date, Map<String, Double> stockWeights,
                                        double commission) throws Exception {
    IPortfolio portfolio = getPortfolio(portfolioName);
    portfolio.investFixedAmountStrategy(strategyName, totalCost, date, stockWeights, commission);
  }

  @Override
  public void investDollarCostAverageStrategy(String portfolioName, String strategyName, Double totalCost, Date date, Date endDate,
                                              String intervalType, int interval, Map<String, Double> stockWeights,
                                              double commission) throws Exception {
    IPortfolio portfolio = getPortfolio(portfolioName);
    portfolio.investDollarCostAverageStrategy(strategyName, totalCost, date, endDate, intervalType, interval,
            stockWeights, commission);
  }
}
