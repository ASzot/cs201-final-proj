package com.cv.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;

public class TradeSeries {
  private List<Trade> trades;

  public TradeSeries(List<Trade> trades) {
    this.trades = trades;
  }

  public List<Trade> getTrades() {
    return trades;
  }

  public static TradeSeries fromJson(String json, Gson gson) {
    Trade[] trades = gson.fromJson(json, Trade[].class);
    List<Trade> tradesList = Arrays.asList(trades);
    DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
    for (int i = 0; i < tradesList.size(); ++i) {
      long timestamp = tradesList.get(i).getTimestamp();
      Date date = new Date(timestamp * 1000);
      String dateStr = df.format(date);
      
      tradesList.get(i).setTimeStr(dateStr);
    }

    return new TradeSeries(tradesList);
  }
}
