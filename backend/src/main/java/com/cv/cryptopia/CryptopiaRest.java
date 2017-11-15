package com.cv.cryptopia;

import org.apache.http.client.methods.HttpGet;

import com.cv.util.RestUtil;

public class CryptopiaRest extends RestUtil {
  private String baseUrl = "https://www.cryptopia.co.nz/api";

  public CryptopiaRest() {

  }

  protected HttpGet generateGet(String endpoint) {
    HttpGet get = new HttpGet(baseUrl + endpoint);

    return get;
  }
}
