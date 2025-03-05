//package model;
//
//import java.util.Date;
//import java.util.Map;
//
///**
// * | MOCK StockModel to be used ONLY for testing the controller in ControllerTest.
// */
//public class MockModel implements IStockModel {
//
//  String log;
//  int id;
//
//  /**
//   * Creates mockmodel to store outputs.
//   *
//   * @param log commands that were performed.
//   * @param id  mockmodel identifier.
//   */
//  public MockModel(String log, int id) {
//    this.log = log;
//    this.id = id;
//  }
//
//  @Override
//  public void createPortfolio(String portfolioName, Map<String, Integer> stocks, Double commission)
//      throws Exception {
//    log = log.concat("CREATING REGULAR PORTFOLIO");
//  }
//
//  @Override
//  public void createFlexiblePortfolio(String portfolioName, Map<String, Integer> stocks,
//      Double commission) throws Exception {
//    log = log.concat("CREATING FLEXIBLE PORTFOLIO");
//
//  }
//
//
//  @Override
//  public Portfolio getPortfolio(String portfolioName) throws Exception {
//    log = log.concat("GOT PORTFOLIO");
//    return null;
//  }
//
//  @Override
//  public boolean isPortfolioPresent(String portfolioName) {
//    return false;
//  }
//
//  @Override
//  public void addPortfolioIfNotPresent(IPortfolio portfolio) throws Exception {
//  //method not needed for mocking of model behavior
//  }
//
//  @Override
//  public Double getTotalValue(String portfolioName, Date date) throws Exception {
//    return 1.00;
//  }
//
//  @Override
//  public boolean validateStock(String tickerSymbol, Date date) throws Exception {
//    return false;
//  }
//
//  @Override
//  public Map<String, Double> generatePerformanceGraph(String portfolioName, Date start, Date end)
//      throws Exception {
//    return null;
//  }
//
//
//  public String getLog() {
//    return log;
//  }
//}
