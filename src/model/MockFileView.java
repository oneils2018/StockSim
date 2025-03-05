package model;

/**
 * MOCK class of the file save class to be used ONLY in ControllerTest.
 */
public class MockFileView implements IFileStorage {

  private String log = "";

  @Override
  public void savePortfolioToFile(IPortfolio portfolio, String fileName) throws Exception {
    log += "Saving Portfolio with name " + fileName;
  }

  @Override
  public Portfolio getPortfolio(String fileName) throws Exception {
    log += "Getting portfolio of name" + fileName;
    return null;
  }
}
