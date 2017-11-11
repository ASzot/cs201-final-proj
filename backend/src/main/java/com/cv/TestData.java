package com.cv;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class TestData {
  private final String testMsg;
  private final String name;

  /**
   * API Handlers
   */
  private ResponseHandler<JsonObject> responseHandler;
  private CloseableHttpClient httpclient;
  private String X_API_KEY_STRING = "211c66b10d53944dcd6e69b181d078d0";
  private String X_API_KEY_SECRET_STRING = "ea5311c878ecdbe7d4587ef668b12d5d";
  
  /******** METHODS **************/
	
  public TestData(String testMsg, String name) {
    this.testMsg = testMsg;
    this.name = name;
    
    //Create client and response handler for calls to API
    httpclient = HttpClients.createDefault();
    responseHandler = new ResponseHandler<JsonObject>() {
      @Override
      public JsonObject handleResponse(
              final HttpResponse response) throws IOException {
          StatusLine statusLine = response.getStatusLine();
          HttpEntity entity = response.getEntity();
          if (statusLine.getStatusCode() >= 300) {
              throw new HttpResponseException(
                      statusLine.getStatusCode(),
                      statusLine.getReasonPhrase());
          }
          if (entity == null) {
              throw new ClientProtocolException("Response contains no content");
      }
      Gson gson = new GsonBuilder().create();

      Reader reader = new InputStreamReader(entity.getContent());
      return gson.fromJson(reader, JsonObject.class);
  }
};
  }

  public String getTestMsg() {
    return testMsg;
  }

  public String getName() {
    return name;
  }
  
  /**
   * Helper method to execute the HTTP call
   * @param httpPost the HTTP Post containing the desired API call
   * @return JsonObject containing the API call results
   */
  private JsonObject executeCall(HttpPost httpPost) {
    JsonObject result = null;
    try {
      result = null;
      result = httpclient.execute(httpPost, responseHandler);
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
  public JsonObject retrieveExchanges() {   
    HttpPost httpPost = new HttpPost(APICall.EXCHANGES_URL);
    httpPost.addHeader("content-type", APICall.APPLICATION_JSON);
    httpPost.addHeader("X-API-KEY", X_API_KEY_STRING);
    httpPost.addHeader("X-API-SECRET", X_API_KEY_SECRET_STRING);
    
    JsonObject result = executeCall(httpPost);
    return result;
  }
  
  /**
   * Method to return all supported markets
   * @return JsonObject containing the supported markets
   */
  public JsonObject retrieveMarkets() {   
    HttpPost httpPost = new HttpPost(APICall.MARKETS_URL);
    httpPost.addHeader("content-type", APICall.APPLICATION_JSON);
    httpPost.addHeader("X-API-KEY", X_API_KEY_STRING);
    httpPost.addHeader("X-API-SECRET", X_API_KEY_SECRET_STRING);
    
    ArrayList<NameValuePair> bodyParameters = new ArrayList<NameValuePair>();
    bodyParameters.add(new BasicNameValuePair("exchange_code", APICall.DEFAULT_EXCHANGE_CODE));
    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(bodyParameters, Consts.UTF_8);
    httpPost.setEntity(entity);
    
    JsonObject result = executeCall(httpPost);
    return result;
  }
  
  /** NOT WORKING CURRENTLY? DONT KNOW WHY
   * Method to return all market data for a single market (currency); type = "all"
   * @return JsonObject containing the market data
   */
  public JsonObject retrieveMarketData(String market) {   
    HttpPost httpPost = new HttpPost(APICall.MARKET_DATA_ALL_URL);
    httpPost.addHeader("content-type", APICall.APPLICATION_JSON);
    httpPost.addHeader("X-API-KEY", X_API_KEY_STRING);
    httpPost.addHeader("X-API-SECRET", X_API_KEY_SECRET_STRING);
    
    ArrayList<NameValuePair> bodyParameters = new ArrayList<NameValuePair>();
    bodyParameters.add(new BasicNameValuePair("exchange_code", APICall.DEFAULT_EXCHANGE_CODE));
    bodyParameters.add(new BasicNameValuePair("exchange_market", "BTC/USD"));
    bodyParameters.add(new BasicNameValuePair("type", "orders"));

    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(bodyParameters, Consts.UTF_8);
    httpPost.setEntity(entity);
    
    JsonObject result = executeCall(httpPost);
    return result;
  }
  
  
  
  
  
  

  
//  /**
//   * Private method to add typical headers to the POST call
//   * @param httpPost the POST call to add headers to 
//   */
//  private void addHeaders(HttpPost httpPost) {
//    httpPost.addHeader(APICall.CONTENT_TYPE_HEADER, APICall.APPLICATION_JSON);
//    httpPost.addHeader(APICall.X_API_KEY_HEADER, X_API_KEY_STRING);
//    httpPost.addHeader(APICall.X_API_KEY_SECRET_HEADER, X_API_KEY_SECRET_STRING);
//  }
//  
//  //Naming method getSupportedExchange throws exception?
//  //Not sure what is going on here
//  /**
//   * Returns a list of the supported exchanges in JSON format
//   * @return the JsonObject containing all supported exchanges on 
//   */
//  public JsonObject retrieveSupportedExchanges() {
//    HttpPost httpPost = new HttpPost(APICall.EXCHANGES_URL);
////    addHeaders(httpPost);
//    httpPost.addHeader(APICall.CONTENT_TYPE_HEADER, APICall.APPLICATION_JSON);
//    httpPost.addHeader(APICall.X_API_KEY_HEADER, X_API_KEY_STRING);
//    httpPost.addHeader(APICall.X_API_KEY_SECRET_HEADER, X_API_KEY_SECRET_STRING);
//    
//    responseHandler = new ResponseHandler<JsonObject>() {
//      @Override
//      public JsonObject handleResponse(
//              final HttpResponse response) throws IOException {
//          
//          StatusLine statusLine = response.getStatusLine();
//          HttpEntity entity = response.getEntity();
//          if (statusLine.getStatusCode() >= 300) {
//              throw new HttpResponseException(
//                      statusLine.getStatusCode(),
//                      statusLine.getReasonPhrase());
//          }
//          if (entity == null) {
//              throw new ClientProtocolException("Response contains no content");
//          }
//          Gson gson = new GsonBuilder().create();
//
//          Reader reader = new InputStreamReader(entity.getContent());
//          return gson.fromJson(reader, JsonObject.class);
//      }
//    };
//	JsonObject myjson = null;
//	try {
//		myjson = null;
//		myjson = httpclient.execute(httpPost, responseHandler);	
//	} catch (ClientProtocolException e) {
//		e.printStackTrace();
//	} catch (IOException e) {
//		e.printStackTrace();
//	}
//	
//    System.out.println(myjson);
//	return myjson;
//  }


  
}
