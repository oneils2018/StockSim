package model;

import strategy.DollarCostAverageStrategy;
import strategy.FixedAmountStrategy;
import strategy.IInvestmentStrategy;
import util.DateUtil;

import java.util.*;

/**
 * FlexiblePortfolio is a modifiable portfolio that can either buy or sell stocks after creation.
 */
public class FlexiblePortfolio extends Portfolio {
  private List<IPortfolioEvent> portfolioEvents;
  private final Double initialCostBasis;
  private List<IInvestmentStrategy> portfolioStrategies;

  /**
   * Will create an empty portfolio with a name.
   *
   * @param portfolioName name of portfolio (cannot be null)
   */
  public FlexiblePortfolio(String portfolioName) throws Exception {
    super(portfolioName);
    this.portfolioEvents = new ArrayList<>();
    this.portfolioStrategies = new ArrayList<>();
    initialCostBasis =
        getTotalValue(DateUtil.convertDateStringToDate(getCreationDate())) + getCommission();
  }

  /**
   * Will create a non empty portfolio with a desired date.
   *
   * @param portfolioName portfolio name
   * @param stocks        Stock map (Stock Ticker, Num of stocks held)
   * @param commission    Initial commission.
   */
  public FlexiblePortfolio(String portfolioName, Map<String, Integer> stocks, Double commission)
      throws Exception {
    super(portfolioName, stocks, commission);
    this.portfolioEvents = new ArrayList<>();
    this.portfolioStrategies = new ArrayList<>();
    initialCostBasis =
        getTotalValue(DateUtil.convertDateStringToDate(getCreationDate())) + getCommission();
  }

  /**
   * Will create a non empty portfolio with a desired date.
   *
   * @param portfolioName portfolio name
   * @param stocks        Stock map (Stock Ticker, Num of stocks held)
   * @param creationDate  specified creation date (yyyy-MM-dd).
   * @param commission    Initial commission.
   */
  public FlexiblePortfolio(String portfolioName, Map<String, Integer> stocks, String creationDate,
      Double commission) throws Exception {
    super(portfolioName, stocks, creationDate, commission);
    this.portfolioEvents = new ArrayList<>();
    this.portfolioStrategies = new ArrayList<>();
    initialCostBasis =
        getTotalValue(DateUtil.convertDateStringToDate(creationDate)) + getCommission();
  }

  /**
   * Will create a non empty portfolio with a desired date.
   *
   * @param portfolioName   portfolio name
   * @param stocks          Stock map (Stock Ticker, Num of stocks held)
   * @param creationDate    specified creation date (yyyy-MM-dd).
   * @param commission      Initial commission.
   * @param portfolioEvents add or sell events (yyyy-MM-dd).
   */
  public FlexiblePortfolio(String portfolioName, Map<String, Integer> stocks, String creationDate,
      Double commission, Double initialCostBasis, List<IPortfolioEvent> portfolioEvents,
                           List<IInvestmentStrategy> portfolioStrategies) throws IllegalArgumentException {
    super(portfolioName, stocks, creationDate, commission);
    this.portfolioEvents = portfolioEvents;
    this.portfolioStrategies = portfolioStrategies;
    this.initialCostBasis = initialCostBasis;
    if (commission < 0) {
      throw new IllegalArgumentException("Comission must be greater than 0");
    }
  }

  /**
   * Get the list of past buys or sells.
   *
   * @return list of past events.
   */
  public List<IPortfolioEvent> getPortfolioEvents() {
    return portfolioEvents;
  }

  /**
   * Get the current cost basis of flexible portfolio.
   *
   * @return cost basis as a Double.
   */
  public Double getInitialCostBasis() {
    return initialCostBasis;
  }

  public List<IInvestmentStrategy> getPortfolioStrategies() {
    return portfolioStrategies;
  }

  /**
   * Add a stock to a preexisting portfolio.
   *
   * @param ticker     name of stock to be added
   * @param quantity   quantity of stock to be added
   * @param date       date of addition
   * @param commission commission fee for transaction
   */
  public void addStock(String ticker, int quantity, Date date, Double commission) {
    String dateStr = DateUtil.convertDateToDateString(date);
    PortfolioEvent purchaseEvent = new PortfolioEvent(true, dateStr, ticker, quantity,
        commission);

    portfolioEvents.add(purchaseEvent);
  }

  /**
   * Remove a stock from a preexisting portfolio.
   *
   * @param ticker     name of stock to be removed
   * @param quantity   quantity of removal
   * @param date       date of removal
   * @param commission commission fee for transaction
   * @throws Exception in case of null pointer
   */
  public void sellStock(String ticker, int quantity, Date date, Double commission)
      throws Exception {
    Map<String, Double> currStocks = loadCurrentStocks();
    if (!currStocks.containsKey(ticker)) {
      throw new Exception("Stock doesn't exist in Portfolio. So remove cannot be performed.");
    } else if (currStocks.get(ticker) < quantity) {
      throw new Exception(
          String.format("Only %d stocks are present for %s. So remove cannot be performed.",
              getStocks().get(ticker), ticker));
    }

    String dateStr = DateUtil.convertDateToDateString(date);
    PortfolioEvent sellEvent = new PortfolioEvent(false, dateStr, ticker, quantity, commission);

    portfolioEvents.add(sellEvent);
  }

