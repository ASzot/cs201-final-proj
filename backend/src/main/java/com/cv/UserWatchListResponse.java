package com.cv;
import java.util.Vector;

public class UserWatchListResponse {
  private Vector<String> allCurrencies;

  public UserWatchListResponse(Vector<String> allCurrencies) {
    this.allCurrencies = allCurrencies; 
  }

  public Vector<String> getCurrencies() {
    return allCurrencies;
  }

  public void setCurrencies(Vector<String> allCurrencies){
    this.allCurrencies = allCurrencies; 
  }
 
}
