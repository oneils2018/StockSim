//import model.Portfolio;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import util.DateUtil;
//import view.IStockView;
//import view.StockView;
//
//import java.io.*;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.text.ParseException;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Scanner;
//
//import static org.junit.Assert.*;
//
///**
// * Testing the view implementation isolated from model and controller.
// */
//public class ViewTest {
//
//  private IStockView view;
//  Scanner scan;
//  PrintWriter print;
//
//  @Before
//  public void init() {
//    scan = new Scanner(System.in);
//    print = new PrintWriter(System.out);
//    view = new StockView(scan, print);
//  }
//
//  @Test
//  public void validateInputTrueTest() {
//    boolean isValid = view.validateInput("5");
//
//    Assert.assertTrue(isValid);
//  }
//
//  @Test
//  public void validateInputFalseTest() {
//    boolean isValid = view.validateInput("z");
//
//    Assert.assertFalse(isValid);
//  }
//
//  @Test
//  public void DisplayTest() throws IOException {
//    File file = new File("DisplayTestOutput.txt");
//    FileOutputStream outstream = new FileOutputStream("DisplayTestOutput.txt");
//    print = new PrintWriter(outstream);
//    file.createNewFile();
//
//    view = new StockView(scan, print);
//
//    view.display("Test String");
//    assertEquals("Test String", new String(Files.readAllBytes(Paths.get(file.getPath()))));
//  }
//
//  @Test
//  public void InputTest() {
//    System.setIn(new ByteArrayInputStream("Test Input".getBytes()));
//
//    view = new StockView(new Scanner(System.in), print);
//
//    assertEquals("Test Input", view.getInput());
//
//  }
//
//  @Test
//  public void ValidationTest() {
//    view = new StockView(scan, print);
//
//    assertFalse(view.validateInput("20"));
//    assertFalse(view.validateInput("test"));
//    assertFalse(view.validateInput(""));
//    assertTrue(view.validateInput("1"));
//    assertTrue(view.validateInput("2"));
//    assertTrue(view.validateInput("3"));
//    assertTrue(view.validateInput("4"));
//    assertTrue(view.validateInput("5"));
//  }
//
//  @Test
//  public void PortfolioInput() throws IOException {
//    String result;
//    File file = new File("PortfolioInputTest.txt");
//    FileOutputStream outstream = new FileOutputStream("PortfolioInputTest.txt");
//    print = new PrintWriter(outstream);
//    file.createNewFile();
//    System.setIn(new ByteArrayInputStream("Portfolio name".getBytes()));
//    scan = new Scanner(System.in);
//    view = new StockView(scan, print);
//
//    result = view.inputPortfolioName();
//
//    System.out.println(new String(Files.readAllBytes(Paths.get(file.getPath()))));
//    assertEquals("Please enter portfolio Name : \n",
//        new String(Files.readAllBytes(Paths.get(file.getPath()))));
//    assertEquals("Portfolio name", result);
//  }
//
//  @Test
//  public void TickerInputTest() throws IOException {
//    String result;
//    File file = new File("TickerInputTest.txt");
//    FileOutputStream outstream = new FileOutputStream("TickerInputTest.txt");
//    file.createNewFile();
//    print = new PrintWriter(outstream);
//
//    System.setIn(new ByteArrayInputStream("Ticker name".getBytes()));
//
//    scan = new Scanner(System.in);
//    view = new StockView(scan, print);
//
//    result = view.inputStockTickerSymbol();
//
//    assertEquals("Please enter Stock Ticker Symbol : \n"
//            + "Please enter 0 when all stocks are entered.\n",
//        new String(Files.readAllBytes(Paths.get(file.getPath()))));
//    assertEquals("Ticker name", result);
//  }
//
//  @Test
//  public void StockQuantityInputTest() throws IOException {
//    double result;
//    File file = new File("StockQuantityTest.txt");
//    FileOutputStream outstream = new FileOutputStream("StockQuantityTest.txt");
//    file.createNewFile();
//    print = new PrintWriter(outstream);
//
//    System.setIn(new ByteArrayInputStream("1".getBytes()));
//
//    scan = new Scanner(System.in);
//    view = new StockView(scan, print);
//
//    result = view.inputStockQuantity();
//
//    assertEquals("Please enter the quantity : \n",
//        new String(Files.readAllBytes(Paths.get(file.getPath()))));
//
//    assertEquals(1.00, result, 0.001);
//
//
//  }
//
//  @Test
//  public void InputDateTest() throws IOException, ParseException {
//    Date result;
//    File file = new File("InputDateTest.txt");
//    FileOutputStream outstream = new FileOutputStream("InputDateTest.txt");
//    file.createNewFile();
//    print = new PrintWriter(outstream);
//
//    System.setIn(new ByteArrayInputStream("2014-05-05".getBytes()));
//
//    scan = new Scanner(System.in);
//    view = new StockView(scan, print);
//
//    result = view.inputDate();
//
//    assertEquals("Enter the Date in this format (yyyy-MM-dd)",
//        new String(Files.readAllBytes(Paths.get(file.getPath()))));
//
//    assertEquals(new Date(114, 04, 05), result);
//
//  }
//
//  @Test
//  public void PortfolioCreationDisplayTest() throws IOException {
//    File file = new File("CreationDisplayTest.txt");
//    FileOutputStream outstream = new FileOutputStream("CreationDisplayTest.txt");
//    file.createNewFile();
//    print = new PrintWriter(outstream);
//
//    scan = new Scanner(System.in);
//    view = new StockView(scan, print);
//    view.displayPortfolioCreateComplete();
//
//    assertEquals("Portfolio created successfully. \n\n",
//        new String(Files.readAllBytes(Paths.get(file.getPath()))));
//  }
//
//  @Test
//  public void DisplayPortfolioInfoTest() throws IOException {
//    Portfolio port1;
//    File file = new File("StockQuantityTest.txt");
//    FileOutputStream outstream = new FileOutputStream("StockQuantityTest.txt");
//    file.createNewFile();
//    print = new PrintWriter(outstream);
//
//    scan = new Scanner(System.in);
//    view = new StockView(scan, print);
//    port1 = new Portfolio("port1");
//    view.displayPortfolioInfo(port1);
//    assertEquals("\nPortfolio Name - port1\nPortfolio Creation Date - "
//        + port1.getCreationDate()
//        + "\nStock\t\tQuantity\n\n", new String(Files.readAllBytes(Paths.get(file.getPath()))));
//
//  }
//
//  @Test
//  public void DisplayTotalValueTest() throws IOException {
//    File file = new File("DisplayTotalValueTest.txt");
//    FileOutputStream outstream = new FileOutputStream("DisplayTotalValueTest.txt");
//    file.createNewFile();
//    print = new PrintWriter(outstream);
//
//    scan = new Scanner(System.in);
//    view = new StockView(scan, print);
//
//    view.displayTotalValue("port1", 10.00);
//
//    assertEquals("Total value for the port1 is 10.0. \n\n",
//        new String(Files.readAllBytes(Paths.get(file.getPath()))));
//  }
//
//  @Test
//  public void GetUserDisplayTest() throws IOException {
//    File file = new File("GetUserDisplayTest.txt");
//    FileOutputStream outstream = new FileOutputStream("GetUserDisplayTest.txt");
//    file.createNewFile();
//    print = new PrintWriter(outstream);
//
//    scan = new Scanner(System.in);
//    view = new StockView(scan, print);
//
//    assertEquals("Please choose a number from the following.\n"
//        + "1. Create portfolio\n"
//        + "2. Examine portfolio\n"
//        + "3. Determine the total value of a portfolio on a certain date\n"
//        + "4. Persist a portfolio to file\n"
//        + "5. Load a portfolio from file\n"
//        + "6. Add a stock to a Flexible Portfolio\n"
//        + "7. Sell a stock from a Flexible Portfolio\n"
//        + "8. Get cost basis of a Flexible Portfolio\n"
//        + "9. Create portfolio performance graph\n"
//        + "0. Exit\n", view.getUserDisplay());
//  }
//
//  @Test
//  public void displayCostBasisTest() throws IOException {
//    new File("displayCostBasisTest.txt").delete();
//    File file = new File("displayCostBasisTest.txt");
//    FileOutputStream outstream = new FileOutputStream("displayCostBasisTest.txt");
//    print = new PrintWriter(outstream);
//    file.createNewFile();
//
//    scan = new Scanner(System.in);
//    view = new StockView(scan, print);
//
//    view.displayCostBasis(0.2, DateUtil.convertDateStringToDate("2022-10-10"));
//    assertEquals("Cost basis on 2022-10-10 is $0.20\n\n",
//        new String(Files.readAllBytes(Paths.get(file.getPath()))));
//
//    new File("displayCostBasisTest.txt").delete();
//  }
//
//  @Test
//  public void inputIsCommissionTest() throws Exception {
//    new File("inputIsCommissionTest.txt").delete();
//    File file = new File("inputIsCommissionTest.txt");
//    FileOutputStream outstream = new FileOutputStream("inputIsCommissionTest.txt");
//    print = new PrintWriter(outstream);
//    file.createNewFile();
//    System.setIn(new ByteArrayInputStream("y".getBytes()));
//
//    scan = new Scanner(System.in);
//    view = new StockView(scan, print);
//
//    boolean inputIsCommission = view.inputIsCommission();
//    assertEquals("Is commission involved? Y/N?",
//        new String(Files.readAllBytes(Paths.get(file.getPath()))));
//    assertTrue(inputIsCommission);
//
//    new File("inputIsCommissionTest.txt").delete();
//  }
//
//  @Test
//  public void inputCommissionTest() throws IOException {
//    new File("inputCommissionTest.txt").delete();
//    File file = new File("inputCommissionTest.txt");
//    FileOutputStream outstream = new FileOutputStream("inputCommissionTest.txt");
//    print = new PrintWriter(outstream);
//    file.createNewFile();
//    System.setIn(new ByteArrayInputStream("0.1".getBytes()));
//
//    scan = new Scanner(System.in);
//    view = new StockView(scan, print);
//
//    Double inputCommission = view.inputCommission();
//    assertEquals("Enter commission : ", new String(Files.readAllBytes(Paths.get(file.getPath()))));
//    assertEquals(inputCommission, 0.1, 0);
//
//    new File("inputCommissionTest.txt").delete();
//  }
//
//  @Test
//  public void displayPerformanceGraphTest() throws IOException {
//    new File("displayPerformanceGraphTest.txt").delete();
//    File file = new File("displayPerformanceGraphTest.txt");
//    FileOutputStream outstream = new FileOutputStream("displayPerformanceGraphTest.txt");
//    print = new PrintWriter(outstream);
//    file.createNewFile();
//
//    scan = new Scanner(System.in);
//    view = new StockView(scan, print);
//
//    Map<String, Double> performanceGraph = new HashMap<>();
//    performanceGraph.put("2022-10-10", 30.0);
//    performanceGraph.put("2022-10-11", 50.0);
//    view.displayPerformanceGraph(performanceGraph, "tmp", "2022-10-10", "2022-10-11", 10L, 20L);
//    assertEquals("\nPerformance of portfolio tmp from 2022-10-10 to 2022-10-11\n\n" +
//        "2022-10-10: *\n" +
//        "2022-10-11: **\n" +
//        "\n\n" +
//        "Scale: * = $20\n" +
//        "Base: $10\n", new String(Files.readAllBytes(Paths.get(file.getPath()))));
//
//    new File("displayPerformanceGraphTest.txt").delete();
//  }
//
//  @Test
//  public void GUITest(){
////    JFrameView frame = new JFrameView();
////    frame.display("test");
//  }
//
//}
