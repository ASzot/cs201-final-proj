package com.cv;

import org.springframework.stereotype.Repository;

import com.cv.cryptopia.CryptopiaRest;
import com.cv.cryptowatch.CryptoWatchRest;
import com.google.gson.Gson;

@Repository
public class AppContext {
  private Gson gson;
  private CryptoWatchRest cwRest;
  private CryptopiaRest cryptopiaRest;
  
  public AppContext() {
    this.gson = new Gson();
    this.cwRest = new CryptoWatchRest();
    this.cryptopiaRest = new CryptopiaRest();
  }

  public Gson getGson() {
    return this.gson;
  }

  public CryptoWatchRest getCwRest() {
    return cwRest;
  }
  
  public CryptopiaRest getCryptopiaRest() {
    return cryptopiaRest;
  }
}
