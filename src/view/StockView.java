package view;

import model.FlexiblePortfolio;
import model.IPortfolio;
import model.IPortfolioEvent;
import model.IStockModel;
import util.DateUtil;
import util.StockUtil;

import java.io.PrintWriter;
import java.text.ParseException;
import java.util.*;

/**
 * Main View to give display to outputstream.
 */
public class StockView implements IStockView {

  private final Scanner scanner;
  private final PrintWriter out;

  /**
   * Generates two I/O streams.
   *
   * @param scanner input stream desired.
   * @param out     output stream desired.
   */
  public StockView(Scanner scanner, PrintWriter out) {
    this.out = out;
    this.scanner = scanner;
  }

  private boolean modify;
  private Date creationDate;
  private String portfolioName;
  private Map<String, Integer> stocks;
  private double commission;
  private Date inputDate;
  private String tickerSymbol;
  private int quantity;
  private Date start;
  private Date end;
  private String strategyName;
  private double totalCost;
  private Map<String, Double> stockWeights;
  private String intervalType;
  private int interval;

  public boolean isModify() {
    return modify;
  }

  public Date getCreationDate() {
    return creationDate;
  }

  public String getPortfolioName() {
    return portfolioName;
  }

  public Map<String, Integer> getStocks() {
    return stocks;
  }

  public double getCommission() {
    return commission;
  }

  public Date getInputDate() {
    return inputDate;
  }

  public String getTickerSymbol() {
    return tickerSymbol;
  }

  public int getQuantity() {
    return quantity;
  }

  public Date getStart() {
    return start;
  }

  public Date getEnd() {
    return end;
  }

  public String getStrategyName() {
    return strategyName;
  }

  public double getTotalCost() {
    return totalCost;
  }

  public Map<String, Double> getStockWeights() {
    return stockWeights;
  }

  public String getIntervalType() {
    return intervalType;
  }

  public int getInterval() {
    return interval;
  }

  @Override
  public void start() {
    String display = getUserDisplay();
    display(display);
  }

  @Override
  public String getInitialInput() {
    return getInput();
  }

  @Override
  public boolean inputCreatePortfolio(Set<String> portfolioNames) {
    creationDate = new Date();
    modify = inputModify();
    portfolioName = inputPortfolioName();
    if (portfolioName == null || portfolioName.equals("") || portfolioNames.contains(portfolioName)) {
      display("Name already used.\n\n");
      return false;
    }

    stocks = new HashMap<>();
    while (true) {
      try {
        String tickerSymbol = inputStockTickerSymbol();
        if (tickerSymbol.equals("0")) {
          break;
        }
        if (StockUtil.validateStock(tickerSymbol, creationDate)) {
          int quantity = inputStockQuantity();
          stocks.put(tickerSymbol, quantity);
        } else {
          display("Please enter a valid stock \n\n");
        }
      } catch (Exception e) {
        display("Please enter a valid stock \n\n");
      }
    }

    commission = calcaulateCommission();

    return true;
  }

  public boolean inputExaminePortfolio(Set<String> portfolioNames) {
    portfolioName = inputPortfolioName();
    if (portfolioName == null || portfolioName.equals("") || !portfolioNames.contains(portfolioName)) {
      display("Portfolio doesn't exist. Please enter a valid portfolio name. \n\n");
      return false;
    }

    return true;
  }

  public boolean inputTotalValue(Set<String> portfolioNames) throws Exception {
    portfolioName = inputPortfolioName();
    if (!portfolioNames.contains(portfolioName)) {
      display("Portfolio doesn't exist. Please enter a valid portfolio name. \n\n");
      return false;
    }
    inputDate = inputDate();

    return true;
  }

  public boolean inputPersistPortfolio(Set<String> portfolioNames) {
    portfolioName = inputPortfolioName();
    if (!portfolioNames.contains(portfolioName)) {
      display("Portfolio doesn't exist. Please enter a valid portfolio name. \n\n");
      return false;
    }

    return true;
  }

  public boolean inputLoadPortfolio(Set<String> portfolioNames) {
    portfolioName = inputPortfolioName();
    if (portfolioNames.contains(portfolioName)) {
      display("Portfolio exists already. \n\n");
      return false;
    }

    return true;
  }

  public boolean inputAddStockPortfolio(Map<String, IPortfolio> portfolioList) throws Exception {
    portfolioName = inputPortfolioName();
    if (!isFlexible(portfolioName, portfolioList)) {
      display("Portfolio cannot be modified\n");
      return false;
    }

    if (portfolioName == null || portfolioName.equals("")) {
      display("Enter valid name.\n\n");
      return false;
    }

    tickerSymbol = inputSingleStockTickerSymbol();
    quantity = inputStockQuantity();
    inputDate = inputDate();

    commission = calcaulateCommission();

    return true;
  }

