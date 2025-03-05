package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.FlexiblePortfolio;
import model.IFileStorage;
import model.IPortfolio;
import model.IStockModel;
import util.DateUtil;
import view.JFrameView;
import view.StockInfo;

public class JFrameController implements ActionListener {

  private final IStockModel model;
  private final JFrameView viewGUI;

  private final IFileStorage fileStorage;

  List<StockInfo> stockInfoList;


  public JFrameController(IStockModel model, JFrameView view, IFileStorage fileStorage) {
    this.model = model;
    this.viewGUI = view;
    this.fileStorage = fileStorage;
    view.setListener(this);
    view.setModel(model);
    stockInfoList = new ArrayList<>();
    view.start();

  }
  @Override
  public void actionPerformed(ActionEvent e) {
    String portfolioName;
    Date date;
    IPortfolio portfolio = null;
    boolean isFlexible = false;
    switch (e.getActionCommand()) {

      case "1":
        viewGUI.createPortfolio();
        portfolioName = viewGUI.getPortfolioName();
        isFlexible = viewGUI.getFlexible();
        if (isFlexible) {
          try {
            model.createFlexiblePortfolio(portfolioName,
                viewGUI.getStocks(), 0.0);
          } catch (Exception ex) {
            viewGUI.generateError(ex.getMessage());
          }
        } else {
          try {
            model.createPortfolio(portfolioName, viewGUI.getStocks(),
                0.0);
          } catch (Exception ex) {
            viewGUI.generateError(ex.getMessage());
          }
        }
        break;
      case "2":
        viewGUI.getPortfolio();
        portfolioName = viewGUI.getPortfolioName();
        try {
          viewGUI.displayPortfolio(model.getPortfolio(portfolioName));
        } catch (Exception ex) {
          viewGUI.generateError(ex.getMessage());
        }
        break;
      case "6":
        viewGUI.modifyPortfolio();
        portfolioName = viewGUI.getPortfolioName();
        stockInfoList = viewGUI.getStockInfoList();
        while (!stockInfoList.isEmpty()) {
          StockInfo temp = stockInfoList.get(0);
          if (temp.getBuy()) {
            try {
            addStockWithCommission(portfolioName, temp.getTicker(), temp.getQuantity(),
                temp.getDate(), temp.getCommission());
          } catch (Exception ex) {
            viewGUI.generateError("Could not buy stock" + temp.getTicker());
          }

          } else {
            try {
              sellStockWithCommission(portfolioName, temp.getTicker(), temp.getQuantity(),
                  temp.getDate(), temp.getCommission());
            } catch (Exception ex) {
              viewGUI.generateError("Could not sell stock" + temp.getTicker());
            }
          }
          stockInfoList.remove(0);
        }

        break;

      case "3":
        viewGUI.getPortfolioNameAndDate();
        date = viewGUI.getInputDate();
        portfolioName = viewGUI.getPortfolioName();
        getTotalValue(portfolioName, date);
        break;

      case "8":
        viewGUI.getPortfolioNameAndDate();
        date = viewGUI.getInputDate();
        portfolioName = viewGUI.getPortfolioName();
        try {
          portfolio = model.getPortfolio(portfolioName);
        } catch (Exception ex) {
          viewGUI.generateError("could not find portfolio");
        }
        if (!(portfolio instanceof FlexiblePortfolio)) {
          viewGUI.generateError("Simple portfolio does not support cost basis");
          break;
        }
        try {
          viewGUI.displayCostBasis(((FlexiblePortfolio) portfolio).getCostBasis(date), date);
        } catch (Exception ex) {
          viewGUI.generateError("Could not get cost basis");
        }
        break;

      case "9":
        viewGUI.getPerformanceGraphInfo();
        try {
          generatePerformanceGraph(viewGUI.getPortfolioName(), viewGUI.getStart()
              , viewGUI.getEnd());
        } catch (Exception ex) {
          viewGUI.generateError("Could not create performance graph");
        }
        break;
      case "4":
        try {
          viewGUI.getPortfolio();
          portfolioName = viewGUI.getPortfolioName();
          savePortfolio(portfolioName, portfolioName);
        }catch(Exception ex){
          viewGUI.generateError("Could not save file");
          break;
        }
        viewGUI.generateError("Successfully saved file");
        break;
      case "5":
        try {
          viewGUI.getPortfolio();
          portfolioName = viewGUI.getPortfolioName();
          getPortfolioFromFile(portfolioName);
        }catch (Exception ex){
          viewGUI.generateError("Could not load file");
          break;
        }
        viewGUI.generateError("Successfully loaded file");

        break;
      case "10":
        viewGUI.fixedCost();
        investFixedAmountStrategy(viewGUI.getPortfolioName(),
            viewGUI.getStrategyName(),
            viewGUI.getTotalCost(),
            viewGUI.getStart(),
            viewGUI.getStockWeights(),
            viewGUI.getCommission());
        break;

      case "11":
        viewGUI.dollarCost();
        try {
          investDollarCostAverageStrategy(viewGUI.getPortfolioName(),
              viewGUI.getStrategyName(),
              viewGUI.getTotalCost(),
              viewGUI.getStart(),
              viewGUI.getEnd(),
              viewGUI.getIntervalType(),
              viewGUI.getInterval(),
              viewGUI.getStockWeights(),
              viewGUI.getCommission());
        } catch (Exception ex) {
          viewGUI.generateError("Could not complete investment strategy");
        }

        break;
      case "exit":
        System.exit(0);
        break;
    }
  }




