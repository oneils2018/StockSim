package model;

/**
 * Used for file I/O. Can save a file to data directory. Can retrieve file from a given path.
 */
public interface IFileStorage {

  /**
   * Saves a given portfolio to the data directory.
   *
   * @param portfolio portfolio to be saved.
   * @param fileName  name of file for saving portfolio
   * @throws Exception throws if filename is null
   */
  void savePortfolioToFile(IPortfolio portfolio, String fileName) throws Exception;

  /**
   * Gets desired portfolio from a filepath and loads into stockmodel.
   *
   * @param fileName file path for desired file
   * @return return portfolio object with contents of file
   * @throws Exception if filename is null or ""
   */
  IPortfolio getPortfolio(String fileName) throws Exception;
}
