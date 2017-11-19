package com.cv.model;

import java.util.List;
import java.util.stream.Collectors;

public class Market {
  private CurrencyTicker fromCur;
  private CurrencyTicker toCur;
  private String exchange;

  public Market(CurrencyTicker fromCur, CurrencyTicker toCur, String exchange) {
    this.fromCur = fromCur;
    this.toCur = toCur;
    this.exchange = exchange;
  }

  public Market() {
    this(null, null, "");
  }

  public String getToTicker() {
    return this.toCur.getTicker();
  }

  public String getExchange() {
    return this.exchange;
  }

  public CurrencyTicker getFromCur() {
    return fromCur;
  }

  public CurrencyTicker getToCur() {
    return toCur;
  }

  public static List<Market> filter(List<Market> markets, List<String> allowedTos) {
    List<Market> allowedToMarkets = markets.stream()
      .filter(m -> allowedTos.contains(m.getToTicker()))
      .collect(Collectors.toList());

    return allowedToMarkets;
  }
}