  public void createPortfolio(String portfolioName, Map<String, Integer> stocks,
      Double commission) {
    try {
      model.createPortfolio(portfolioName, stocks, commission);
      viewGUI.displayPortfolioCreateComplete();
    } catch (Exception e) {
      viewGUI.display(e.getMessage() + "\n\n");
    }
  }

  //new

  public void createFlexiblePortfolio(String portfolioName, Map<String, Integer> stocks,
      Double commission) {
    try {
      model.createFlexiblePortfolio(portfolioName, stocks, commission);
    } catch (Exception e) {
      viewGUI.display(e.getMessage() + "\n\n");
    }
  }


  public void getPortfolio(String portfolioName) {
    try {
      IPortfolio portfolio = model.getPortfolio(portfolioName);
      viewGUI.displayPortfolioInfo(portfolio);
    } catch (Exception e) {
      viewGUI.display(e.getMessage() + "\n\n");
    }
  }


  public void getTotalValue(String portfolioName, Date date) {
    try {
      double price = model.getTotalValue(portfolioName, date);
      viewGUI.displayTotalValue(portfolioName, price);
    } catch (Exception e) {
      viewGUI.generateError(e.getMessage() + "\n\n");
    }
  }

  public void savePortfolio(String portfolioName, String fileName) {
    try {
      IPortfolio portfolio = model.getPortfolio(portfolioName);
      fileStorage.savePortfolioToFile(portfolio, fileName);
      viewGUI.display("File saved.\n\n");
    } catch (Exception e) {
      viewGUI.display(e.getMessage() + "\n\n");
    }
  }


  public void getPortfolioFromFile(String fileName) {
    try {
      IPortfolio portfolio = fileStorage.getPortfolio(fileName);
      model.addPortfolioIfNotPresent(portfolio);
      viewGUI.displayPortfolioInfo(portfolio);
    } catch (Exception e) {
      e.printStackTrace();
      viewGUI.display(e.getMessage() + "\n\n");
    }
  }


  public void addStock(String portfolioName, String stockName, int num, Date date)
      throws Exception {
    addStockWithCommission(portfolioName, stockName, num, date, 0.0);
  }


  public void addStockWithCommission(String portfolioName, String stockName, int num, Date date,
      Double commission) throws Exception {
    System.out.println(portfolioName);
    IPortfolio portfolio = model.getPortfolio(portfolioName);

    if (!(portfolio instanceof FlexiblePortfolio)) {
      viewGUI.generateError("Given portfolio is unmodifiable.\n\n");
    } else {
      try {
        ((FlexiblePortfolio) portfolio).addStock(stockName, num, date, commission);
        viewGUI.generateError("Added the stock to the portfolio.\n\n");
      } catch (Exception e) {
        viewGUI.generateError(e.getMessage() + "\n\n");
      }
    }
  }