  public boolean inputSellStockPortfolio(Map<String, IPortfolio> portfolioList) throws Exception {
    portfolioName = inputPortfolioName();
    if (!isFlexible(portfolioName, portfolioList)) {
      display("Portfolio cannot be modified\n");
      return false;
    }

    if (portfolioName == null || portfolioName.equals("")) {
      display("Enter valid name.\n\n");
      return false;
    }

    tickerSymbol = inputSingleStockTickerSymbol();
    quantity = inputStockQuantity();
    inputDate = inputDate();

    commission = calcaulateCommission();

    return true;
  }

  public boolean inputCostBasis(Map<String, IPortfolio> portfolioList) throws Exception {
    portfolioName = inputPortfolioName();
    if (!isFlexible(portfolioName, portfolioList)) {
      display("Simple portfolio does not support cost basis\n");
      return false;
    }
    inputDate = inputDate();

    return true;
  }

  public boolean inputPerformanceGraph(Map<String, IPortfolio> portfolioList) throws Exception {
    portfolioName = inputPortfolioName();

    if (!isFlexible(portfolioName, portfolioList)) {
      display("Simple portfolio does not support performance graph\n");
      return false;
    }
    start = inputStartDate();
    end = inputEndDate();

    if (start.after(end)) {
      display("Start Date should be before end Date.\n\n");
      return false;
    }

    return true;
  }

  public boolean inputFixedAmountStrategy(Map<String, IPortfolio> portfolioList) throws Exception {
    portfolioName = inputPortfolioName();
    if (!isFlexible(portfolioName, portfolioList)) {
      display("Simple portfolio cannot do investment strategy\n");
      return false;
    }
    strategyName = inputStrategyName();
    totalCost = inputTotalCost();

    stockWeights = new HashMap<>();
    double totalPercentage = 0.0;
    while (true) {
      try {
        String tickerSymbol = inputStockTickerSymbol();
        if (tickerSymbol.equals("0")) {
          break;
        }
        if (StockUtil.validateStock(tickerSymbol, creationDate)) {
          double quantity = inputStockWeight();
          totalPercentage += quantity;

          if(totalPercentage > 100.0) {
            display("Total weights have gone more than 100. Please recheck your strategy. \n\n");
            return false;
          }

          stockWeights.put(tickerSymbol, quantity);
        } else {
          display("Please enter a valid stock \n\n");
        }
      } catch (Exception e) {
        display("Please enter a valid stock \n\n");
      }
    }
    if(totalPercentage < 100.0) {
      display("Total weights are less than 100. Please recheck your strategy. \n\n");
      return false;
    }
    inputDate = inputDate();
    commission = calcaulateCommission();

    return true;
  }

  public boolean inputDollarCostAverageStrategy(Map<String, IPortfolio> portfolioList) throws Exception {
    portfolioName = inputPortfolioName();
    if (!isFlexible(portfolioName, portfolioList)) {
      display("Simple portfolio cannot do investment strategy\n");
      return false;
    }
    strategyName = inputStrategyName();
    totalCost = inputTotalCost();

    stockWeights = new HashMap<>();
    double totalPercentage = 0.0;
    while (true) {
      try {
        String tickerSymbol = inputStockTickerSymbol();
        if (tickerSymbol.equals("0")) {
          break;
        }
        if (StockUtil.validateStock(tickerSymbol, creationDate)) {
          double quantity = inputStockWeight();
          totalPercentage += quantity;

          if(totalPercentage > 100.0) {
            display("Total weights have gone more than 100. Please recheck your strategy. \n\n");
            return false;
          }

          stockWeights.put(tickerSymbol, quantity);
        } else {
          display("Please enter a valid stock \n\n");
        }
      } catch (Exception e) {
        display("Please enter a valid stock \n\n");
      }
    }
    if(totalPercentage < 100.0) {
      display("Total weights are less than 100. Please recheck your strategy. \n\n");
      return false;
    }
    start = inputStartDate();
    end = inputEndDate();
    intervalType = inputIntervalType();
    interval = inputInterval();
    commission = calcaulateCommission();

    return true;
  }

  private double calcaulateCommission() {
    if (inputIsCommission()) {
      return inputCommission();
    }
    return 0.0;
  }

  private boolean isFlexible(String portfolioName, Map<String, IPortfolio> portfolioList) {
    try {
      IPortfolio portfolio = portfolioList.get(portfolioName);
      return portfolio instanceof FlexiblePortfolio;
    } catch (Exception e) {
      display("Cannot find portfolio\n");
      return false;
    }
  }

  @Override
  public void display(String input) {
    out.print(input);
    out.flush();
  }

