package com.cv.model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class TradeSeries extends TimeSeries {

  public static String TRADEPAIRID_KEY = "TradePairId";
  private static String LABEL_KEY = "Label";
  private static String TYPE_KEY = "Type";
  private static String PRICE_KEY = "Price";
  private static String AMOUNT_KEY = "Amount";
  private static String TOTAL_KEY = "Total";
  private static String TIMESTAMP_KEY = "Timestamp";
  
  public static TradeSeries fromJson(JsonObject trades) {
    String successMessage = trades.get("Success").getAsString();
    if (!successMessage.equals("true")) return new TradeSeries();
    
    JsonArray tradeData = trades.get("Data").getAsJsonArray();
    TradeSeries tradeSeries = new TradeSeries();
    
    for (int i = 0; i < tradeData.size(); i++) {
      JsonObject tradeDataElement = tradeData.get(i).getAsJsonObject();
      
      int tradePairId = tradeDataElement.get(TradeSeries.TRADEPAIRID_KEY).getAsInt();
      String label = tradeDataElement.get(TradeSeries.LABEL_KEY).getAsString();
      String type = tradeDataElement.get(TradeSeries.TYPE_KEY).getAsString();
      Double price = tradeDataElement.get(TradeSeries.PRICE_KEY).getAsDouble();
      Double amount = tradeDataElement.get(TradeSeries.AMOUNT_KEY).getAsDouble();
      Double total = tradeDataElement.get(TradeSeries.TOTAL_KEY).getAsDouble();
      Long timestamp = tradeDataElement.get(TradeSeries.TIMESTAMP_KEY).getAsLong();

      TradePoint tradePoint = new TradePoint(timestamp, tradePairId, label, type, price, amount, total);
      tradeSeries.addPoint(tradePoint);
    }    
    
    return tradeSeries;
    
//    TradePoint[] trades = gson.fromJson(json, TradePoint[].class);
//    List<TradePoint> tradesList = Arrays.asList(trades);
//    DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
//    for (int i = 0; i < tradesList.size(); ++i) {
//      long timestamp = tradesList.get(i).getTimestamp();
//      Date date = new Date(timestamp * 1000);
//      String dateStr = df.format(date);
//      
//      tradesList.get(i).setTimeStr(dateStr);
//    }

//    return new TradeSeries(tradesList);
  }
}
