package com.cv.model;

public class MovingAveragePoint extends TimeSeriesPoint {
  
  private Double average;
  
  public Double getAverage() {
    return average;
  }
  
  public void setAverage(Double average) {
    this.average = average;
  }
  
  public MovingAveragePoint(long timestamp, Double average) {
    super(timestamp);
    this.average = average;
  }
  
}
