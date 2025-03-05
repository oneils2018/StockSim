package view;

import model.FlexiblePortfolio;
import model.IPortfolio;
import model.IPortfolioEvent;
import model.IStockModel;
import util.DateUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.*;

public class JFrameView extends JDialog implements IStockView{
  private final JButton createPortfolio;
  private final JButton displayPortfolio;
  private final JButton getValueOfPortfolio;
  private final JButton addStockPortfolio;
  private final JButton sellStockPortfolio;
  private final JButton costBasis;
  private final JButton performanceGraph;
  private final JButton savePortfolio;
  private final JButton loadPortfolio;
  private final JButton fixedStrategy;
  private final JButton dollarCostStrategy;

  private final JButton exitButton;
  private static JPanel datePanel;

    private IStockModel model;
    private boolean modify;
    private Date creationDate;
    private String portfolioName;
    private Map<String, Integer> stocks;
    private double commission;
    private boolean flexible;
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
    private String initialInput;
    private List<StockInfo> stocksList;

    public void updateStocksList(StockInfo stock) {
        stocksList.add(stock);
    }

    public void setModel(IStockModel model) {
        this.model = model;
    }

    @Override
    public String getInitialInput() {
        while(initialInput == null) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }

        String tmp = initialInput;
        initialInput = null;
        return tmp;
    }

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
    public boolean getFlexible(){return this.flexible;}

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
    public List<StockInfo> getStockInfoList(){
      List<StockInfo> temp = new ArrayList<>(this.stocksList);
      this.stocksList.clear();
      return temp;
    }


    public void setFlexible(boolean flexible){this.flexible = flexible;}
    public void setModify(boolean modify) {
        this.modify = modify;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setStocks(Map<String, Integer> stocks) {
        this.stocks = stocks;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }

    public void setTickerSymbol(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public void setStockWeights(Map<String, Double> stockWeights) {
        this.stockWeights = stockWeights;
    }

    public void setIntervalType(String intervalType) {
        this.intervalType = intervalType;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
    }


    public boolean inputCreatePortfolio(Set<String> portfolioNames) {
        return true;
    }

    public boolean inputExaminePortfolio(Set<String> portfolioNames) {
        return true;
    }

    public boolean inputTotalValue(Set<String> portfolioNames) throws Exception {
        return true;
    }

    public boolean inputPersistPortfolio(Set<String> portfolioNames) {
        return true;
    }

    public boolean inputLoadPortfolio(Set<String> portfolioNames) {
        return true;
    }

    public boolean inputAddStockPortfolio(Map<String, IPortfolio> portfolioList) throws Exception {
        return true;
    }

    public boolean inputSellStockPortfolio(Map<String, IPortfolio> portfolioList) throws Exception {
        return true;
    }

    public boolean inputCostBasis(Map<String, IPortfolio> portfolioList) throws Exception {
        return true;
    }

    public boolean inputPerformanceGraph(Map<String, IPortfolio> portfolioList) throws Exception {
        return true;
    }

    public boolean inputFixedAmountStrategy(Map<String, IPortfolio> portfolioList) throws Exception {
        return true;
    }

    public boolean inputDollarCostAverageStrategy(Map<String, IPortfolio> portfolioList) throws Exception {
        return true;
    }

  public JFrameView(IStockModel model){
    super((Window)null);
    setModal(false);
    setResizable(true);
    setSize(500, 300);
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setBackground(Color.BLACK);

    this.setLayout(new FlowLayout());
      JPanel mainPanel = new JPanel(new GridLayout(10, 1, 10, 5));

    //portfolio
    createPortfolio = new JButton("Create Portfolio");
    createPortfolio.setActionCommand("1");
    mainPanel.add(createPortfolio);

    displayPortfolio = new JButton("Display Portfolio");
    displayPortfolio.setActionCommand("2");
    mainPanel.add(displayPortfolio);

    getValueOfPortfolio = new JButton("Get value of portfolio");
    getValueOfPortfolio.setActionCommand("3");
    mainPanel.add(getValueOfPortfolio);

    savePortfolio = new JButton("Persist a portfolio to file");
    savePortfolio.setActionCommand("4");
    mainPanel.add(savePortfolio);

    loadPortfolio = new JButton("Load a portfolio from file");
    loadPortfolio.setActionCommand("5");
    mainPanel.add(loadPortfolio);

    addStockPortfolio = new JButton("Buy Stock Portfolio");
    addStockPortfolio.setActionCommand("6");
    mainPanel.add(addStockPortfolio);

    sellStockPortfolio = new JButton("Sell Stock Portfolio");
    sellStockPortfolio.setActionCommand("7");
    mainPanel.add(sellStockPortfolio);

    costBasis = new JButton("Get Cost Basis");
    costBasis.setActionCommand("8");
    mainPanel.add(costBasis);

    performanceGraph = new JButton("Get Performance Graph");
    performanceGraph.setActionCommand("9");
    mainPanel.add(performanceGraph);

    fixedStrategy = new JButton("Fixed Amount Strategy");
    fixedStrategy.setActionCommand("10");
    mainPanel.add(fixedStrategy);

    dollarCostStrategy = new JButton("Dollar Cost Averaging Strategy");
    dollarCostStrategy.setActionCommand("11");
    mainPanel.add(dollarCostStrategy);


    exitButton = new JButton("Exit");
    exitButton.setActionCommand("exit");
    mainPanel.add(exitButton);

    this.add(mainPanel);
    pack();


    datePanel = new JPanel(new FlowLayout());
    JTextArea year = new JTextArea( 1, 4);
    year.addKeyListener(new KeyAdapter() {
      @Override
      public void keyTyped(KeyEvent e){
        if (year.getText().length() > 3){
          e.consume();
        }
      }
    });
    JTextArea month = new JTextArea(1, 2);
    month.addKeyListener(new KeyAdapter() {
      @Override
      public void keyTyped(KeyEvent e){
        if (month.getText().length() > 1){
          e.consume();
        }
      }
    });
    JTextArea day = new JTextArea(1, 2);
    day.addKeyListener(new KeyAdapter() {
      @Override
      public void keyTyped(KeyEvent e){
        if (day.getText().length() > 1){
          e.consume();
        }
      }
    });
    JLabel addYear = new JLabel("Year:");
    JLabel addMonth = new JLabel("Month:");
    JLabel addDay = new JLabel("Day:");

    datePanel.add(addYear);
    datePanel.add(year);
    datePanel.add(addMonth);
    datePanel.add(month);
    datePanel.add(addDay);
    datePanel.add(day);


    modify = false;
    creationDate = null;
    portfolioName = "";
    stocks = new HashMap<>();
    commission = 0.0;
    inputDate = null;
     tickerSymbol = "";
     quantity = 0;
    start = null;
    end = null;
    strategyName = "";
    totalCost = 0.0;
    stockWeights = new HashMap<>();
    intervalType = "";
    interval = 0;
    initialInput = "";
    stocksList = new ArrayList<>();

  }

  public void setListener(ActionListener listener) {
    createPortfolio.addActionListener(listener);
    displayPortfolio.addActionListener(listener);
    addStockPortfolio.addActionListener(listener);
    sellStockPortfolio.addActionListener(listener);
    getValueOfPortfolio.addActionListener(listener);
    costBasis.addActionListener(listener);
    performanceGraph.addActionListener(listener);
    loadPortfolio.addActionListener(listener);
    savePortfolio.addActionListener(listener);
    fixedStrategy.addActionListener(listener);
    dollarCostStrategy.addActionListener(listener);
    exitButton.addActionListener(listener);
  }

  @Override
  public void start() {
    this.setVisible(true);
  }

  private JPanel getDatePanel(){
    JPanel panel = new JPanel(new FlowLayout());
    JTextArea year = new JTextArea( 1, 4);
    year.addKeyListener(new KeyAdapter() {
      @Override
      public void keyTyped(KeyEvent e){
        if (year.getText().length() > 3){
          e.consume();
        }
      }
    });
    JTextArea month = new JTextArea(1, 2);
    month.addKeyListener(new KeyAdapter() {
      @Override
      public void keyTyped(KeyEvent e){
        if (month.getText().length() > 1){
          e.consume();
        }
      }
    });
    JTextArea day = new JTextArea(1, 2);
    day.addKeyListener(new KeyAdapter() {
      @Override
      public void keyTyped(KeyEvent e){
        if (day.getText().length() > 1){
          e.consume();
        }
      }
    });
    JLabel addYear = new JLabel("Year:");
    JLabel addMonth = new JLabel("Month:");
    JLabel addDay = new JLabel("Day:");

    panel.add(addYear);
    panel.add(year);
    panel.add(addMonth);
    panel.add(month);
    panel.add(addDay);
    panel.add(day);

    return panel;
  }

  @Override
  public void display(String input) {
    generateError(input);
  }

  @Override
  public boolean validateInput(String input) {
    return true;
  }

  @Override
  public void displayPortfolioCreateComplete() {

  }

  @Override
  public void displayPortfolioInfo(IPortfolio portfolio) {
        displayPortfolio(portfolio);
  }

  @Override
  public void displayTotalValue(String portfolioName, double price) {
    JOptionPane.showMessageDialog(new JFrame(), "Total value for " + portfolioName +
        " is $" + price);
  }

  @Override
  public void displayCostBasis(Double cost, Date date) {
    JOptionPane.showMessageDialog(new JFrame(), "Cost basis on " +
        DateUtil.convertDateToDateString(date) + " is $" + String.format("%.2f",
        cost));

  }

  public void getPerformanceGraphInfo(){
    JDialog frame = new JDialog();

    frame.setModal(true);
    frame.setSize(getPreferredSize());
    frame.setLocation(200, 200);
    frame.setResizable(true);

    frame.setLayout(new GridLayout(10, 1, 5, 5));
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    JLabel title = new JLabel("Enter Performance Graph Information");
    title.setHorizontalAlignment(JLabel.CENTER);
    frame.add(title);


    JLabel name = new JLabel("Start and end dates");
    JLabel portfolio = new JLabel("Enter in portfolio name");

    JPanel getName = new JPanel();
    getName.setLayout(new FlowLayout());
    getName.add(portfolio);
    JTextArea nameText = new JTextArea(1, 5);
    getName.add(nameText);
    JPanel start = getDatePanel();
    JPanel end = getDatePanel();

    JButton enter = new JButton("enter");
    enter.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        String dateString = "";
        setPortfolioName(nameText.getText());
        for (Component component: start.getComponents()){
          if (component instanceof JTextArea){
            dateString = dateString.concat(((JTextArea) component).getText());
          }
        }

        for (int i = 0; i < 10; i++){
          if (i == 4 || i == 7){
            dateString = dateString.substring(0, i) + '-' + dateString.substring(i);
          }
        }

        setStart(DateUtil.convertDateStringToDate(dateString));

        for (Component component: end.getComponents()){
          if (component instanceof JTextArea){
            dateString = dateString.concat(((JTextArea) component).getText());
          }
        }

        for (int i = 0; i < 10; i++){
          if (i == 4 || i == 7){
            dateString = dateString.substring(0, i) + '-' + dateString.substring(i);
          }
        }
        setEnd(DateUtil.convertDateStringToDate(dateString));
        frame.dispose();

      }
    });
    frame.add(getName);
    frame.add(name);
    frame.add(start);
    frame.add(end);
    frame.add(enter);

    frame.pack();
    frame.setVisible(true);

  }

  @Override
  public void displayPerformanceGraph(Map<String, Double> performanceGraph, String portfolioName,
      String start, String end, Long base, Long asterisksValue) {
      JDialog frame = new JDialog();
      frame.setResizable(true);
      frame.setLayout(new GridLayout(50, 1));
      frame.setSize(500, 500);
      frame.add(new JLabel(String.format("\nPerformance of portfolio %s from %s to %s\n\n",
          portfolioName, start,
          end)));
      frame.add(new JLabel());


      List<String> dates = new ArrayList<>(performanceGraph.keySet());
      Collections.sort(dates);

      for (String date : dates) {
        frame.add(new JLabel(String.format("%s: %s\n", date,
            getAstericks(performanceGraph.get(date), base, asterisksValue))));
      }
      frame.add(new JLabel());

      frame.add(new JLabel("Scale: * = $" + asterisksValue + "\n"));
      if (base != -1L) {
        frame.add(new JLabel("Base: $" + base + "\n"));
      }

      frame.setVisible(true);
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

  public void modifyPortfolio(){
    JDialog frame = new GetPortfolioPopup(this, datePanel, true, false);
    this.setPortfolioName(frame.getName());
  }

  public void createPortfolio(){
    stocks = new HashMap<>();
    JDialog frame = new GetPortfolioPopup(this, datePanel, false, true);
    this.portfolioName = frame.getName();
  }

  public void displayPortfolio( IPortfolio portfolio){
    JDialog frame = new JDialog(this);
    frame.setModal(false);
    frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    frame.setLayout(new GridLayout(20, 1, 5, 5));
    frame.setSize(500,500);
    Map<String, Integer> stocks = portfolio.getStocks();
    Map<String, Double> stocksD = null;
    if (portfolio instanceof FlexiblePortfolio) {
      stocksD = ((FlexiblePortfolio) portfolio).loadCurrentStocks();
    }
    JPanel main = new JPanel();
    main.setLayout(new FlowLayout());
    main.add(new JLabel("Portfolio Name - \t" + portfolio.getPortfolioName()));
    main.add(new JLabel("Portfolio Creation Date - " + portfolio.getCreationDate()));
    frame.add(main);

    if(portfolio instanceof FlexiblePortfolio) {
      for (String tickerSymbol : stocksD.keySet()) {
        JPanel temp = new JPanel();
        temp.setLayout(new FlowLayout());
        temp.add(new JLabel(tickerSymbol + ":\t\t" + String.format("%.2f", stocksD.get(tickerSymbol))));
        frame.add(temp);
      }
    }
    else {
      for (String tickerSymbol : stocks.keySet()) {
        JPanel temp = new JPanel();
        temp.setLayout(new FlowLayout());
        temp.add(new JLabel(tickerSymbol + ":\t\t" + stocks.get(tickerSymbol)));
        frame.add(temp);
      }
    }

    if (portfolio instanceof FlexiblePortfolio) {
      List<IPortfolioEvent> portfolioEvents = ((FlexiblePortfolio) portfolio).getPortfolioEvents();
      JPanel flexibleTitles = new JPanel();
      flexibleTitles.setLayout(new FlowLayout());
      frame.add(new JLabel("Portfolio Events : "));
      flexibleTitles.add(new JLabel("Event Type   TickerSymbol  Quantity    CreationDate    Commission"));
      frame.add(flexibleTitles);
      for (IPortfolioEvent portfolioEvent : portfolioEvents) {
        JPanel temp = new JPanel();
        temp.setLayout(new FlowLayout());
        temp.add(new JLabel(("\n" + (portfolioEvent.isPurchase() ? "Purchase" : "Sell")
              + "     " + portfolioEvent.getTickerSymbol() + "    " + portfolioEvent.getQuantity()
             + "    " + portfolioEvent.getCreationDate() + "   "
            + portfolioEvent.getCommission())));
        frame.add(temp);
      }

    }

    frame.pack();
    frame.setVisible(true);

  }

  public void getPortfolio(){
    GetPortfolioName frame = new GetPortfolioName(false);
    this.portfolioName = frame.getName();
  }

  public void generateError(String error){
    JOptionPane.showMessageDialog(new JFrame(), error);
  }


    public void getPortfolioNameAndDate(){
        JDialog frame = new GetPortfolioName(true);
        this.portfolioName = frame.getName();
        System.out.println("set name " + frame.getName());
        this.inputDate = ((GetPortfolioName) frame).getDate();

    }

  private boolean validateStock(String ticker, Date date) throws Exception {
    return model.validateStock(ticker, date);
  }

  private boolean isPortfolioPresent(String name){
    return model.isPortfolioPresent(name);
  }

  public void windowTest(){
    costAveragingWindow window = new costAveragingWindow(this, this.model);
  }

  public void dollarCost(){
    costAveragingWindow window = new costAveragingWindow(this, this.model);
  }

  public void fixedCost(){
    strategyWindow window = new strategyWindow(this.model, this);
  }

  private static class GetPortfolioPopup extends JDialog {
    private JLabel portfolio;
    private JTextArea getText;
    private JButton addStocks;
    private JButton complete;
    JCheckBox flexible;
    private List<JRadioButton> buy;
    private List<JRadioButton> sell;
    private List<ButtonGroup> group;
    private List<JTextArea> commissionList;
    private boolean modify;

    private JDialog stocks;
    List<StockInfo> stockList;
    private String name;


    GetPortfolioPopup(JFrameView frame, JPanel datePanel, boolean modify, boolean newPortfolio) {
      super((Window)null);
      this.setModal(true);
      this.modify = modify;
      stockList = new Stack<>();
      setSize(800,500);
      setLocation(200, 200);
      this.setResizable(true);

      this.setLayout(new GridLayout(10, 1, 5, 5));
      this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      JLabel title = new JLabel("Enter Portfolio Information");
      title.setHorizontalAlignment(JLabel.CENTER);
      this.add(title);

      portfolio = new JLabel("Enter portfolio name:");
      getText = new JTextArea(1, 8);

      JPanel getPort = new JPanel(new FlowLayout());
      getPort.add(portfolio);
      getPort.add(getText);

      addStocks = new JButton("Add stock info");
      addStocks.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
          stocks = stockInit();
          stocks.setVisible(true);
        }
      });

      complete = new JButton("Done modifying portfolio");
      complete.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
          name = getText.getText();
          Map<String, Integer> map = new HashMap<>();
          if (newPortfolio && frame.isPortfolioPresent(name)){
            frame.generateError("Portfolio name already used");
            return;
          }
          frame.setName(getText.getText());

          if(newPortfolio && flexible.isSelected()){
            frame.setFlexible(true);
          }

          while (!stockList.isEmpty()){
            StockInfo temp = stockList.get(0);
            if (modify) {
              JRadioButton button = buy.get(0);
              JTextArea com = commissionList.get(0);

              temp.setBuy(button.isSelected());
              try {
                temp.setCommission(Double.valueOf(com.getText()));
              } catch (Exception ex) {
                frame.generateError("Invalid commission value, enter in a xx.yy format");
                return;
              }
              buy.remove(0);
              commissionList.remove(0);
            }
            stockList.remove(0);
            map.put(temp.getTicker(), temp.getQuantity());
            frame.updateStocksList(temp);
            try {
              if (!frame.validateStock(temp.getTicker(), temp.getDate())){
                frame.generateError("Invalid stock");
                return;
              }
            } catch (Exception ex) {
              frame.generateError("Invalid stock");
              return;
            }
          }
          frame.setStocks(map);
          dispose();
        }
      });
      buy = new Stack<>();
      sell = new Stack<>();
      commissionList = new Stack<>();

      group = new Stack<>();

      this.add(getPort);


      JPanel buttons = new JPanel(new FlowLayout());
      buttons.add(addStocks);
      buttons.add(complete);
      this.add(buttons);

      if (newPortfolio){
        flexible = new JCheckBox("Flexible Portfolio");
        this.add(flexible);
      }

      pack();
      setVisible(true);
    }
    public void updateStocks(String tickerSymbol, Date currDate, Integer num){
      JPanel stocks = new JPanel(new FlowLayout());
      stocks.add(new JLabel("Ticker Symbol: " + tickerSymbol));
      stocks.add(new JLabel("   Date: " + DateUtil.convertDateToDateString(currDate)));
      stocks.add(new JLabel("   Quantity: " + num));
      if (modify) {
        addButtons(stocks);
      }
      this.add(stocks);
      this.setVisible(true);
      stockList.add(new StockInfo(tickerSymbol, num, currDate));


    }

    public GetPortfolioPopup getCurr(){
      return this;
    }

    @Override
    public String getName(){return this.name;}

    public JDialog stockInit(){
      JDialog frame = new JDialog(this);
      frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      frame.setSize(500, 300);
      frame.setLocation(200, 200);
      frame.setResizable(true);


      frame.setLayout(new GridLayout(10,1,5,5));
      JLabel title = new JLabel("Enter Stock Information");
      title.setHorizontalAlignment(JLabel.CENTER);
      frame.add(title);

      frame.add(datePanel);
      JTextArea tickerText = new JTextArea(1, 4);
      tickerText.addKeyListener(new KeyAdapter() {
        @Override
        public void keyTyped(KeyEvent e){
          if (tickerText.getText().length() > 4){
            e.consume();
          }
        }
      });

      JPanel tickerPanel = new JPanel(new FlowLayout());
      tickerPanel.add(new JLabel("Stock Ticker:"));
      tickerPanel.add(tickerText);
      frame.add(tickerPanel);


      JPanel quantityPanel = new JPanel(new FlowLayout());
      quantityPanel.add(new JLabel("Quantity:"));
      JTextArea quantityText = new JTextArea(1, 4);
      quantityPanel.add(quantityText);

      frame.add(quantityPanel);

      JButton addStock = new JButton("add stock");
      addStock.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          String tickerSymbol = tickerText.getText();
          System.out.println(tickerSymbol);
          String dateString = "";
          Integer quantityReal = null;
          try {
            quantityReal = Integer.parseInt(quantityText.getText());
          }
          catch(Exception ex){
            JOptionPane.showMessageDialog(new JFrame(), "Invalid quantity entered");
          }

          if (quantityReal < 0){
            JOptionPane.showMessageDialog(new JFrame(), "Quantity cannot be negative");
          }
          for (Component component : datePanel.getComponents()){
            if (component instanceof JTextArea){
              dateString = dateString.concat(((JTextArea) component).getText());
              ((JTextArea)component).setText("");
            }
          }

          for (int i = 0; i < 10; i++){
            if (i == 4 || i == 7) {
              dateString = dateString.substring(0, i) + '-' + dateString.substring(i);
            }
          }
          frame.dispose();
          updateStocks(tickerSymbol, DateUtil.convertDateStringToDate(dateString), quantityReal);

        }
      });

      frame.add(addStock);
      frame.pack();
      return frame;

    }

    private void addButtons(JPanel panel){
    JRadioButton buyCurr = new JRadioButton("buy");
    JRadioButton sellCurr = new JRadioButton("sell");
    ButtonGroup currGroup = new ButtonGroup();
    JTextArea commission = new JTextArea("Enter Commission");

    buyCurr.setActionCommand("buy");
    buyCurr.setSelected(true);

    sellCurr.setActionCommand("sell");

    currGroup.add(buyCurr);
    currGroup.add(sellCurr);

    buy.add(buyCurr);
    sell.add(sellCurr);
    group.add(currGroup);
    commissionList.add(commission);
    panel.add(buyCurr);
    panel.add(sellCurr);
    panel.add(commission);
    }
  }

  private static class GetPortfolioName extends JDialog {

    String portfolioName;
    Date date;
    GetPortfolioName(boolean getDate){
      super((Window)null);
      setModal(true);

      this.setSize(500, 300);
      this.setLocation(200,200);
      setDefaultCloseOperation(DISPOSE_ON_CLOSE);

      JTextArea name = new JTextArea(1, 5);

      JButton exit = new JButton("Enter");
      exit.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          String text = name.getText();
          if (getDate) {
            String dateString = "";
            for (Component component : datePanel.getComponents()) {
              if (component instanceof JTextArea) {
                dateString = dateString.concat(((JTextArea) component).getText());
                ((JTextArea)component).setText("");
              }
            }
            for (int i = 0; i < 10; i++) {
              if (i == 4 || i == 7) {
                dateString = dateString.substring(0, i) + '-' + dateString.substring(i);
              }
            }
            setDate(DateUtil.convertDateStringToDate(dateString));
          }

          setVisible(false);
          dispose();
          setName(text);
        }
      });
      this.setLayout(new GridLayout(1, 3));
      this.add(new JLabel("Portfolio Name: "));
      this.add(name);
      if (getDate){
        this.add(datePanel);
      }
      this.add(exit);

      pack();
      this.setVisible(true);
    }

    public void setName(String name){
      portfolioName = name;
    }
    @Override
    public String getName(){return this.portfolioName;}

    public void setDate(Date date){
      this.date = date; }

    public Date getDate(){return this.date;}
  }

  private static class strategyWindow extends JDialog {

    String portfolioName;
    String date;
    List<JTextArea> weights;
    JTextArea amount;
    Double totAmount;
    List<String> tickers;


    strategyWindow(IStockModel model, JFrameView frame){
      super((Window)null);
      setModal(true);

      this.setSize(500, 300);
      this.setLocation(200,200);
      setDefaultCloseOperation(DISPOSE_ON_CLOSE);

      JTextArea strategyName = new JTextArea(1, 5);
      JTextArea portName = new JTextArea(1,5);
      weights = new Stack<>();
      tickers = new Stack<>();

      JButton portfolio = new JButton("Get portfolio");
      portfolio.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          IPortfolio port;
          try {
            port = model.getPortfolio(portName.getText());
            frame.setPortfolioName(portName.getText());

          } catch (Exception ex) {
            frame.generateError("Could not find portfolio please try again");
            return;
          }
          if (!(port instanceof FlexiblePortfolio)){
            frame.generateError("Simple portfolios do not support strategy plans");
            return;
          }
          Map<String, Double> stocks = ((FlexiblePortfolio) port).loadCurrentStocks();

          for (String tickerSymbol : stocks.keySet()) {
            updateList(tickerSymbol, stocks.get(tickerSymbol));
          }
        }
      });

      JButton exit = new JButton("Complete Strategy");
      exit.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          double tot = 0.0;
          double weight;
          String dateString = "";

          frame.setStrategyName(strategyName.getText());
          try{
            setAmount(Double.parseDouble(amount.getText()));
          }catch (Exception ex){
            frame.generateError("Invalid amount investment");
            return;
          }
          for (Component component : datePanel.getComponents()) {
            if (component instanceof JTextArea) {
              dateString = dateString.concat(((JTextArea) component).getText());
              ((JTextArea)component).setText("");
            }
          }
          for (int i = 0; i < 10; i++) {
            if (i == 4 || i == 7) {
              dateString = dateString.substring(0, i) + '-' + dateString.substring(i);
            }
          }
          frame.setStart(DateUtil.convertDateStringToDate(dateString));
          for (int i = 0; i < weights.size(); i++) {
            try {
              weight = Double.parseDouble(weights.get(i).getText());
              frame.getStockWeights().put(tickers.get(i), weight);
              tot += weight;

            } catch (Exception ex) {
              frame.generateError("Invalid weight " + weights.get(0).getText());
              return;
            }
          }

          if (Math.abs(tot - 1.0) > 0.001){
            frame.generateError("Ensure weights add up to a total of 1");
            return;
          }
          weights.clear();
          dispose();
        }
      });
      this.setLayout(new GridLayout(3, 1));


      JPanel name = new JPanel();
      name.add(new JLabel("Portfolio Name: "));
      name.add(portName);
      name.add(portfolio);

      JPanel stratName = new JPanel();
      stratName.add(new JLabel("Enter Strategy Name: "));
      stratName.add(strategyName);

      JPanel getAmount = new JPanel();
      amount = new JTextArea(1, 5);
      getAmount.add(new JLabel("Enter total investment amount: "));
      getAmount.add(amount);

      this.add(name);
      this.add(stratName);
      this.add(datePanel);
      this.add(getAmount);
      this.add(exit);


      pack();
      this.setVisible(true);


    }
    @Override
    public void setName(String name){
      portfolioName = name;
    }
    @Override
    public String getName(){return this.portfolioName;}

    private void setDate(String date){
      this.date = date; }

    public String getDate(){return this.date;}


    private void updateList(String ticker, Double quan){
      JPanel panel = new JPanel();
      panel.setLayout(new FlowLayout());
      panel.add(new JLabel("Stock: " + ticker + "   "));
      panel.add(new JLabel("Quantity: " + quan + "    "));
      panel.add(new JLabel("Investment Weight: "));
      JTextArea weight = new JTextArea(1, 3);
      panel.add(weight);
      weights.add(weight);
      tickers.add(ticker);
      this.add(panel);
      this.setVisible(true);

    }

    private void setAmount(Double amt){
      this.totAmount = amt;
    }

    public Double getAmount(){
      return this.totAmount;
    }

  }

  private static class costAveragingWindow extends JDialog{
    private JTextArea name;
    private JTextArea amount;
    private JTextArea strategyName;

    private JPanel start;
    private JPanel end;
    private JCheckBox hasEnd;
    private JDialog stocks;
    List<String> stockTickers;
    private List<JTextArea> weights;
    private ButtonGroup group;
    private JRadioButton day;
    private JRadioButton week;
    private JRadioButton month;
    private JRadioButton quarterly;
    private JRadioButton year;
    private JPanel investmentInterval;
    private JTextArea duration;
    private JTextArea commission;



    costAveragingWindow(JFrameView frame, IStockModel model){

      super((Window)null);
      this.setSize(500,500);
      this.setLayout(new GridLayout(10, 1));
      this.setModal(true);
      weights = new Stack<>();
      stockTickers = new Stack<>();

      JPanel namePanel = new JPanel();
      name = new JTextArea( 1,5);
      namePanel.add(new JLabel("Portfolio Name: "));
      namePanel.add(name);
      this.add(namePanel);

      JPanel strategyNamePanel = new JPanel();
      strategyName = new JTextArea(1,5);
      strategyNamePanel.add(new JLabel("Enter strategy name: "));
      strategyNamePanel.add(strategyName);
      this.add(strategyNamePanel);

      JPanel amountPanel = new JPanel();
      amount = new JTextArea(1 ,5);
      amountPanel.add(new JLabel("Enter investment amount:" ));
      amountPanel.add(amount);
      this.add(amountPanel);


      JPanel commissionPanel = new JPanel();
      commission = new JTextArea(1, 5);
      commissionPanel.add(new JLabel("Enter Comission: "));
      commissionPanel.add(commission);
      this.add(commissionPanel);
      start = frame.getDatePanel();
      end = frame.getDatePanel();

      //hasEnd = new JCheckBox();
      JPanel startDate = new JPanel();
      startDate.add(new JLabel("Enter start date: "));
      startDate.add(start);
      this.add(startDate);

      JPanel endDate = new JPanel();
      endDate.add(new JLabel("Enter end date (optional): "));
      endDate.add(end);
      this.add(endDate);


      JButton addStock = new JButton("add stocks");
      addStock.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          stocks = stockInit();
          stocks.setVisible(true);
        }
      });

      JButton enter = new JButton("Finish strategy");
      enter.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          String portName = name.getText();
          String stratName = strategyName.getText();
          Double weightTot = 0.0;
          Double amountNum = 0.0;
          Double commissionNum = 0.0;
          int durationNum;

          frame.setPortfolioName(portName);
          frame.setStrategyName(stratName);
          String startDate = "";
          String endDate = "";

          try {
            amountNum = Double.parseDouble(amount.getText());
          }catch(Exception ex){
            frame.generateError("Invalid investment amount");
            return;
          }

          try{
            commissionNum = Double.parseDouble(commission.getText());
          }catch (Exception ex){
            frame.generateError("Invalid Commission Value");
            return;
          }


          frame.setTotalCost(amountNum);
          frame.setCommission(commissionNum);

          for (Component component : start.getComponents()){
            if (component instanceof JTextArea){
              startDate = startDate.concat(((JTextArea)component).getText());
            }
          }
          for (int i = 0; i < 10; i++){
            if (i == 4 || i == 7){
              startDate = startDate.substring(0, i) + '-' + startDate.substring(i);
            }
          }
          frame.setStart(DateUtil.convertDateStringToDate(startDate));

          for (Component component : end.getComponents()){
            if (component instanceof JTextArea){
              endDate = endDate.concat(((JTextArea)component).getText());
            }
          }

        if (!endDate.equals("")){
          for (int i = 0; i < 10; i++){
            if (i == 4 || i == 7) {
              endDate = endDate.substring(0 , i) + '-' + endDate.substring(i);
            }
            }
          frame.setEnd(DateUtil.convertDateStringToDate(endDate));
          }



          for (Component button : investmentInterval.getComponents()){
            if (button instanceof JRadioButton){
              if (((JRadioButton)button).isSelected()){
                frame.setIntervalType(((JRadioButton)button).getText());
              }
            }
          }

          try {
            durationNum = Integer.parseInt(duration.getText());
          } catch(Exception ex){
            frame.generateError("Invalid duration");
            return;
          }

          frame.setInterval(durationNum);


          for (int i = 0; i < weights.size(); i++){
            try {
              frame.getStockWeights().put(stockTickers.get(i),
                  Double.parseDouble(weights.get(i).getText()));
              weightTot += Double.parseDouble(weights.get(i).getText());
            }catch(Exception ex){
              frame.generateError("Invalid Weight value " + weights.get(i));
              return;
            }
          }

          if (Math.abs(weightTot - 1) > 0.001){
            frame.generateError("Weights do not add up to 1");
            frame.getStockWeights();
            return;
          }

          dispose();
        }
      });


      day = new JRadioButton("Daily");
      day.setSelected(true);
      week = new JRadioButton("Weekly");
      month = new JRadioButton("Monthly");
      quarterly = new JRadioButton("Quarterly");
      year = new JRadioButton("Yearly");

      group = new ButtonGroup();
      group.add(day);
      group.add(week);
      group.add(month);
      group.add(quarterly);
      group.add(year);

      investmentInterval = new JPanel();
      investmentInterval.setLayout(new FlowLayout());
      investmentInterval.add(new JLabel("Investement Interval: "));
      investmentInterval.add(day);
      investmentInterval.add(week);
      investmentInterval.add(month);
      investmentInterval.add(quarterly);
      investmentInterval.add(year);
      investmentInterval.add(new JLabel("Enter interval duration: "));
      duration = new JTextArea(1, 5);
      investmentInterval.add(duration);
      this.add(investmentInterval);


      this.add(addStock);
      this.add(enter);
      this.pack();
      this.setVisible(true);
    }

    public JDialog stockInit(){
      JDialog frame = new JDialog(this);
      frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      frame.setSize(500, 300);
      frame.setLocation(200, 200);
      frame.setResizable(true);


      frame.setLayout(new GridLayout(10,1,5,5));
      JLabel title = new JLabel("Enter Stock Information");
      title.setHorizontalAlignment(JLabel.CENTER);
      frame.add(title);

      frame.add(datePanel);
      JTextArea tickerText = new JTextArea(1, 4);
      tickerText.addKeyListener(new KeyAdapter() {
        @Override
        public void keyTyped(KeyEvent e){
          if (tickerText.getText().length() > 4){
            e.consume();
          }
        }
      });

      JPanel tickerPanel = new JPanel(new FlowLayout());
      tickerPanel.add(new JLabel("Stock Ticker:"));
      tickerPanel.add(tickerText);
      frame.add(tickerPanel);


      JPanel quantityPanel = new JPanel(new FlowLayout());
      quantityPanel.add(new JLabel("Quantity:"));
      JTextArea quantityText = new JTextArea(1, 4);
      quantityPanel.add(quantityText);

      frame.add(quantityPanel);

      JButton addStock = new JButton("add stock");
      addStock.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          String tickerSymbol = tickerText.getText();
          String dateString = "";
          Integer quantityReal = null;
          try {
            quantityReal = Integer.parseInt(quantityText.getText());
          }
          catch(Exception ex){
            JOptionPane.showMessageDialog(new JFrame(), "Invalid quantity entered");
          }

          if (quantityReal < 0){
            JOptionPane.showMessageDialog(new JFrame(), "Quantity cannot be negative");
          }
          for (Component component : datePanel.getComponents()){
            if (component instanceof JTextArea){
              dateString = dateString.concat(((JTextArea) component).getText());
            }
          }

          for (int i = 0; i < 10; i++){
            if (i == 4 || i == 7) {
              dateString = dateString.substring(0, i) + '-' + dateString.substring(i);
            }
          }

          frame.dispose();
          updateStocks(tickerSymbol, DateUtil.convertDateStringToDate(dateString), quantityReal);

        }
      });

      frame.add(addStock);
      frame.pack();
      return frame;

    }

    public void updateStocks(String tickerSymbol, Date currDate, Integer num){
      JPanel stocks = new JPanel(new FlowLayout());
      stocks.add(new JLabel("Ticker Symbol: " + tickerSymbol));
      stocks.add(new JLabel("   Date: " + DateUtil.convertDateToDateString(currDate)));
      stocks.add(new JLabel("   Quantity: " + num));
      stocks.add(new JLabel("   Enter weight: "));
      JTextArea weight = new JTextArea(1, 5);
      stocks.add(weight);
      weights.add(weight);
      stockTickers.add(tickerSymbol);
      this.add(stocks);
      this.setVisible(true);
    }
  }
}
