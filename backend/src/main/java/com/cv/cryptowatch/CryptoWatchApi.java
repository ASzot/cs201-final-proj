package com.cv.cryptowatch;

import java.util.ArrayList;
import java.util.List;

import com.cv.model.CandleStickSeries;
import com.cv.model.CurrencyTicker;
import com.cv.model.Market;
import com.cv.model.MarketSummary;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class CryptoWatchApi {
  private CryptoWatchRest rest;

  public CryptoWatchApi(CryptoWatchRest rest) {
    this.rest = rest;
  }

  //print information regarding remaining time before API rate limitation
  private void printAPIRateLimitations(JsonObject result) {
    JsonObject rateInfo = result.get("allowance").getAsJsonObject();
    String cost = rateInfo.get("cost").getAsString();
    String allowance = rateInfo.get("remaining").getAsString();
    System.out.println("Cost: " + cost + " Allowance remaining: " + allowance);
  }

  public List<Market> getMarkets() {
    String endpoint = "/markets";
    JsonObject result = rest.get(endpoint);
    if (result == null) {
      return null;
    }

    JsonArray exchanges = result.get("result").getAsJsonArray();

    List<Market> markets = new ArrayList<Market>();
    for (JsonElement exchange : exchanges) {
      JsonObject exchangeObj = exchange.getAsJsonObject();
      boolean active = exchangeObj.get("active").getAsBoolean();
      String exchangeStr = exchangeObj.get("exchange").getAsString();
      String pair = exchangeObj.get("pair").getAsString();

      if (!active) {
        continue;
      }

      if (pair.length() != 6) {
        continue;
      }

      String fromStr = pair.substring(0, 3);
      String toStr = pair.substring(3, 6);

      CurrencyTicker from = new CurrencyTicker(-1, fromStr);
      CurrencyTicker to = new CurrencyTicker(-1, toStr);

      markets.add(new Market(from, to, exchangeStr));
    }

    printAPIRateLimitations(result);
    return markets;
  }
  
  public CandleStickSeries getCandlestick(String market, String exchange, List<String> periods, long before, long after) {   
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

    String endpoint = "/markets/" + exchange + "/" + market + "/ohlc" + paramsStr;
    System.out.println("Endpoint is");
    System.out.println(endpoint);

    JsonObject result = rest.get(endpoint);
    if (result == null) {
      return null;
    }

    JsonObject windows = result.get("result").getAsJsonObject();
    //INFO REGARDING RATE LIMITATIONS
    printAPIRateLimitations(result);
    
    CandleStickSeries css = CandleStickSeries.fromJson(windows);
    return css;
  }

  public MarketSummary getMarketSummary(String fromCur, String toCur, String exchange, Gson gson) {
    String endpoint = "/markets/" + exchange + "/" + fromCur + toCur + "/summary";
    JsonObject result = rest.get(endpoint);
    String jsonStr = result.get("result").getAsJsonObject().toString();
    System.out.println("Json string " + jsonStr);
    return gson.fromJson(jsonStr, MarketSummary.class);
  }
}
