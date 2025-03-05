package model;

import util.FileUtil;

/**
 * Used for file I/O. Can save files to data directory or retrieve files with a given path.
 */
public class FileStorage implements IFileStorage {

  @Override
  public void savePortfolioToFile(IPortfolio portfolio, String fileName) throws Exception {
    FileUtil.savePortfolioToFile(portfolio, fileName);
  }

  @Override
  public IPortfolio getPortfolio(String fileName) throws Exception {
    return FileUtil.getPortfolio(fileName);
  }
}
