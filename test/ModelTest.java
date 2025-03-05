import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import model.FileStorage;
import model.IFileStorage;
import model.IStockDB;
import model.IStockModel;
import model.IStockSource;
import model.Portfolio;
import model.Stock;
import model.StockDB;
import model.StockModel;
import model.StockSourceAlphaVintage;
import org.junit.Test;


/**
 * Tests the StockModel for creation of portfolios and file I/O.
 */
public class ModelTest {

  IStockModel model;
  Stock stock;
  IStockDB stockDB;

  /**
   * Verifies the creation of a stock database using StockDB.
   */
  @Test
  public void stockDBCreationTest() {
    Date date = new Date();
    stockDB = new StockDB();
    TreeMap<Date, Double> map = new TreeMap<>();

    map.put(date, 10.00);
    stock = new Stock("AAPL", map, date);

    stockDB.addStock(stock);

    stock = new Stock("MSFT", map, date);
    stockDB.addStock(stock);
    stock = new Stock("GOOG", map, date);
    stockDB.addStock(stock);

    assertEquals(map, stockDB.getStock("GOOG").getPriceList());
    assertEquals(map, stockDB.getStock("AAPL").getPriceList());
    assertEquals(map, stockDB.getStock("MSFT").getPriceList());

    assertEquals("GOOG", stockDB.getStock("GOOG").getTickerSymbol());

    map = new TreeMap<>();
    map.put(new Date(), 15.00);

    stockDB.addStock(new Stock("ATT", map, date));

    assertEquals(map, stockDB.getStock("ATT").getPriceList());
    assertEquals("ATT", stockDB.getStock("ATT").getTickerSymbol());


  }

  /**
   * Verifies the creation of the stock source from AlphaVintage.
   *
   * @throws Exception in case of invalid stock
   */
  @Test
  public void StockSourceTest() throws Exception {
    IStockSource stockSrc = new StockSourceAlphaVintage();
    stock = stockSrc.getStock("AAPL", new Date());

    assertEquals("AAPL", stock.getTickerSymbol());

  }

  /**
   * Verifies exception thrown if invalid stock ticker.
   *
   * @throws Exception for when invalid stock ticker.
   */
  @Test(expected = Exception.class)
  public void StockSourceExceptionTest() throws Exception {
    IStockSource stockSrc = new StockSourceAlphaVintage();
    stock = stockSrc.getStock("999", new Date());
  }

  @Test
  public void PortfolioCreationTest() {
    Map<String, Integer> map = new HashMap<>();
    map.put("AAPL", 12);
    map.put("T", 2);
    map.put("META", 155);
    Portfolio portfolio = new Portfolio("port1", map, 0.1);

    assertEquals(map, portfolio.getStocks());
    assertEquals("port1", portfolio.getPortfolioName());

    portfolio = new Portfolio("port2", map, 0.2);

    assertEquals(map, portfolio.getStocks());
    assertEquals("port2", portfolio.getPortfolioName());

    portfolio = new Portfolio("port3", map, 0.3);

    assertEquals(map, portfolio.getStocks());
    assertEquals("port3", portfolio.getPortfolioName());


  }

  @Test
  public void StockModelTest() throws Exception {
    IStockSource alpha;
    Portfolio portfolio1;
    Portfolio portfolio2;
    Stock stock1;
    Stock stock2;
    Stock stock3;

    Date date;

    alpha = new StockSourceAlphaVintage();

    stock1 = alpha.getStock("AAPL", new Date());
    stock2 = alpha.getStock("T", new Date());
    stock3 = alpha.getStock("META", new Date());
    date = new Date(114, 6, 14);

    stockDB = new StockDB();

    stockDB.addStock(stock1);
    stockDB.addStock(stock2);
    stockDB.addStock(stock3);

    model = new StockModel(alpha, stockDB);
    Map<String, Integer> map = new HashMap<>();
    map.put("AAPL", 15);
    map.put("T", 12);
    map.put("META", 200);
    model.createPortfolio("port1", map, 0.1);
    portfolio1 = new Portfolio("port1", map, 0.1);
    map = new HashMap<>();

    map.put("AAPL", 17);
    map.put("T", 1);

    model.createPortfolio("port2", map, 0.1);
    portfolio2 = new Portfolio("port2", map, 0.1);

    assertEquals(portfolio1.getPortfolioName(), model.getPortfolio("port1").getPortfolioName());
    assertEquals(portfolio2.getPortfolioName(), model.getPortfolio("port2").getPortfolioName());
    assertEquals(portfolio1.getStocks(), model.getPortfolio("port1").getStocks());
    assertEquals(portfolio2.getStocks(), model.getPortfolio("port2").getStocks());

    double price1 = stock1.getPriceList().get(date);
    double price2 = stock2.getPriceList().get(date);
    double price3 = stock3.getPriceList().get(date);

    double tot1 = price1 * portfolio1.getStocks().get("AAPL")
        + price2 * portfolio1.getStocks().get("T")
        + price3 * portfolio1.getStocks().get("META");

    double tot2 = price1 * portfolio2.getStocks().get("AAPL")
        + price2 * portfolio2.getStocks().get("T");

    assertEquals(tot1, model.getTotalValue("port1", date), 0.001);
    assertEquals(tot2, model.getTotalValue("port2", date), 0.001);
  }

  @Test
  public void FileIOTest() throws Exception {
    IStockSource alpha;
    IStockModel model;
    IFileStorage fileS;
    Portfolio copy;
    Stock stock1;
    Stock stock2;
    Stock stock3;
    TreeMap<Date, Double> map;
    File file;

    file = new File("data/port1test");
    map = new TreeMap<>();
    map.put(new Date(), 10.00);
    alpha = new StockSourceAlphaVintage();
    fileS = new FileStorage();

    stock1 = new Stock("AAPL", map, new Date());
    stock2 = new Stock("GOOG", map, new Date());
    stock3 = new Stock("A", map, new Date());
    stockDB = new StockDB();

    stockDB.addStock(stock1);
    stockDB.addStock(stock2);
    stockDB.addStock(stock3);

    model = new StockModel(alpha, stockDB);
    Map<String, Integer> map1 = new HashMap<>();
    map1.put("A", 200);
    map1.put("GOOG", 50);
    map1.put("AAPL", 10);

    model.createPortfolio("port1", map1, 0.1);

    if (file.exists()) {
      file.delete();
    }
    fileS.savePortfolioToFile(model.getPortfolio("port1"), "port1test");

    copy = (Portfolio) fileS.getPortfolio("port1test");

    assertEquals(copy.getPortfolioName(), model.getPortfolio("port1").getPortfolioName());
    assertEquals(copy.getCreationDate(), model.getPortfolio("port1").getCreationDate());
    assertEquals(copy.getStocks(), model.getPortfolio("port1").getStocks());
  }

  @Test(expected = NullPointerException.class)
  public void CreatePortfolioExceptionTest() throws Exception {
    model.createPortfolio("", new HashMap<String, Integer>(), 0.1);
  }

  @Test(expected = NullPointerException.class)
  public void GetPortfolioExceptionTest() throws Exception {
    model.getPortfolio("portfolioTest");
  }

  @Test(expected = NullPointerException.class)
  public void AddPortfolioExceptionTest() throws Exception {
    model.isPortfolioPresent(null);
  }

  @Test(expected = NullPointerException.class)
  public void GetTotalValueExceptionTest1() throws Exception {
    model.getTotalValue("", new Date());
  }

  @Test(expected = Exception.class)
  public void GetTotalValueExceptionTest2() throws Exception {
    model.getTotalValue("test", new Date(1000, 5, 5));
  }


}
