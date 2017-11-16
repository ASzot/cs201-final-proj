package com.cv;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class TestData {
  /**
   * API Handlers
   */
  private ResponseHandler<JsonObject> responseHandler;
  private CloseableHttpClient httpclient;

  /******** METHODS **************/

  public TestData(String testMsg, String name) {
    //Create client and response handler for calls to API
    httpclient = HttpClients.createDefault();
    responseHandler = new ResponseHandler<JsonObject>() {
      @Override
      public JsonObject handleResponse(final HttpResponse response) throws IOException {
        StatusLine statusLine = response.getStatusLine();
        HttpEntity entity = response.getEntity();
        if (statusLine.getStatusCode() >= 300) {
          throw new HttpResponseException(
              statusLine.getStatusCode(),
              statusLine.getReasonPhrase());
        }
        if (entity == null) {
          System.out.println("No content");
          throw new ClientProtocolException("Response contains no content");
        }

        Gson gson = new GsonBuilder().create();

        Reader reader = new InputStreamReader(entity.getContent());
        return gson.fromJson(reader, JsonObject.class);
      }
    };
  }

  /**
   * Helper method to execute the HTTP call
   * @param httpGet the HTTP Get containing the desired API call
   * @return JsonObject containing the API call results
   */
  private JsonObject executeCall(String url) {
    HttpGet httpGet = new HttpGet(url);
    JsonObject result = null;
    try {
      result = null;
      result = httpclient.execute(httpGet, responseHandler);
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return result;
  }

  /**
   * Method to return all supported exchanges
   * @return JsonObject containing the supported exchanges
   */
  public JsonObject getExchanges() {   
    String url = "https://api.cryptowat.ch/assets";

    JsonObject result = executeCall(url);
    return result;
  }

  /**
   * Method to return all supported markets and accompanying URL for market data
   * @return JsonObject containing the supported exchanges
   */
  public JsonObject getMarkets() {   
    String url = "https://api.cryptowat.ch/markets";
    JsonObject result = executeCall(url);
    return result;
  }


  //Markets: ltcusd, btcusd, ltcbtc
  public JsonObject getMarketDataURLs(String market) {   
    String url = "https://api.cryptowat.ch/markets/gdax/" + market;
    JsonObject result = executeCall(url);
    return result;
  }

  //Markets: ltcusd, btcusd, ltcbtc
  public JsonObject getLastPrice(String market) {   
    String url = "https://api.cryptowat.ch/markets/gdax/" + market + "/price";
    JsonObject result = executeCall(url);
    return result;
  }

  //Markets: ltcusd, btcusd, ltcbtc
  public JsonObject getCurrencySummary(String market) {   
    String url = "https://api.cryptowat.ch/markets/gdax/" + market + "/summary";

    JsonObject result = executeCall(url);
    return result;
  }

  public JsonObject getTrades(String market, int limitTrades) {   
    String url = "https://api.cryptowat.ch/markets/gdax/" + market + "/trades";
    String params = "?limit=" + limitTrades;
    url += params;

    JsonObject result = executeCall(url);
    return result;
  }

  public JsonObject getOrders(String market) {   
    String url = "https://api.cryptowat.ch/markets/gdax/" + market + "/orderbook";
    JsonObject result = executeCall(url);
    return result;
  }

  public JsonObject getCandlestick(String market) {   
    String url = "https://api.cryptowat.ch/markets/gdax/" + market + "/ohlc";
    JsonObject result = executeCall(url);
    return result;
  }

  public JsonObject getAggregateMarketData() {   
    String url = "https://api.cryptowat.ch/markets/prices";
    JsonObject result = executeCall(url);
    return result;
  }

  public JsonObject getAggregateMarketSummaries() {   
    String url = "https://api.cryptowat.ch/markets/summaries";

    JsonObject result = executeCall(url);
    return result;
  }

}
