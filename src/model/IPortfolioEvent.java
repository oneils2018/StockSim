package model;

/**
 * Interface to be used for creation Portfolio Events.
 * Portfolio Events are either buy or sell operations performed on an existing portfolio.
 */
public interface IPortfolioEvent {

  /**
   * Determines whether a given event is a purchase event.
   * @return True if it is a purchase
   */
  boolean isPurchase();

  /**
   * Get creation date of a given event.
   * @return Date as a string
   */
  String getCreationDate();

  /**
   * Get ticker symbol of specified event.
   * @return Name of stock as a string
   */
  String getTickerSymbol();

  /**
   * Get the quantity of stock either being sold or bought.
   * @return integer of quantity.
   */
  int getQuantity();

  /**
   * Get commission fee for specified event transaction.
   * @return Double of commission fee.
   */
  Double getCommission();

  /**
   * Get cost basis for the specified event.
   * @return Double of cost basis.
   */
  Double calculateCostBasis();
}
