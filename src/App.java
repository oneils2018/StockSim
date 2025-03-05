import controller.StockController;
import java.io.PrintWriter;
import java.util.Scanner;
import model.FileStorage;
import model.IFileStorage;
import model.IStockDB;
import model.IStockModel;
import model.IStockSource;
import model.StockDB;
import model.StockModel;
import model.StockSourceAlphaVintage;
import view.IStockView;
import view.JFrameView;
import view.StockView;

/**
 * Main method to run Stock program. Initializes Model, view, and controller.6
 */
public class App {

  /**
   * Main program to be run for Stocks assigment.
   *
   * @param args no arguments expected.
   * @throws Exception Possible exception thrown for invalid inputs.
   */

  public static void main(String[] args) throws Exception {
    IStockSource stockSource = new StockSourceAlphaVintage();
    IStockDB stockDB = new StockDB();

    IStockModel model = new StockModel(stockSource, stockDB);
    JFrameView viewGUI = new JFrameView(model);
   IStockView viewTUI = new StockView(new Scanner(System.in), new PrintWriter(System.out));

    IFileStorage fileStorage = new FileStorage();
//    JFrameController controller = new JFrameController(model, view, fileStorage);
    StockController stockController = new StockController(model, viewTUI, fileStorage, viewGUI);
    stockController.startGUI();
    //frame.display("test");
  }
}
