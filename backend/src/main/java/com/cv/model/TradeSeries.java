package com.cv.model;

import java.util.ArrayList;
import java.util.Arrays;
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
    return new TradeSeries(tradesList);
  }
}
