package com.cv;

import org.springframework.stereotype.Repository;

import com.cv.cryptowatch.CryptoWatchRest;
import com.google.gson.Gson;

@Repository
public class AppContext {
  private Gson gson;
  private CryptoWatchRest cwRest;

  public AppContext() {
    this.gson = new Gson();
    this.cwRest = new CryptoWatchRest();
  }

  public Gson getGson() {
    return this.gson;
  }

  public CryptoWatchRest getCwRest() {
    return cwRest;
  }
}
