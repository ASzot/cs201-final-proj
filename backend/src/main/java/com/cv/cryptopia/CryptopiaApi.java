package com.cv.cryptopia;

import com.cv.model.TradeSeries;
import com.google.gson.JsonObject;

public class CryptopiaApi {
  private CryptopiaRest rest;

  public CryptopiaApi(CryptopiaRest rest) {
    this.rest = rest;
  }
  
  public TradeSeries getHistoricalData(String market) {   
    String endpoint = "/GetMarketHistory/" + market ;
    System.out.println("Endpoint is");
    System.out.println(endpoint);

    JsonObject result = rest.get(endpoint);
    if (result == null) {
      return null;
    }

    TradeSeries ts = TradeSeries.fromJson(result);
    return ts;
  }

}
