package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import model.FlexiblePortfolio;
import model.IFileStorage;
import model.IPortfolio;
import model.IStockModel;
import util.DateUtil;
import view.IStockView;
import view.JFrameView;
import view.StockInfo;

/**
 * Main controller to be used for Stocks Application. Takes inputs and delegates tasks to view and
 * StockModel.
 */
public class StockController implements IFeatures, ActionListener {

  private final IStockModel model;
  private final IStockView viewTUI;
  private final JFrameView viewGUI;
  private final IFileStorage fileStorage;
  private List<StockInfo> stockInfoList;

  /**
   * Creates controller object.
   *
   * @param model       Stock model to be used for commands.
   * @param viewTUI        View to be used for display purposes.
   * @param fileStorage File storage object to be used for file I/O
   */
  public StockController(IStockModel model, IStockView viewTUI, IFileStorage fileStorage, JFrameView viewGUI) {
    this.model = model;
    this.viewTUI = viewTUI;
    this.fileStorage = fileStorage;
    this.viewGUI = viewGUI;

    viewGUI.setListener(this);
    viewGUI.setModel(model);
    stockInfoList = new ArrayList<>();
  }

  /**
   * Main method for Stock Assignment. Will loop until exited by user. Makes calls to model and
   * view, takes in inputs.
   */

  public void start() throws Exception {
    while (true) {
      viewTUI.setModel(model);
      viewTUI.start();
      String input = viewTUI.getInitialInput();
      if (viewTUI.validateInput(input)) {
        int number = Integer.parseInt(input);
        System.out.println(number);
        if (number == 0) {
          viewTUI.display("Exiting the Application.\n\n");
          break;
        }

        switch (number) {
          case 1:
            if(viewTUI.inputCreatePortfolio(model.getPortfoliosList().keySet())) {
              if (viewTUI.isModify()) {
                createFlexiblePortfolio(viewTUI.getPortfolioName(), viewTUI.getStocks(), viewTUI.getCommission());
              } else {
                createPortfolio(viewTUI.getPortfolioName(), viewTUI.getStocks(), viewTUI.getCommission());
              }
            }
            break;
          case 2:
            if(viewTUI.inputExaminePortfolio(model.getPortfoliosList().keySet())){
              getPortfolio(viewTUI.getPortfolioName());
            }
            break;
          case 3:
            if(viewTUI.inputTotalValue(model.getPortfoliosList().keySet())){
              getTotalValue(viewTUI.getPortfolioName(), viewTUI.getInputDate());
            }
            break;
          case 4:
            if(viewTUI.inputPersistPortfolio(model.getPortfoliosList().keySet())){
              savePortfolio(viewTUI.getPortfolioName(), viewTUI.getPortfolioName() + ".txt");
            }
            break;
          case 5:
            if(viewTUI.inputLoadPortfolio(model.getPortfoliosList().keySet())){
              getPortfolioFromFile(viewTUI.getPortfolioName() + ".txt");
            }
            break;
          case 6:
            if(viewTUI.inputAddStockPortfolio(model.getPortfoliosList())){
              addStockWithCommission(viewTUI.getPortfolioName(), viewTUI.getTickerSymbol(), viewTUI.getQuantity(),
                      viewTUI.getInputDate(), viewTUI.getCommission());
            }
            break;
          case 7:
            if(viewTUI.inputSellStockPortfolio(model.getPortfoliosList())){
              sellStockWithCommission(
                  viewTUI.getPortfolioName(), viewTUI.getTickerSymbol(), viewTUI.getQuantity(),
                      viewTUI.getInputDate(), viewTUI.getCommission());
            }
            break;
          case 8:
            if(viewTUI.inputCostBasis(model.getPortfoliosList())){
              getCostBasis(viewTUI.getPortfolioName(), viewTUI.getInputDate());
            }
            break;
          case 9:
            if(viewTUI.inputPerformanceGraph(model.getPortfoliosList())){
              generatePerformanceGraph(viewTUI.getPortfolioName(), viewTUI.getStart(), viewTUI.getEnd());
            }
            break;
          case 10:
            if(viewTUI.inputFixedAmountStrategy(model.getPortfoliosList())){
              investFixedAmountStrategy(viewTUI.getPortfolioName(), viewTUI.getStrategyName(), viewTUI.getTotalCost(),
                      viewTUI.getInputDate(), viewTUI.getStockWeights(), viewTUI.getCommission());
            }
            break;
          case 11:
            if(viewTUI.inputDollarCostAverageStrategy(model.getPortfoliosList())){
              investDollarCostAverageStrategy(viewTUI.getPortfolioName(), viewTUI.getStrategyName(), viewTUI.getTotalCost(),
                      viewTUI.getStart(), viewTUI.getEnd(), viewTUI.getIntervalType(), viewTUI.getInterval(),
                      viewTUI.getStockWeights(), viewTUI.getCommission());
            }
            break;
          default:
            break;
        }
      }
    }
  }



  @Override
  public void createPortfolio(String portfolioName, Map<String, Integer> stocks,
      Double commission) {
    try {
      model.createPortfolio(portfolioName, stocks, commission);
      viewTUI.displayPortfolioCreateComplete();
    } catch (Exception e) {
      viewTUI.display(e.getMessage() + "\n\n");
    }
  }

