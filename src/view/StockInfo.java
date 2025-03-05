package view;

import java.util.Date;

public class StockInfo {
  String ticker;
  Integer quantity;
  Date date;
  boolean buy;
  Double commission;

  StockInfo(String tick, Integer quan, Date currDate){
    this.ticker = tick;
    this.quantity = quan;
    this.date = currDate;
    buy = true;
    commission = 0.0;
  }


  public String getTicker(){ return this.ticker;
  }


  public Integer getQuantity(){return quantity;}

  public Date getDate(){return date;}

  public boolean getBuy(){
    return this.buy;
  }

  public Double getCommission(){return this.commission;}

  public void setCommission(Double com){
    this.commission = com;
  }

  public void setBuy(boolean bool){
    this.buy = bool;
  }

}