  public void sellStock(String portfolioName, String stockName, int num, Date date)
      throws Exception {
    sellStockWithCommission(portfolioName, stockName, num, date, 0.0);
  }

  public void sellStockWithCommission(String portfolioName, String stockName, int num, Date date,
      Double commission) throws Exception {
    IPortfolio portfolio = model.getPortfolio(portfolioName);

    if (!(portfolio instanceof FlexiblePortfolio)) {
      viewGUI.generateError("Given portfolio is unmodifiable.\n\n");
    } else if (!portfolio.getStocks().containsKey(stockName)) {
      viewGUI.generateError("Cannot find stock in given portfolio\n\n");
    } else {
      try {
        ((FlexiblePortfolio) portfolio).sellStock(stockName, num, date, commission);
        viewGUI.generateError("Removed the stock from the portfolio.\n\n");
      } catch (Exception e) {
        viewGUI.generateError(e.getMessage() + "\n\n");
      }
    }
  }


  public void getCostBasis(String portfolioName, Date date) {
    try {
      IPortfolio portfolio = model.getPortfolio(portfolioName);
      if (!(portfolio instanceof FlexiblePortfolio)) {
        viewGUI.display("Simple portfolio does not support cost basis\n\n");
      } else {
        double costBasis = ((FlexiblePortfolio) portfolio).getCostBasis(date);
        viewGUI.displayCostBasis(costBasis, date);
      }
    } catch (Exception e) {
      viewGUI.display("Cannot find portfolio\n");
    }

  }


  public void generatePerformanceGraph(String portfolioName, Date start, Date end) {
    try {
      Map<String, Double> performanceGraph = model.generatePerformanceGraph(portfolioName, start,
          end);

      Double min = performanceGraph.values().stream().filter(v -> v != 0.0).mapToDouble(v -> v)
          .min()
          .orElse(0.0);
      Double max = performanceGraph.values().stream().filter(v -> v != 0.0).mapToDouble(v -> v)
          .max()
          .orElse(0.0);
      int minLength = String.valueOf(min).length();
      int maxLength = String.valueOf(max).length();

      long base;
      long asterisksValue;
      if (maxLength > 50 * minLength) {
        base = -1L;
        asterisksValue = Double.valueOf(Math.ceil(max / 50)).longValue();
      } else if (maxLength < 3 * minLength) {
        asterisksValue = Double.valueOf(Math.ceil((max - min) / 48)).longValue();
        base = Math.max(0L, Double.valueOf(min - asterisksValue).longValue());
      } else {
        base = -1L;
        asterisksValue = min.longValue();
      }

      viewGUI.displayPerformanceGraph(performanceGraph, portfolioName,
          DateUtil.convertDateToDateString(start), DateUtil.convertDateToDateString(end), base,
          asterisksValue);
    } catch (Exception e) {
      viewGUI.display("Could not generate performance graph\n");
    }
  }


  public void investFixedAmountStrategy(String portfolioName, String strategyName, double totalCost, Date date, Map<String, Double> stockWeights, double commission) {
    try {
      model.investFixedAmountStrategy(portfolioName, strategyName, totalCost, date, stockWeights, commission);
    } catch (Exception e) {
      viewGUI.generateError(e.getMessage());

    }
  }


  public void investDollarCostAverageStrategy(String portfolioName, String strategyName, double totalCost, Date start,
      Date end, String intervalType, int interval,
      Map<String, Double> stockWeights, double commission) throws Exception {
    try {
      model.investDollarCostAverageStrategy(portfolioName, strategyName, totalCost, start, end, intervalType,
          interval, stockWeights, commission);
    } catch (Exception e) {
      viewGUI.generateError("Could not invest FixedAmountStrategy \n");
      e.printStackTrace();
    }
  }

  private Map<String, Integer> convertListToMap(String name, List<StockInfo> stocks) {
    Map<String, Integer> result = new HashMap<>();
    while (!stocks.isEmpty()) {
      result.put(stocks.get(0).getTicker(), stocks.get(0).getQuantity());
      stocks.remove(0);
    }
    return result;
  }
}
