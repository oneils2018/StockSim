package util;

import model.*;

import java.util.Date;

public class StockUtil {
    public static IStockDB stockDB = new StockDB();
    public static IStockSource stockSource = new StockSourceAlphaVintage();

    public static IStockDB getStockDBInstance() {
        return stockDB;
    }

    public static IStockSource getStockSourceInstance() {
        return stockSource;
    }

    public static void setStockDBInstance(IStockDB newStockDB) {
        stockDB = newStockDB;
    }

    public static void setStockSourceInstance(IStockSource newStockSource) {
        stockSource = newStockSource;
    }

    public static boolean validateStock(String tickerSymbol, Date date) throws Exception {
        Stock stock = stockDB.getStock(tickerSymbol);
        if (stock == null) {
            stock = stockSource.getStock(tickerSymbol, date);
            stockDB.addStock(stock);
        }

        return stock != null && stock.getPriceList().size() > 0;
    }
}