  //new
  @Override
  public void createFlexiblePortfolio(String portfolioName, Map<String, Integer> stocks,
      Double commission) {
    try {
      model.createFlexiblePortfolio(portfolioName, stocks, commission);
    } catch (Exception e) {
      viewTUI.display(e.getMessage() + "\n\n");
    }
  }

  @Override
  public void getPortfolio(String portfolioName) {
    try {
      IPortfolio portfolio = model.getPortfolio(portfolioName);
      viewTUI.displayPortfolioInfo(portfolio);
    } catch (Exception e) {
      viewTUI.display(e.getMessage() + "\n\n");
    }
  }

  @Override
  public void getTotalValue(String portfolioName, Date date) {
    try {
      double price = model.getTotalValue(portfolioName, date);
      viewTUI.displayTotalValue(portfolioName, price);
    } catch (Exception e) {
      viewTUI.display(e.getMessage() + "\n\n");
    }
  }

  @Override
  public void savePortfolio(String portfolioName, String fileName) {
    try {
      IPortfolio portfolio = model.getPortfolio(portfolioName);
      fileStorage.savePortfolioToFile(portfolio, fileName);
      viewTUI.display("File saved.\n\n");
    } catch (Exception e) {
      viewTUI.display(e.getMessage() + "\n\n");
    }
  }

  @Override
  public void getPortfolioFromFile(String fileName) {
    try {
      IPortfolio portfolio = fileStorage.getPortfolio(fileName);
      model.addPortfolioIfNotPresent(portfolio);
      viewTUI.displayPortfolioInfo(portfolio);
    } catch (Exception e) {
      e.printStackTrace();
      viewTUI.display(e.getMessage() + "\n\n");
    }
  }

  @Override
  public void addStock(String portfolioName, String stockName, int num, Date date)
      throws Exception {
    addStockWithCommission(portfolioName, stockName, num, date, 0.0);
  }

  @Override
  public void addStockWithCommission(String portfolioName, String stockName, int num, Date date,
      Double commission) throws Exception {
    IPortfolio portfolio = model.getPortfolio(portfolioName);

    if (!(portfolio instanceof FlexiblePortfolio)) {
      viewTUI.display("Given portfolio is unmodifiable.\n\n");
    } else {
      try {
        ((FlexiblePortfolio) portfolio).addStock(stockName, num, date, commission);
        viewTUI.display("Added the stock to the portfolio.\n\n");
      } catch (Exception e) {
        viewTUI.display(e.getMessage() + "\n\n");
      }
    }
  }

  @Override
  public void sellStock(String portfolioName, String stockName, int num, Date date)
      throws Exception {
    sellStockWithCommission(portfolioName, stockName, num, date, 0.0);
  }

  @Override
  public void sellStockWithCommission(String portfolioName, String stockName, int num, Date date,
      Double commission) throws Exception {
    IPortfolio portfolio = model.getPortfolio(portfolioName);

    if (!(portfolio instanceof FlexiblePortfolio)) {
      viewTUI.display("Given portfolio is unmodifiable.\n\n");
    } else if (!portfolio.getStocks().containsKey(stockName)) {
      viewTUI.display("Cannot find stock in given portfolio\n\n");
    } else {
      try {
        ((FlexiblePortfolio) portfolio).sellStock(stockName, num, date, commission);
        viewTUI.display("Removed the stock from the portfolio.\n\n");
      } catch (Exception e) {
        viewTUI.display(e.getMessage() + "\n\n");
      }
    }
  }

  @Override
  public void getCostBasis(String portfolioName, Date date) {
    try {
      IPortfolio portfolio = model.getPortfolio(portfolioName);
      if (!(portfolio instanceof FlexiblePortfolio)) {
        viewTUI.display("Simple portfolio does not support cost basis\n\n");
      } else {
        double costBasis = ((FlexiblePortfolio) portfolio).getCostBasis(date);
        viewTUI.displayCostBasis(costBasis, date);
      }
    } catch (Exception e) {
      viewTUI.display("Cannot find portfolio\n");
    }

  }

  @Override
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

      viewTUI.displayPerformanceGraph(performanceGraph, portfolioName,
          DateUtil.convertDateToDateString(start), DateUtil.convertDateToDateString(end), base,
          asterisksValue);
    } catch (Exception e) {
      viewTUI.display("Could not generate performance graph\n");
    }
  }

  @Override
  public void investFixedAmountStrategy(String portfolioName, String strategyName, double totalCost, Date date, Map<String, Double> stockWeights, double commission) {
    try {
      model.investFixedAmountStrategy(portfolioName, strategyName, totalCost, date, stockWeights, commission);
    } catch (Exception e) {
      viewTUI.display("Could not invest FixedAmountStrategy \n");
      e.printStackTrace();
    }
  }

  @Override
  public void investDollarCostAverageStrategy(String portfolioName, String strategyName, double totalCost, Date start,
                                              Date end, String intervalType, int interval,
                                              Map<String, Double> stockWeights, double commission) throws Exception {
    try {
      model.investDollarCostAverageStrategy(portfolioName, strategyName, totalCost, start, end, intervalType,
              interval, stockWeights, commission);
    } catch (Exception e) {
      viewTUI.display("Could not invest FixedAmountStrategy \n");
      e.printStackTrace();
    }
  }


  public void startGUI(){
    viewGUI.start();
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
}
