package model;

import java.util.HashMap;
import java.util.Map;

/**
 * Database for storing stocks and their values in a map.
 */
public class StockDB implements IStockDB {
  private Map<String, Stock> stocksMap;

  /**
   * Initialized as an empty map.
   */
  public StockDB() {
    stocksMap = new HashMap<>();
  }

  @Override
  public void addStock(Stock stock) {
    if (stocksMap.containsKey(stock.getTickerSymbol())) {
      return;
    }

    stocksMap.put(stock.getTickerSymbol(), stock);
  }

  @Override
  public Stock getStock(String tickerSymbol) {
    return stocksMap.get(tickerSymbol);
  }
}
