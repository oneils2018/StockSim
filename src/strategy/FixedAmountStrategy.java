package strategy;

import model.IStockDB;
import model.IStockSource;
import model.Stock;
import util.DateUtil;
import util.StockUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FixedAmountStrategy implements IInvestmentStrategy {
    private final String strategyName;
    private final double totalCost;
    private final Map<String, Double> stockWeights;
    private final String date;
    private final double commission;
    private final IStockDB stockDB = StockUtil.getStockDBInstance();

    public FixedAmountStrategy(String strategyName, double totalCost, Map<String, Double> stockWeights, String date, double commission) {
        this.strategyName = strategyName;
        this.totalCost = totalCost;
        this.stockWeights = stockWeights;
        this.date = date;
        this.commission = commission;
    }

    @Override
    public String getStrategyName() {
        return strategyName;
    }

    @Override
    public double getTotalCost() {
        return totalCost;
    }

    @Override
    public Map<String, Double> getStockWeights() {
        return stockWeights;
    }

    @Override
    public String getDate() {
        return date;
    }

    @Override
    public String getEndDate() {
        return null;
    }

    @Override
    public double getCommission() {
        return commission;
    }

    @Override
    public String getIntervalType() {
        return null;
    }

    @Override
    public Integer getInterval() {
        return null;
    }

    @Override
    public double calculateCostBasis() {
        if (checkIfStrategyStarted()) {
            return totalCost;
        }
        return 0.0;
    }

    @Override
    public int calculateNumberOfInvestments() {
        if (checkIfStrategyStarted()) {
            return 1;
        }
        return 0;
    }

    @Override
    public boolean checkIfStrategyStarted() {
        IStockSource stockSource = StockUtil.getStockSourceInstance();
        Date currDate = new Date(System.currentTimeMillis());
        for(String tickerSymbol: stockWeights.keySet()) {
            Stock stock = stockDB.getStock(tickerSymbol);
            if (stock == null) {
                try {
                    stock = stockSource.getStock(tickerSymbol, currDate);
                    stockDB.addStock(stock);
                } catch (Exception e) {
                    continue;
                }
            }

            Map.Entry<Date, Double> entry = stock.getPriceList().ceilingEntry(DateUtil.convertDateStringToDate(date));

            if(entry != null) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Map<String, Double> currentStocks(Date date) {
        if(DateUtil.convertDateStringToDate(this.date).after(date)) {
            return new HashMap<>();
        }

        Map<String, Double> currentStocks = new HashMap<>();
        IStockSource stockSource = StockUtil.getStockSourceInstance();
        Date currDate = new Date(System.currentTimeMillis());
        for(String tickerSymbol: stockWeights.keySet()) {
            Stock stock = stockDB.getStock(tickerSymbol);
            if (stock == null) {
                try {
                    stock = stockSource.getStock(tickerSymbol, currDate);
                    stockDB.addStock(stock);
                } catch (Exception e) {
                    continue;
                }
            }

            Map.Entry<Date, Double> entry = stock.getPriceList().ceilingEntry(DateUtil.convertDateStringToDate(this.date));

            if(entry != null) {
                currentStocks.put(tickerSymbol, (totalCost * stockWeights.get(tickerSymbol) / 100 / entry.getValue()));
            }
        }
        return currentStocks;
    }
}
