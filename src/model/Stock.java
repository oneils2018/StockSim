package model;

import java.util.Date;
import java.util.TreeMap;

/**
 * Stock object to store ticker symbols and the price lists of certain stocks.
 */
public class Stock {

  private String tickerSymbol;
  private TreeMap<Date, Double> priceList;
  private Date creationDate;

  /**
   * Generates a stock object.
   *
   * @param tickerSymbol ticker symbol of desired stock
   * @param priceList    list of all prices on given dates retrieved by API.
   */
  public Stock(String tickerSymbol, TreeMap<Date, Double> priceList, Date creationDate) {
    this.tickerSymbol = tickerSymbol;
    this.priceList = priceList;
    this.creationDate = creationDate;
  }

  public String getTickerSymbol() {
    return tickerSymbol;
  }

  public TreeMap<Date, Double> getPriceList() {
    return priceList;
  }

  //modified to add addition date for stocks being added
  public Date getCreationDate() {
    return creationDate;
  }
}
