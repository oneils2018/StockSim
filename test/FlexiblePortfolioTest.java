import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import model.FlexiblePortfolio;
import model.IStockSource;
import model.Stock;
import model.StockSourceAlphaVintage;
import org.junit.Before;
import org.junit.Test;
import util.DateUtil;


/**
 * Tests the implementation of a flexible portfolios.
 */
public class FlexiblePortfolioTest {

  @Before
  public void init() {
    String name = "portfolio";
    Date date = DateUtil.convertDateStringToDate("2014-05-05");
  }


  @Test
  public void getStocksTest() throws Exception {
    Map<String, Integer> stocks = new HashMap<>();
    Map<String, Date> dates = new HashMap<>();
    Date date = new Date();

    stocks.put("GOOG", 20);
    stocks.put("AAPL", 10);
    stocks.put("MSFT", 5);
    dates.put("GOOG", date);
    dates.put("AAPL", date);
    dates.put("MSFT", date);

    FlexiblePortfolio portfolio = new FlexiblePortfolio("portfolio", stocks,
        0.05);

    assertEquals(stocks, portfolio.getStocks());
  }

  @Test
  public void addStocksTest() throws Exception {
    Map<String, Double> map = new HashMap<>();
    FlexiblePortfolio portfolio = new FlexiblePortfolio("portfolio");
    Map<String, Integer> stocks = new HashMap<>();
    Date date = DateUtil.convertDateStringToDate("2022-01-01");

    portfolio.addStock("GOOG", 20, date, 0.05);
    portfolio.addStock("AAPL", 15, date, 0.05);
    stocks.put("GOOG", 20);
    stocks.put("AAPL", 15);

    assertEquals(stocks, portfolio.loadCurrentStocks());
  }

  @Test
  public void sellStocksTest() throws Exception {
    Map<String, Integer> stocks = new HashMap<>();
    stocks.put("GOOG", 20);
    stocks.put("AAPL", 15);
    FlexiblePortfolio portfolio = new FlexiblePortfolio("portfolio", stocks,
        0.0);
    Date date = DateUtil.convertDateStringToDate("2022-01-01");
    portfolio.sellStock("GOOG", 5, date, 0.05);

    Map<String, Integer> stocks1 = new HashMap<>();
    stocks1.put("GOOG", 15);
    stocks1.put("AAPL", 15);
    assertEquals(stocks1, portfolio.loadCurrentStocks());
  }

  @Test
  public void getCostBasisTest() throws Exception {
    FlexiblePortfolio portfolio = new FlexiblePortfolio("portfolio", new HashMap<>(),
        "2022-11-15",
        0.1);
    portfolio.addStock("GOOG", 10, DateUtil.convertDateStringToDate("2022-11-15"),
        0.05);
    portfolio.addStock("AAPL", 10, DateUtil.convertDateStringToDate("2022-11-15"),
        0.05);

    assertEquals(2492.4,
        portfolio.getCostBasis(DateUtil.convertDateStringToDate("2022-11-16")),
        0.01);
  }

  @Test
  public void getTotalValueTest() throws Exception {
    FlexiblePortfolio portfolio = new FlexiblePortfolio("portfolio", new HashMap<>(),
        "2022-11-11",
        0.1);
    IStockSource alpha = new StockSourceAlphaVintage();

    Stock goog = alpha.getStock("GOOG", DateUtil.convertDateStringToDate("2022-11-11"));
    Stock aapl = alpha.getStock("AAPL", DateUtil.convertDateStringToDate("2022-11-11"));

    double tot;
    double googCost = goog.getPriceList().floorEntry(DateUtil.convertDateStringToDate("2022-11-11"))
        .getValue();
    double aaplCost = aapl.getPriceList().floorEntry(DateUtil.convertDateStringToDate("2022-11-11"))
        .getValue();

    portfolio.addStock("GOOG", 10, DateUtil.convertDateStringToDate("2022-11-11"),
        0.05);
    portfolio.addStock("AAPL", 10, DateUtil.convertDateStringToDate("2022-11-11"),
        0.05);

    tot = (googCost * 10) + (aaplCost * 10);

    assertEquals(tot, portfolio.getTotalValue(DateUtil.convertDateStringToDate("2022-11-11")),
        0.001);
  }

