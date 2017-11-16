package com.cv.cryptowatch;

import org.apache.http.client.methods.HttpGet;

import com.cv.util.RestUtil;

public class CryptoWatchRest extends RestUtil {
  private String baseUrl = "https://api.cryptowat.ch";

  public CryptoWatchRest() {

  }

  protected HttpGet generateGet(String endpoint) {
    HttpGet get = new HttpGet(baseUrl + endpoint);

    return get;
  }
}
