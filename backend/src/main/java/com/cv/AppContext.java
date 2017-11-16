package com.cv;

import org.springframework.stereotype.Repository;

import com.google.gson.Gson;

@Repository
public class AppContext {
  private Gson gson;

  public AppContext() {
    this.gson = new Gson();
  }

  public Gson getGson() {
    return this.gson;
  }
}
