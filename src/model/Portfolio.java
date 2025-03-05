package model;

import util.DateUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Portfolio object to store given information such as name, stocks, and date of creation.
 */
public class Portfolio implements IPortfolio {

  private final String portfolioName;
  private final Map<String, Integer> stocks;
  private final String creationDate;
  private Double commission;

  protected final IStockDB stockDB = new StockDB();
  protected final IStockSource stockSource = new StockSourceAlphaVintage();

  /**
   * Will create an empty portfolio with a name.
   *
   * @param portfolioName name of portfolio (cannot be null)
   */
  public Portfolio(String portfolioName) {
    this.portfolioName = portfolioName;
    this.stocks = new HashMap<>();
    this.creationDate = DateUtil.convertDateToDateString(new Date(System.currentTimeMillis()));
    this.commission = 0.0;
  }

  /**
   * Will create a non empty portfolio with the current date.
   *
   * @param portfolioName name of portfolio
   * @param stocks        map of all stocks in format of (Stock Ticker, Num of stocks held).
   */
  public Portfolio(String portfolioName, Map<String, Integer> stocks, Double commission)
      throws IllegalArgumentException {

    if (commission < 0) {
      throw new IllegalArgumentException("Commission cannot be negative");
    }
    if (portfolioName == null || portfolioName.equals("")) {
      throw new IllegalArgumentException("Portfolio name cannot be empty");
    }
    this.portfolioName = portfolioName;
    this.stocks = stocks;
    this.creationDate = DateUtil.convertDateToDateString(new Date(System.currentTimeMillis()));
    this.commission = commission;
  }


  /**
   * Will create a non empty portfolio with a desired date.
   *
   * @param portfolioName portfolio name
   * @param stocks        Stock map (Stock Ticker, Num of stocks held)
   * @param creationDate  specified creation date (yyyy-MM-dd).
   */
  public Portfolio(String portfolioName, Map<String, Integer> stocks, String creationDate,
      Double commission) throws IllegalArgumentException {

    if (commission < 0) {
      throw new IllegalArgumentException("Commission cannot be negative");
    }
    if (portfolioName == null || portfolioName.equals("")) {
      throw new IllegalArgumentException("Name cannot be empty");
    }

    this.portfolioName = portfolioName;
    this.stocks = stocks;
    this.creationDate = creationDate;
    this.commission = commission;
  }

  @Override
  public String getPortfolioName() {
    return portfolioName;
  }

  @Override
  public Map<String, Integer> getStocks() {
    return stocks;
  }

  @Override
  public String getCreationDate() {
    return creationDate;
  }

  @Override
  public Double getCommission() {
    return commission;
  }

  @Override
  public Double getTotalValue(Date date) throws Exception {

    double totalValue = 0.0;
    for (String tickerSymbol : stocks.keySet()) {
      totalValue += (Double) this.getTotalValue(tickerSymbol, stocks.get(tickerSymbol), date);
    }

    return totalValue;
  }

  protected Double getTotalValue(String tickerSymbol, double quantity, Date date) throws Exception {
    Stock stock = stockDB.getStock(tickerSymbol);
    if (stock == null) {
      stock = stockSource.getStock(tickerSymbol, date);
      stockDB.addStock(stock);
    }
    Map.Entry<Date, Double> entry = stock.getPriceList().floorEntry(date);
    return entry.getValue() * quantity;
  }

  protected Map<String, Date> getDatesForGraph(Date start, Date end) {
    Map<String, Date> dates = new HashMap<>();
    long timeDiff = Math.abs(end.getTime() - start.getTime());
    long daysDiff = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);

    Date tempStart = start;
    // < 30 days
    if (daysDiff <= 30) {
      while (!tempStart.after(end)) {
        String formattedDateStr = DateUtil.convertDateToDateStringWithFormat(tempStart,
            DateUtil.DATE_FORMAT);
        dates.put(formattedDateStr, tempStart);
        tempStart = DateUtil.incrementOneDay(tempStart);
      }
    } else if (daysDiff <= 29 * 7) { // < 30 weeks includes start and end months
      tempStart = DateUtil.getNextFriday(tempStart);
      while (true) {
        String formattedDateStr = DateUtil.convertDateToDateStringWithFormat(tempStart,
            DateUtil.WEEK_FORMAT);
        dates.put(formattedDateStr, tempStart);
        if (tempStart.after(end)) {
          break;
        }
        tempStart = DateUtil.addDays(tempStart, 7);
      }
    } else if (daysDiff <= 29 * 30) { // < 30 months includes start and end months
      while (!tempStart.after(end) || DateUtil.isSameMonth(tempStart, end)) {
        Date lastDay = DateUtil.getLastDayOfMonth(tempStart);
        String formattedDateStr = DateUtil.convertDateToDateStringWithFormat(lastDay,
            DateUtil.MONTH_FORMAT);
        dates.put(formattedDateStr, lastDay);
        tempStart = DateUtil.incrementOneMonth(tempStart);
      }
    } else if (daysDiff <= 29 * 90) { // < 30 quarters includes start and end months
      while (true) {
        Date lastDay = DateUtil.getLastDayOfQuarter(tempStart);
        String formattedDateStr = DateUtil.convertDateToDateStringWithFormat(lastDay,
            DateUtil.QUARTER_FORMAT);
        dates.put(formattedDateStr, lastDay);
        if (tempStart.after(end)) {
          break;
        }
        tempStart = DateUtil.incrementOneQuarter(tempStart);
      }
    } else {
      while (!tempStart.after(end) || DateUtil.isSameYear(tempStart, end)) {
        Date lastDay = DateUtil.getLastDayOfYear(tempStart);
        String formattedDateStr = DateUtil.convertDateToDateStringWithFormat(tempStart,
            DateUtil.YEAR_FORMAT);
        dates.put(formattedDateStr, lastDay);
        tempStart = DateUtil.incrementOneYear(tempStart);
      }
    }
    return dates;
  }

  @Override
  public Map<String, Double> generatePerformanceGraph(String portfolioName, Date start, Date end)
      throws Exception {
    Map<String, Double> performanceGraph = new HashMap<>();
    Map<String, Date> dates = getDatesForGraph(start, end);

    for (String date : dates.keySet()) {
      performanceGraph.put(date, getTotalValue(dates.get(date)));
    }

    return performanceGraph;
  }

  @Override
  public void investFixedAmountStrategy(String strategyName, Double totalCost, Date date, Map<String, Double> stockWeights,
                                        double commission) throws Exception {
    throw new Exception("Simple Portfolio cannot perform strategies.");
  }

  @Override
  public void investDollarCostAverageStrategy(String strategyName, Double totalCost, Date date, Date endDate,
                                              String intervalType, int interval, Map<String, Double> stockWeights,
                                              double commission) throws Exception {
    throw new Exception("Simple Portfolio cannot perform strategies.");
  }
}