  private String getInput() {
    return scanner.nextLine();
  }

  @Override
  public boolean validateInput(String input) {
    int number = Integer.parseInt(input);
    if (number < 0 || number > 12) {
      display("Please enter a valid number from above\n\n");
      return false;
    }

    return true;
  }

  private String inputPortfolioName() {
    display("Please enter portfolio Name : \n");
    return getInput();
  }

  private String inputStockTickerSymbol() {
    display("Please enter Stock Ticker Symbol : \n");
    display("Please enter 0 when all stocks are entered.\n");
    return getInput();
  }

  private String inputSingleStockTickerSymbol() {
    display("Please enter Stock Ticker Symbol : \n");
    return getInput();
  }

  private Integer inputStockQuantity() {
    display("Please enter the quantity : \n");
    int input;
    try {
      input = Integer.parseInt(getInput());
      if (input <= 0) {
        display("Invalid Input : Please enter a valid positive number.\n");
        return inputStockQuantity();
      }
    } catch (Exception e) {
      display("Invalid Input : Please enter a valid positive number.\n");
      return inputStockQuantity();
    }

    return input;
  }

  //modified to validate date is in correct format
  private Date inputDate() throws ParseException {
    display("Enter the Date in this format (yyyy-MM-dd)");
    return getDate();
  }

  private Date inputStartDate() throws ParseException {
    display("Enter the Start Date in this format (yyyy-MM-dd)");
    return getDate();
  }

  private Date inputEndDate() throws ParseException {
    display("Enter the End Date in this format (yyyy-MM-dd)");
    return getDate();
  }

  private Date getDate() throws ParseException {
    String date = getInput();

    if (!DateUtil.validateDate(date)) {
      display("Please enter a valid date in this format (yyyy-MM-dd)");
      return getDate();
    }
    return DateUtil.convertDateStringToDate(date);
  }

  private Boolean inputModify() {
    display("Is this portfolio flexible (can it be modified after creation)? Y/N?");
    String s = getInput().toLowerCase();
    if (s.equals("y") || s.equals("yes")) {
      return true;
    } else if (s.equals("n") || s.equals("no")) {
      return false;
    } else {
      display("Invalid Input: Please enter Y/N\n");
      return inputModify();
    }

  }

  @Override
  public void displayPortfolioCreateComplete() {
    display("Portfolio created successfully. \n\n");
  }

  @Override
  public void displayPortfolioInfo(IPortfolio portfolio) {
    if (portfolio == null) {
      display("Porfolio not present \n\n");
      return;
    }

    Map<String, Double> stocks;
    if (portfolio instanceof FlexiblePortfolio) {
      stocks = ((FlexiblePortfolio) portfolio).loadCurrentStocks();
    } else {
      stocks = new HashMap<>();
      portfolio.getStocks().forEach((stock, quantity) -> stocks.put(stock, quantity * 1.0));
    }

    display("\nPortfolio Name - " + portfolio.getPortfolioName());
    display("\nPortfolio Creation Date - " + portfolio.getCreationDate());
    display("\nStock\t\tQuantity");
    for (String tickerSymbol : stocks.keySet()) {
      display("\n" + tickerSymbol + "\t\t" + String.format("%.2f", stocks.get(tickerSymbol)));
    }
    display("\n\n");

    if (portfolio instanceof FlexiblePortfolio) {
      List<IPortfolioEvent> portfolioEvents = ((FlexiblePortfolio) portfolio).getPortfolioEvents();

      display("Portfolio Events : ");
      display("\nEvent Type\t\tTickerSymbol\t\tQuantity\t\tCreationDate\t\tCommission");
      for (IPortfolioEvent portfolioEvent : portfolioEvents) {
        display("\n" + (portfolioEvent.isPurchase() ? "Purchase" : "Sell\t") + "\t\t"
            + portfolioEvent.getTickerSymbol() + "\t\t\t\t" + portfolioEvent.getQuantity()
            + "\t\t\t\t" + portfolioEvent.getCreationDate() + "\t\t\t"
            + portfolioEvent.getCommission());
      }
      display("\n\n");
    }
  }

  @Override
  public void displayTotalValue(String portfolioName, double price) {
    display("Total value for the " + portfolioName + " is " + price + ". \n\n");
  }

  private String getUserDisplay() {
    return "Please choose a number from the following.\n"
        + " 1. Create portfolio\n"
        + " 2. Examine portfolio\n"
        + " 3. Determine the total value of a portfolio on a certain date\n"
        + " 4. Persist a portfolio to file\n"
        + " 5. Load a portfolio from file\n"
        + " 6. Add a stock to a Flexible Portfolio\n"
        + " 7. Sell a stock from a Flexible Portfolio\n"
        + " 8. Get cost basis of a Flexible Portfolio\n"
        + " 9. Create portfolio performance graph\n"
        + "10. Create a Fixed Amount Strategy\n"
        + "11. Create a Dollar Cost Average Strategy\n"
        + " 0. Exit\n";
  }

