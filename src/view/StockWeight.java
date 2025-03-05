package view;

public class StockWeight {
  private double weight;
  private String ticker;

  StockWeight(double weight, String ticker){
    this.weight = weight;
    this.ticker = ticker;
  }

  public double getWeight(){
    return this.weight;
  }

  public String getTicker(){return this.ticker;}

}