  /**
   * Get cost basis of flexible portfolio on specified date.
   *
   * @param date date of cost basis analysis
   * @return Double of cost basis value
   * @throws Exception in case of null pointer.
   */
  public Double getCostBasis(Date date) throws Exception {
    if (DateUtil.convertDateStringToDate(getCreationDate()).after(date)) {
      throw new Exception("Please enter a date after the portfolio is created.");
    }

    Double totalCostBasis = initialCostBasis;
    totalCostBasis += portfolioEvents.stream()
        .filter(IPortfolioEvent::isPurchase)
        .filter(
            portfolioEvent -> !DateUtil.convertDateStringToDate(portfolioEvent.getCreationDate())
                .after(date))
        .mapToDouble(IPortfolioEvent::calculateCostBasis)
        .sum();

    totalCostBasis += portfolioStrategies.stream()
            .mapToDouble(IInvestmentStrategy::calculateCostBasis)
            .sum();

    return totalCostBasis;
  }


  /**
   * Get total value of a flexible portfolio on a specifided date.
   *
   * @param date date of which total value is being retrieved
   * @return value of portolio as Double
   * @throws Exception in case of null pointer
   */
  @Override
  public Double getTotalValue(Date date) throws Exception {
    if (DateUtil.convertDateStringToDate(getCreationDate()).after(date)) {
      return 0.0;
    }

    double totalValue = super.getTotalValue(date);
    totalValue += portfolioEvents.stream()
        .filter(
            portfolioEvent -> !DateUtil.convertDateStringToDate(portfolioEvent.getCreationDate())
                .after(date))
        .mapToDouble(portfolioEvent -> {
          try {
            return getTotalValue(portfolioEvent.getTickerSymbol(), portfolioEvent.getQuantity(),
                date) * (portfolioEvent.isPurchase() ? 1 : -1);
          } catch (Exception e) {
            return 0.0;
          }
        })
        .sum();

    totalValue += portfolioStrategies.stream()
            .filter(IInvestmentStrategy::checkIfStrategyStarted)
            .mapToDouble(portfolioStrategy -> {
              double total = 0.0;
              Map<String, Double> currentStocks =  portfolioStrategy.currentStocks(date);
              for(String tickerSymbol: currentStocks.keySet()) {
                try {
                  total += getTotalValue(tickerSymbol, currentStocks.get(tickerSymbol), date);
                } catch (Exception ignored) { }
              }
              return total;
            })
            .sum();

    return totalValue;
  }

  /**
   * Gets current stock from memory.
   *
   * @return MAP of stock ticker and quantity.
   */
  public Map<String, Double> loadCurrentStocks() {
    Date date = new Date(System.currentTimeMillis());
    Map<String, Double> currStocks = new HashMap<>();
    getStocks().forEach((stock, quantity) -> currStocks.put(stock, quantity * 1.0));
    portfolioEvents.stream()
        .filter(
            portfolioEvent -> !DateUtil.convertDateStringToDate(portfolioEvent.getCreationDate())
                .after(date))
        .forEach(portfolioEvent -> {
          String tickerSymbol = portfolioEvent.getTickerSymbol();
          int quantity = portfolioEvent.getQuantity();
          currStocks.put(tickerSymbol, currStocks.getOrDefault(tickerSymbol, 0.0)
              + quantity * (portfolioEvent.isPurchase() ? 1 : -1));
        });

    portfolioStrategies.stream()
            .filter(IInvestmentStrategy::checkIfStrategyStarted)
            .forEach(portfolioStrategy -> {
              for(String tickerSymbol: portfolioStrategy.currentStocks(date).keySet()) {
                currStocks.put(tickerSymbol, currStocks.getOrDefault(tickerSymbol, 0.0)
                        + portfolioStrategy.currentStocks(date).get(tickerSymbol));
              }
            });

    return currStocks;
  }

  public void investFixedAmountStrategy(String strategyName, Double totalCost, Date date, Map<String, Double> stockWeights,
                                        double commission) throws Exception {
    portfolioStrategies.add(new FixedAmountStrategy(strategyName, totalCost, stockWeights, DateUtil.convertDateToDateString(date), commission));
  }

  public void investDollarCostAverageStrategy(String strategyName, Double totalCost, Date date, Date endDate,
                                              String intervalType, int interval, Map<String, Double> stockWeights,
                                              double commission) throws Exception {
    portfolioStrategies.add(new DollarCostAverageStrategy(strategyName, totalCost, stockWeights,
            DateUtil.convertDateToDateString(date), DateUtil.convertDateToDateString(endDate),
            intervalType, interval, commission));
  }
}


