package util;

import model.FlexiblePortfolio;
import model.Portfolio;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * Testing the file IO.
 */
public class FileUtilTest {

  @Test
  public void getPortfolioTest() throws Exception {
    Portfolio portfolio = FileUtil.getPortfolio("input.txt");

    Assert.assertEquals(portfolio.getPortfolioName(), "input");
    Assert.assertEquals(portfolio.getCreationDate(), "2020-11-02");
    Assert.assertEquals(portfolio.getCommission(), 0.1, 0);
    Assert.assertEquals(portfolio.getStocks().size(), 2);
  }

  @Test
  public void getFlexiblePortfolioTest() throws Exception {
    Portfolio portfolio = FileUtil.getPortfolio("flexiblePortfolioInput.txt");

    Assert.assertEquals(portfolio.getPortfolioName(), "flexiblePortfolioInput");
    Assert.assertEquals(portfolio.getCreationDate(), "2020-11-02");
    Assert.assertEquals(portfolio.getCommission(), 0.3, 0);
    Assert.assertEquals(portfolio.getStocks().size(), 2);
    Assert.assertTrue(portfolio instanceof FlexiblePortfolio);
    Assert.assertEquals(((FlexiblePortfolio) portfolio).getInitialCostBasis(), 297.58, 0);
    Assert.assertEquals(((FlexiblePortfolio) portfolio).getPortfolioEvents().size(), 1);
  }

  @Test
  public void savePortfolioToFileTest() throws Exception {
    new File("data/unit-test.txt").delete();
    Portfolio portfolio = FileUtil.getPortfolio("input.txt");
    FileUtil.savePortfolioToFile(portfolio, "unit-test.txt");

    portfolio = FileUtil.getPortfolio("unit-test.txt");
    Assert.assertEquals(portfolio.getPortfolioName(), "input");
    Assert.assertEquals(portfolio.getCreationDate(), "2020-11-02");
    Assert.assertEquals(portfolio.getCommission(), 0.1, 0);
    Assert.assertEquals(portfolio.getStocks().size(), 2);
    new File("data/unit-test.txt").delete();
  }

  @Test
  public void saveFlexiblePortfolioToFileTest() throws Exception {
    new File("data/unit-test.txt").delete();
    Portfolio portfolio = FileUtil.getPortfolio("flexiblePortfolioInput.txt");
    FileUtil.savePortfolioToFile(portfolio, "unit-test.txt");

    portfolio = FileUtil.getPortfolio("unit-test.txt");
    Assert.assertEquals(portfolio.getPortfolioName(), "flexiblePortfolioInput");
    Assert.assertEquals(portfolio.getCreationDate(), "2020-11-02");
    Assert.assertEquals(portfolio.getCommission(), 0.3, 0);
    Assert.assertEquals(portfolio.getStocks().size(), 2);
    Assert.assertTrue(portfolio instanceof FlexiblePortfolio);
    Assert.assertEquals(((FlexiblePortfolio) portfolio).getInitialCostBasis(), 297.58, 0);
    Assert.assertEquals(((FlexiblePortfolio) portfolio).getPortfolioEvents().size(), 1);
    new File("data/unit-test.txt").delete();
  }
}