  @Override
  public void displayCostBasis(Double cost, Date date) {
    display(
        "Cost basis on " + DateUtil.convertDateToDateString(date) + " is $" + String.format("%.2f",
            cost) + "\n\n");
  }

  private Boolean inputIsCommission() {
    display("Is commission involved? Y/N?");
    String s = getInput().toLowerCase();
    if (s.equals("y") || s.equals("yes")) {
      return true;
    } else if (s.equals("n") || s.equals("no")) {
      return false;
    } else {
      display("Invalid Input: Please enter Y/N\n");
      return inputIsCommission();
    }
  }

  private Double inputCommission() {
    display("Enter commission : ");
    String input = getInput();

    try {
      double num = Double.parseDouble(input);
      if (num < 0) {
        display("Please enter a commission greater than 0\n");
        num = inputCommission();
      }
      return num;
    } catch (Exception e) {
      display("Invalid Input: Please enter a double value\n");
      return inputCommission();
    }
  }

  @Override
  public void displayPerformanceGraph(Map<String, Double> performanceGraph, String portfolioName,
      String start, String end, Long base, Long asterisksValue) {
    display(String.format("\nPerformance of portfolio %s from %s to %s\n\n", portfolioName, start,
        end));

    List<String> dates = new ArrayList<>(performanceGraph.keySet());
    Collections.sort(dates);

    for (String date : dates) {
      display(String.format("%s: %s\n", date,
          getAstericks(performanceGraph.get(date), base, asterisksValue)));
    }
    display("\n\n");

    display("Scale: * = $" + asterisksValue + "\n");
    if (base != -1L) {
      display("Base: $" + base + "\n");
    }
  }

  private String getAstericks(Double value, Long base, Long asterisksValue) {
    StringBuilder sb = new StringBuilder();

    int asteriskCount = (int) Math.ceil(value / asterisksValue);
    if (base != -1L) {
      asteriskCount = (int) Math.ceil((value - base) / asterisksValue);
    }

    for (int i = 0; i < asteriskCount; i++) {
      sb.append("*");
    }

    return sb.toString();
  }

  private Double inputStockWeight() {
    display("Please enter the weight of stock (0-100) : \n");
    double input;
    try {
      input = Double.parseDouble(getInput());
      if (input <= 0 || input > 100) {
        display("Invalid Input : Please enter a valid number between 0 and 100. (0 excluded)\n");
        return inputStockWeight();
      }
    } catch (Exception e) {
      display("Invalid Input : Please enter a valid number between 0 and 100. (0 excluded)\n");
      return inputStockWeight();
    }

    return input;
  }

  private String inputStrategyName() {
    display("Please enter strategy Name : \n");
    String strategyName = getInput();
    if(strategyName == null || strategyName.equals("")) {
      display("Please enter a valid Name. \n");
      return inputStrategyName();
    }
    return strategyName;
  }

  private double inputTotalCost() {
    display("Please enter amount for the investment : \n");
    double input;
    try {
      return Double.parseDouble(getInput());
    } catch (Exception e) {
      display("Invalid Input : Please enter a valid number. \n");
      return inputTotalCost();
    }
  }

  private String inputIntervalType() {
    List<String> intervalTypeList = Arrays.asList("Daily", "Weekly", "Monthly", "Quarterly", "Yearly");
    Map<String, String> intervalTypeMap  = new HashMap<String, String>() {{
      put("D", "Daily");
      put("W", "Weekly");
      put("M", "Monthly");
      put("Q", "Quarterly");
      put("Y", "Yearly");
    }};
    display("Please enter Interval Type (Daily, Weekly, Monthly, Quarterly, Yearly): \n");
    String intervalType = getInput();
    if(intervalTypeList.contains(intervalType)) {
      return intervalType;
    } else if(intervalTypeMap.containsKey(intervalType)) {
      return intervalTypeMap.get(intervalType);
    } else {
      display("Please enter a valid IntervalType. \n");
      return inputIntervalType();
    }
  }

  private int inputInterval() {
    display("Please enter the interval : (Ex - 5 days) \n");
    int input;
    try {
      input = Integer.parseInt(getInput());
      if (input <= 0) {
        display("Invalid Input : Please enter a valid positive number.\n");
        return inputInterval();
      }
    } catch (Exception e) {
      display("Invalid Input : Please enter a valid positive number.\n");
      return inputInterval();
    }

    return input;
  }

  public void setModel(IStockModel model) {
  }
}
