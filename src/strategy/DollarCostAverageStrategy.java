package strategy;

import model.IStockDB;
import model.IStockSource;
import model.Stock;
import util.DateUtil;
import util.StockUtil;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class DollarCostAverageStrategy implements IInvestmentStrategy {
    private final String strategyName;
    private final double totalCost;
    private final Map<String, Double> stockWeights;
    private final String date;
    private final String endDate;
    private final String intervalType;
    private final int interval;
    private final double commission;
    private final IStockDB stockDB = StockUtil.getStockDBInstance();
    private final TreeMap<Date, Map<String, Double>> dateToCurrentStocks = new TreeMap<>();

    public DollarCostAverageStrategy(String strategyName, double totalCost, Map<String, Double> stockWeights, String startDate, String endDate, String intervalType, int interval, double commission) {
        this.strategyName = strategyName;
        this.totalCost = totalCost;
        this.stockWeights = stockWeights;
        this.date = startDate;
        this.endDate = endDate;
        this.intervalType = intervalType;
        this.interval = interval;
        this.commission = commission;
        calculateStocks();
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
        return endDate;
    }

    @Override
    public double getCommission() {
        return commission;
    }

    @Override
    public String getIntervalType() {
        return intervalType;
    }

    @Override
    public Integer getInterval() {
        return interval;
    }

    @Override
    public double calculateCostBasis() {
        if(!checkIfStrategyStarted()) {
            return 0.0;
        }

        return totalCost * calculateNumberOfInvestments();
    }

    @Override
    public int calculateNumberOfInvestments() {
        if(!checkIfStrategyStarted()) {
            return 0;
        }

        int count = 0;
        Date date = DateUtil.convertDateStringToDate(this.date);
        Date endDate = new Date(System.currentTimeMillis());
        if(this.endDate != null && endDate.after(DateUtil.convertDateStringToDate(this.endDate))) {
            endDate = DateUtil.convertDateStringToDate(this.endDate);
        }
        switch (intervalType) {
            case "Daily":
                count = (int) DateUtil.between(date, endDate, ChronoUnit.DAYS);
                break;
            case "Weekly":
                count = (int) DateUtil.between(date, endDate, ChronoUnit.WEEKS);
                break;
            case "Monthly":
                count = (int) DateUtil.between(date, endDate, ChronoUnit.MONTHS);
                break;
            case "Quarterly":
                count = (int) (DateUtil.between(date, endDate, ChronoUnit.MONTHS) / 3);
                break;
            case "Yearly":
                count = (int) DateUtil.between(date, endDate, ChronoUnit.YEARS);
                break;
        }

        return 1 + (count / interval);
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

    public void calculateStocks() {
        Map<String, Double> currentStocks = new HashMap<>();
        IStockSource stockSource = StockUtil.getStockSourceInstance();
        Date currDate = DateUtil.convertDateStringToDate(this.date);
        Date endDate = new Date(System.currentTimeMillis());
        if(this.endDate != null && endDate.after(DateUtil.convertDateStringToDate(this.endDate))) {
            endDate = DateUtil.convertDateStringToDate(this.endDate);
        }
        while(!currDate.after(endDate)) {
            for (String tickerSymbol : stockWeights.keySet()) {
                Stock stock = stockDB.getStock(tickerSymbol);
                if (stock == null) {
                    try {
                        stock = stockSource.getStock(tickerSymbol, currDate);
                        stockDB.addStock(stock);
                    } catch (Exception e) {
                        continue;
                    }
                }

                Map.Entry<Date, Double> entry = stock.getPriceList().ceilingEntry(currDate);

                if (entry != null) {
                    currentStocks.put(tickerSymbol, currentStocks.getOrDefault(tickerSymbol, 0.0) + totalCost * stockWeights.get(tickerSymbol) / 100 / entry.getValue());
                }
            }
            dateToCurrentStocks.put(currDate, new HashMap<>(currentStocks));
            currDate = DateUtil.increment(currDate, intervalType, interval);
        }
    }

    @Override
    public Map<String, Double> currentStocks(Date date) {
        Map.Entry<Date, Map<String, Double>> currentStocks = dateToCurrentStocks.floorEntry(date);
        return currentStocks == null ? new HashMap<>() : currentStocks.getValue();
    }
}
