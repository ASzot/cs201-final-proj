package com.cv.model;


class ChangeSummary {
  private double percentage;
  private double absolute;

  public double getPercentage() {
    return percentage;
  }
  public double getAbsolute() {
    return absolute;
  }
}

class PriceSummary {
  private double last;
  private double high;
  private double low;
  private ChangeSummary change;

  public double getLast() {
    return last;
  }
  public double getHigh() {
    return high;
  }
  public double getLow() {
    return low;
  }
  public ChangeSummary getChange() {
    return change;
  }
}

public class MarketSummary {
  private PriceSummary price;
  private double volume;

  public PriceSummary getPrice() {
    return price;
  }
  public double getVolume() {
    return volume;
  }
}
