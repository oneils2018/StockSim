public interface Portfolio {

  double getShares();

  //PorfolioBuilder getBuilder();

  int size();

  Stock getStock(int index);

}
