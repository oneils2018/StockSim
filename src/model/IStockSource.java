package model;

import java.util.Date;

/**
 * Used to retrieve data from given API.
 */
public interface IStockSource {

  /**
   * Retrieves a given stock from the AlphaVantage API.
   *
   * @param tickerSymbol symbol for stock
   * @return stock object.
   * @throws Exception thrown if name is null or ""
   */
  Stock getStock(String tickerSymbol, Date date) throws Exception;
}
