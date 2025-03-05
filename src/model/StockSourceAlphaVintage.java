package model;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;

/**
 * Retreives a stocks information from AlphaVintage  API on a list of dates.
 */
public class StockSourceAlphaVintage implements IStockSource {

  private final String apiKey = "AQ20KT9AHNJVT0UM";
  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

  @Override
  public Stock getStock(String tickerSymbol, Date date) throws Exception {
    String stockEntries = getDataFromAPI(tickerSymbol);
    TreeMap<Date, Double> stockMap = getStockMap(stockEntries);
    if (stockMap.size() == 0) {
      throw new Exception("Stock Prices List not found.");
    }

    return new Stock(tickerSymbol, stockMap, date);
  }

  private String getDataFromAPI(String tickerSymbol) throws Exception {
    URL url;
    try {
      url = new URL("https://www.alphavantage"
          + ".co/query?function=TIME_SERIES_DAILY"
          + "&outputsize=full"
          + "&symbol"
          + "=" + tickerSymbol + "&apikey=" + apiKey + "&datatype=csv");
    } catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
          + "no longer works");
    }
    InputStream in = null;
    StringBuilder output = new StringBuilder();

    try {
      in = url.openStream();
      int b;

      while ((b = in.read()) != -1) {
        output.append((char) b);
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("No price data found for " + tickerSymbol);
    }

    return output.toString();
  }

  private TreeMap<Date, Double> getStockMap(String stockEntries) throws Exception {
    String[] stockEntriesList = stockEntries.split("\n");
    TreeMap<Date, Double> priceList = new TreeMap<>();

    boolean header = true;
    for (String stockEntry : stockEntriesList) {
      if (header) {
        header = false;
        continue;
      }

      //timestamp,open,high,low,close,volume
      String[] stockEntrySplit = stockEntry.split(",");
      if (stockEntrySplit.length < 6) {
        continue;
      }

      Double price = Double.parseDouble(stockEntrySplit[4]);
      Date date = sdf.parse(stockEntrySplit[0]);
      priceList.put(date, price);
    }
    return priceList;
  }
}
