package model;

import util.StockUtil;

import java.util.Date;
import java.util.Map;

/**
 * Portfolio events store either a buy or sell operation performed on an existing portfolio.
 */
public class PortfolioEvent implements IPortfolioEvent {
  private final boolean isPurchase;
  private final String creationDate;
  private final String tickerSymbol;
  private final int quantity;
  private final Double commission;

  /**
   * Constructor for creation of a portfolio event.
   * @param isPurchase if the event is a buy or sell
   * @param creationDate date of event
   * @param tickerSymbol name of stock in event
   * @param quantity quantity of event operation
   * @param commission fee for specified event
   */
  public PortfolioEvent(boolean isPurchase, String creationDate, String tickerSymbol, int quantity,
      Double commission) {
    this.isPurchase = isPurchase;
    this.creationDate = creationDate;
    this.tickerSymbol = tickerSymbol;
    this.quantity = quantity;
    this.commission = commission;
  }

  @Override
  public boolean isPurchase() {
    return isPurchase;
  }

  @Override
  public String getCreationDate() {
    return creationDate;
  }

  @Override
  public String getTickerSymbol() {
    return tickerSymbol;
  }

  @Override
  public int getQuantity() {
    return quantity;
  }

  @Override
  public Double getCommission() {
    return commission;
  }

  @Override
  public Double calculateCostBasis() {
    IStockDB stockDB = StockUtil.getStockDBInstance();
    IStockSource stockSource = StockUtil.getStockSourceInstance();
    Stock stock = stockDB.getStock(tickerSymbol);
    Date date = new Date(System.currentTimeMillis());
    if (stock == null) {
      try {
        stock = stockSource.getStock(tickerSymbol, date);
      } catch (Exception e) {
        return 0.0;
      }
      stockDB.addStock(stock);
    }

    Map.Entry<Date, Double> entry = stock.getPriceList().floorEntry(date);
    return entry.getValue() * quantity + commission;
  }
}