  @Test
  public void getPerformanceGraphMonthTest() throws Exception {
    FlexiblePortfolio portfolio = new FlexiblePortfolio("portfolio", new HashMap<>(),
        "2022-01-01",
        0.1);
    portfolio.addStock("GOOG", 5, DateUtil.convertDateStringToDate("2022-01-01"),
        0.05);
    portfolio.addStock("AAPL", 5, DateUtil.convertDateStringToDate("2022-01-01"),
        0.05);
    Map<String, Double> result = portfolio.generatePerformanceGraph("portfolio",
        DateUtil.convertDateStringToDate("2020-05-05"),
        DateUtil.convertDateStringToDate("2022-05-05"));
    Map<String, Double> graph = new HashMap<>();
    graph.put("Feb 2022", 14314.7);
    graph.put("Jul 2020", 0.0);
    graph.put("Apr 2021", 0.0);
    graph.put("May 2020", 0.0);
    graph.put("Sep 2020", 0.0);
    graph.put("Apr 2022", 12284.9);
    graph.put("Sep 2021", 0.0);
    graph.put("Jul 2021", 0.0);
    graph.put("May 2021", 0.0);
    graph.put("Nov 2021", 0.0);
    graph.put("Oct 2020", 0.0);
    graph.put("Dec 2020", 0.0);
    graph.put("Feb 2021", 0.0);
    graph.put("Jan 2021", 0.0);
    graph.put("Mar 2021", 0.0);
    graph.put("Jun 2020", 0.0);
    graph.put("Aug 2020", 0.0);
    graph.put("Oct 2021", 0.0);
    graph.put("May 2022", 12148.10);
    graph.put("Aug 2021", 0.0);
    graph.put("Jun 2021", 0.0);
    graph.put("Mar 2022", 14837.99);
    graph.put("Dec 2021", 0.0);
    graph.put("Nov 2020", 0.0);
    graph.put("Jan 2022", 14443.74);
    assertEquals(graph.size(), result.size());

    for (String date : result.keySet()) {
      assertEquals(result.get(date), graph.get(date), 0.1);
    }
  }


  @Test(expected = Exception.class)
  public void addStocksExceptionTest() throws Exception {
    FlexiblePortfolio portfolio = new FlexiblePortfolio("portfolio");
    portfolio.sellStock("GOOG", 5, DateUtil.convertDateStringToDate("test"),
        0.05);

  }

  @Test(expected = Exception.class)
  public void sellStocksExceptionTest1() throws Exception {
    FlexiblePortfolio portfolio = new FlexiblePortfolio("portfolio");
    portfolio.sellStock("29", 5, DateUtil.convertDateStringToDate("2021-01-01"),
        0.05);
  }

  @Test(expected = Exception.class)
  public void sellStocksExceptionTest2() throws Exception {
    FlexiblePortfolio portfolio = new FlexiblePortfolio("portfolio");
    portfolio.sellStock("GOOG", -5, DateUtil.convertDateStringToDate("2021-01-01"),
        0.05);
  }

  @Test(expected = Exception.class)
  public void sellStocksExceptionTest3() throws Exception {
    FlexiblePortfolio portfolio = new FlexiblePortfolio("portfolio");
    portfolio.sellStock("GOOG", 5, DateUtil.convertDateStringToDate("test"),
        0.05);

  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeCommissionTest() throws Exception {
    FlexiblePortfolio portfolio = new FlexiblePortfolio("portfolio",
        new HashMap<String, Integer>(), -0.5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void badNameTest1() throws Exception {
    FlexiblePortfolio portfolio = new FlexiblePortfolio("",
        new HashMap<String, Integer>(), 0.1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void badNameTest2() throws Exception {
    FlexiblePortfolio portfolio = new FlexiblePortfolio(null,
        new HashMap<String, Integer>(), 0.1);
  }

  @Test(expected = Exception.class)
  public void sellMoreStocksThanExistTest() throws Exception {
    FlexiblePortfolio portfolio = new FlexiblePortfolio("portfolio");
    portfolio.addStock("GOOG", 10, DateUtil.convertDateStringToDate("2022-11-17"),
        0.05);
    portfolio.sellStock("GOOG", 20, DateUtil.convertDateStringToDate("2022-11-17"),
        0.01);
  }

}
