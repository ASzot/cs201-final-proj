package com.cv.cryptowatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.cv.model.CandleStickData;
import com.cv.model.CandleStickSeries;
import com.cv.model.TimeSeries;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class CryptoWatchApi {
  private CryptoWatchRest rest;

  public CryptoWatchApi(CryptoWatchRest rest) {
    this.rest = rest;
  }

  ///**
  // * Method to return all supported exchanges
  // * @return JsonObject containing the supported exchanges
  // */
  //public JsonObject getExchanges() {   
  //  String url = "https://api.cryptowat.ch/assets";

  //  JsonObject result = executeCall(url);
  //  return result;
  //}

  ///**
  // * Method to return all supported markets and accompanying URL for market data
  // * @return JsonObject containing the supported exchanges
  // */
  //public JsonObject getMarkets() {   
  //  String url = "https://api.cryptowat.ch/markets";
  //  JsonObject result = executeCall(url);
  //  return result;
  //}


  //Markets: ltcusd, btcusd, ltcbtc
  //public JsonObject getMarketDataURLs(String market) {   
  //  String url = "https://api.cryptowat.ch/markets/gdax/" + market;
  //  JsonObject result = executeCall(url);
  //  return result;
  //}

  ////Markets: ltcusd, btcusd, ltcbtc
  //public JsonObject getLastPrice(String market) {   
  //  String url = "https://api.cryptowat.ch/markets/gdax/" + market + "/price";
  //  JsonObject result = executeCall(url);
  //  return result;
  //}

  ////Markets: ltcusd, btcusd, ltcbtc
  //public JsonObject getCurrencySummary(String market) {   
  //  String url = "https://api.cryptowat.ch/markets/gdax/" + market + "/summary";

  //  JsonObject result = executeCall(url);
  //  return result;
  //}

  //public JsonObject getTrades(String market, int limitTrades) {   
  //  String url = "https://api.cryptowat.ch/markets/gdax/" + market + "/trades";
  //  String params = "?limit=" + limitTrades;
  //  url += params;

  //  JsonObject result = executeCall(url);
  //  return result;
  //}

  //public JsonObject getOrders(String market) {   
  //  String url = "https://api.cryptowat.ch/markets/gdax/" + market + "/orderbook";
  //  JsonObject result = executeCall(url);
  //  return result;
  //}

  //print information regarding remaining time before API rate limitation
  private void printAPIRateLimitations(JsonObject result) {
    JsonObject rateInfo = result.get("allowance").getAsJsonObject();
    String cost = rateInfo.get("cost").getAsString();
    String allowance = rateInfo.get("remaining").getAsString();
    System.out.println("Cost: " + cost + " Allowance remaining: " + allowance);
  }
  
  public CandleStickSeries getCandlestick(String market, List<String> periods, long before, long after) {   
    List<String> paramParts = new ArrayList<String>();
    if (periods != null && periods.size() > 0) {
      String periodsStr = String.join(",", periods);
      if (periodsStr.trim().length() > 0) {
        paramParts.add("periods=" + periodsStr);
      }
    }

    if (before >= 0) {
      paramParts.add("before=" + before);
    }

    if (after >= 0) {
      paramParts.add("after=" + after);
    }

    String paramsStr = String.join("&", paramParts);
    if (paramsStr.length() > 0) {
      paramsStr = "?" + paramsStr;
    }

    String endpoint = "/markets/gdax/" + market + "/ohlc" + paramsStr;
    System.out.println("Endpoint is");
    System.out.println(endpoint);

    JsonObject result = rest.get(endpoint);
    if (result == null) {
      return null;
    }

    JsonObject windows = result.get("result").getAsJsonObject();
    
    //INFO REGARDING RATE LIMITATIONS
    printAPIRateLimitations(result);
    
    Set<String> timePeriods = windows.keySet();

    CandleStickSeries candleStickSeries = new CandleStickSeries();
    for (String timePeriod : timePeriods) {
      JsonArray dataPoints = windows.get(timePeriod).getAsJsonArray();

      TimeSeries periodTimeSeries = new TimeSeries();
      for (JsonElement dataPointEle : dataPoints) {
        JsonArray dataPointObj = dataPointEle.getAsJsonArray();
        
        long timestamp = dataPointObj.get(0).getAsLong();
        double open = dataPointObj.get(1).getAsDouble();
        double high = dataPointObj.get(2).getAsDouble();
        double low = dataPointObj.get(3).getAsDouble();
        double close = dataPointObj.get(4).getAsDouble();
        double volume = dataPointObj.get(5).getAsDouble();
        
        CandleStickData csd = new CandleStickData(timestamp, open, high, low, close, volume);
        periodTimeSeries.addPoint(csd);
      }

      long timePeriodD = Long.parseLong(timePeriod);

      candleStickSeries.addPeriod(timePeriodD, periodTimeSeries);
    }

    return candleStickSeries;
  }

//  public JsonObject getAggregateMarketData() {   
//    String url = "https://api.cryptowat.ch/markets/prices";
//    JsonObject result = executeCall(url);
//    return result;
//  }
//
//  public JsonObject getAggregateMarketSummaries() {   
//    String url = "https://api.cryptowat.ch/markets/summaries";
//
//    JsonObject result = executeCall(url);
//    return result;
//  }

}
