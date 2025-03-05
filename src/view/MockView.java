//package view;
//
//import java.text.ParseException;
//import java.util.Date;
//import java.util.Map;
//import java.util.Scanner;
//import model.IPortfolio;
//
///**
// * MOCK of View to be used ONLY in ControllerTest.
// */
//public class MockView implements IStockView {
//
//  private String log;
//  int uniquecode;
//
//  /**
//   * Creates a log and code for mockview.
//   *
//   * @param log        string of commands performed.
//   * @param uniquecode identifier.
//   */
//  public MockView(String log, int uniquecode) {
//    this.log = log;
//    this.uniquecode = uniquecode;
//  }
//
//  @Override
//  public void display(String input) {
//    this.log = log.concat(input);
//  }
//
//  @Override
//  public void display() {
//
//  }
//
//  @Override
//  public String getUserDisplay() {
//    return "USER DISPLAY STAND-IN";
//  }
//
//  @Override
//  public String getInput() {
//    Scanner input = new Scanner(System.in);
//
//    if (!input.hasNext()) {
//      return "0";
//    }
//    return input.nextLine();
//  }
//
//  @Override
//  public boolean validateInput(String input) {
//    return true;
//  }
//
//  @Override
//  public String inputPortfolioName() {
//    log = log.concat("Input Name Call");
//    return "port1";
//  }
//
//  @Override
//  public String inputStockTickerSymbol() {
//    log = log.concat("Input Stock Call");
//    if (log.length() % 2 == 0) {
//      return "0";
//    }
//    return "GOOG";
//  }
//
//  @Override
//  public Integer inputStockQuantity() {
//    return 1;
//  }
//
//  @Override
//  public Date inputDate() {
//    return new Date(0, 0, 0);
//  }
//
//  @Override
//  public Boolean inputModify() {
//    return null;
//  }
//
//  @Override
//  public void displayPortfolioCreateComplete() {
//    log = log.concat("PORT DISP");
//  }
//
//  @Override
//  public void displayPortfolioInfo(IPortfolio portfolio) {
//    log = log.concat("PORTDISPINFO");
//  }
//
//
//  @Override
//  public void displayTotalValue(String portfolioName, double price) {
//    log = log.concat("DISP TOT VAL");
//  }
//
//  @Override
//  public void displayCostBasis(Double cost, Date date) {
//    log = log.concat("COST BASIS DISP");
//  }
//
//  @Override
//  public Boolean inputIsCommission() {
//    return false;
//  }
//
//  @Override
//  public Double inputCommission() {
//    return 1.0;
//  }
//
//  @Override
//  public Date inputStartDate() throws ParseException {
//    return null;
//  }
//
//  @Override
//  public Date inputEndDate() throws ParseException {
//    return null;
//  }
//
//  @Override
//  public void displayPerformanceGraph(Map<String, Double> performanceGraph, String portfolioName,
//      String start, String end, Long base, Long asterisksValue) {
//    log = log.concat("DISP PERFORMANCE GRAPH");
//  }
//
//  public String getLog() {
//    return this.log;
//  }
//}
