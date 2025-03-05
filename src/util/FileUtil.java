package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.*;
import strategy.DollarCostAverageStrategy;
import strategy.FixedAmountStrategy;
import strategy.IInvestmentStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Class used for taking file IO and determining if a file already exists in data path.
 */
public class FileUtil {

  private static final ObjectMapper mapper = new ObjectMapper();

  /**
   * Saves given portfolio to a folder using the provided format.
   *
   * @param portfolio portfolio to be saved
   * @param fileName  name of file to be created
   * @throws Exception thrown if portfolio name already exists in path
   */
  public static void savePortfolioToFile(IPortfolio portfolio, String fileName) throws Exception {
    File f = new File("data/" + fileName);
    if (f.exists()) {
      throw new Exception("portfolio is already saved.");
    }

    mapper.writeValue(new File("data/" + fileName), portfolio);
  }

  /**
   * Used for retrieving a desired portfolio from the data folder.
   *
   * @param fileName filename for portfolio desired.
   * @return the portfolio retrieved from file.
   * @throws Exception thrown if file/portfolio does not exist.
   */
  public static Portfolio getPortfolio(String fileName) throws Exception {
    File file = new File("data/" + fileName);
    Map<String, Object> map = mapper.readValue(file, Map.class);

    String portfolioName = (String) map.get("portfolioName");
    String creationDate = (String) map.get("creationDate");
    Map<String, Integer> stocks = (Map<String, Integer>) map.get("stocks");
    Double commission = (Double) map.getOrDefault("commission", 0.0);

    Object initialCostBasis = map.get("initialCostBasis");
    if (initialCostBasis != null) {
      List<LinkedHashMap<String, Object>> portfolioEventsList =
          (List<LinkedHashMap<String, Object>>) map.get(
          "portfolioEvents");
      List<LinkedHashMap<String, Object>> portfolioStrategiesList =
              (List<LinkedHashMap<String, Object>>) map.get(
                      "portfolioStrategies");

      List<IPortfolioEvent> portfolioEvents = new ArrayList<>();
      if(portfolioEventsList != null) {
        for (LinkedHashMap<String, Object> portfolioEvent : portfolioEventsList) {
          Boolean isPurchase = (boolean) portfolioEvent.get("purchase");
          String creationDate1 = (String) portfolioEvent.get("creationDate");
          String tickerSymbol = (String) portfolioEvent.get("tickerSymbol");
          Integer quantity = (int) portfolioEvent.get("quantity");
          Double commission1 = (double) portfolioEvent.getOrDefault("commission", 0.0);

          portfolioEvents.add(
                  new PortfolioEvent(isPurchase, creationDate1, tickerSymbol, quantity, commission1));
        }
      }

      List<IInvestmentStrategy> portfolioStrategies = new ArrayList<>();
      if(portfolioStrategiesList != null) {
        for (LinkedHashMap<String, Object> portfolioStrategy : portfolioStrategiesList) {
          String strategyName = (String) portfolioStrategy.get("strategyName");
          Double totalCost = (double) portfolioStrategy.get("totalCost");
          Map<String, Double> stockWeights = (Map<String, Double>) portfolioStrategy.get("stockWeights");
          String date = (String) portfolioStrategy.get("date");
          String endDate = (String) portfolioStrategy.get("endDate");
          String intervalType = (String) portfolioStrategy.get("intervalType");
          Integer interval = (Integer) portfolioStrategy.get("interval");
          Double commission1 = (Double) portfolioStrategy.getOrDefault("commission", 0.0);

          if (intervalType == null) {
            portfolioStrategies.add(
                    new FixedAmountStrategy(strategyName, totalCost, stockWeights, date, commission1));
          } else {
            portfolioStrategies.add(
                    new DollarCostAverageStrategy(strategyName, totalCost, stockWeights, date, endDate,
                            intervalType, interval, commission1));
          }
        }
      }

      return new FlexiblePortfolio(portfolioName, stocks, creationDate, commission,
          (Double) initialCostBasis, portfolioEvents, portfolioStrategies);
    }

    return new Portfolio(portfolioName, stocks, creationDate, commission);
  }
}
