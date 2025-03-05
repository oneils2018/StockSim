//
//import static org.junit.Assert.assertEquals;
//
//import controller.IFeatures;
//import controller.StockController;
//import java.io.ByteArrayInputStream;
//import model.IFileStorage;
//import model.IStockSource;
//import model.MockFileView;
//import model.MockModel;
//import model.StockSourceAlphaVintage;
//import org.junit.Before;
//import org.junit.Test;
//import view.MockView;
//
///**
// * Testing of the controller class. Uses MOCK classes for View and Model to ensure isolation.
// */
//public class ControllerTestIsolated {
//
//  IFeatures control;
//  MockModel model;
//  MockView view;
//  IStockSource src;
//  String log1;
//  String log2;
//  String result;
//
//  IFileStorage fileStorage;
//
//  @Before
//  public void init() {
//    src = new StockSourceAlphaVintage();
//    log1 = "";
//    log2 = "";
//    result = "";
//    model = new MockModel(log1, 1);
//    view = new MockView(log2, 2);
//    fileStorage = new MockFileView();
//    control = new StockController(model, view, fileStorage);
//  }
//
//  @Test
//  public void StartTest() throws Exception {
//
//    System.setIn(new ByteArrayInputStream("0".getBytes()));
//    result = "USER DISPLAY STAND-INExiting the Application.\n\n";
//
//    control.start();
//    log2 = view.getLog();
//    System.out.println(log2);
//    assertEquals(result, log2);
//    result = "";
//    assertEquals(result, log1);
//
//  }
//
//
//  @Test
//  public void ViewPortfolioTest() throws Exception {
//    System.setIn(new ByteArrayInputStream("2".getBytes()));
//    control.start();
//
//    log2 = view.getLog();
//    log1 = model.getLog();
//    log2 = log2.replace('\n', 'n').replace('\r', 'r');
//    System.out.println(log2);
//    log1 = log1.replace('\n', 'n').replace('\r', 'r');
//    System.out.println(log1);
//    result = "USER DISPLAY STAND-INInput Name CallPORTDISPINFOUSER DISPLAY STAND-INExiting "
//        + "the Application.nn";
//    assertEquals(log2, result);
//    result = "GOT PORTFOLIO";
//
//    assertEquals(log1, result);
//
//  }
//
//  @Test
//  public void TotalValTest() throws Exception {
//    System.setIn(new ByteArrayInputStream("3".getBytes()));
//    control.start();
//
//    log2 = view.getLog();
//    log1 = model.getLog();
//    log2 = log2.replace('\n', 'n').replace('\r', 'r');
//    System.out.println(log2);
//    log1 = log1.replace('\n', 'n').replace('\r', 'r');
//    System.out.println(log1);
//    result = "USER DISPLAY STAND-INInput Name CallPortfolio doesn't exist.nn"
//        + "USER DISPLAY STAND-INExiting the Application.nn";
//    assertEquals(log2, result);
//    result = "";
//
//    assertEquals(log1, result);
//
//  }
//
//  @Test
//  public void SavePortfolioTest() throws Exception {
//    System.setIn(new ByteArrayInputStream("4".getBytes()));
//    control.start();
//
//    log2 = view.getLog();
//    log1 = model.getLog();
//    log2 = log2.replace('\n', 'n').replace('\r', 'r');
//    System.out.println(log2);
//    log1 = log1.replace('\n', 'n').replace('\r', 'r');
//    System.out.println(log1);
//    result = "USER DISPLAY STAND-INInput Name CallFile saved.nn"
//        + "USER DISPLAY STAND-INExiting the Application.nn";
//    assertEquals(log2, result);
//    result = "GOT PORTFOLIO";
//
//    assertEquals(log1, result);
//
//  }
//
//  @Test
//  public void LoadPortfolioTest() throws Exception {
//    System.setIn(new ByteArrayInputStream("5".getBytes()));
//    control.start();
//
//    log2 = view.getLog();
//    log1 = model.getLog();
//    log2 = log2.replace('\n', 'n').replace('\r', 'r');
//    System.out.println(log2);
//    log1 = log1.replace('\n', 'n').replace('\r', 'r');
//    System.out.println(log1);
//    result = "USER DISPLAY STAND-INInput Name CallPORTDISPINFOUSER DISPLAY STAND-INExiting the "
//        + "Application.nn";
//    assertEquals(log2, result);
//    result = "";
//
//    assertEquals(log1, result);
//
//  }
//}
//
