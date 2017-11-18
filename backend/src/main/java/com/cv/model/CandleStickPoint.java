package com.cv.model;

public class CandleStickPoint extends TimeSeriesPoint {
  private double open;
  private double high;
  private double low;
  private double close;
  private double volume;

  public CandleStickPoint(long timestamp, double open, double high, double low, double close, double volume) {
    super(timestamp);
    this.open      = open;
    this.high      = high;
    this.low       = low;
    this.close     = close;
    this.volume    = volume;
  }

  public double getOpen() {
    return open;
  }

  public double getHigh() {
    return high;
  }

  public double getLow() {
    return low;
  }

  public double getClose() {
    return close;
  }

  public double getVolume() {
    return volume;
  }
}




