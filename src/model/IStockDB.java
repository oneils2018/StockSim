package model;

/**
 * Used for creating a stock database. Stock database stores the information of a stock for any
 * given date in the past. Used for calculating the value of a portfolio on a previous date.
 */
public interface IStockDB {

  /**
   * Adds a stock to the stock database.
   *
   * @param stock stock object to be added and stored.
   */
  public void addStock(Stock stock);

  /**
   * Retreives a given stock based on the ticker value of said stock.
   *
   * @param tickerSymbol Symbol for desired stock.
   * @return Returns desired stock object.
   */
  public Stock getStock(String tickerSymbol);
}
