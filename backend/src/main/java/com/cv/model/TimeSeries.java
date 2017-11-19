package com.cv.model;

import java.util.List;
import java.util.ArrayList;

public class TimeSeries {
  protected List<TimeSeriesPoint> timeSeriesPoints = new ArrayList<TimeSeriesPoint>();

  public void addPoint(TimeSeriesPoint point) {
    timeSeriesPoints.add(point);
  }

  public List<TimeSeriesPoint> getTimeSeriesPoints() {
    return timeSeriesPoints;
  }
  
  public Integer size() {
    return timeSeriesPoints.size();
  }
  
  public TimeSeriesPoint get(Integer i) {
    return timeSeriesPoints.get(i);
  }
  
  public void print() {
    System.out.println("Moving Averages: ");
    for (TimeSeriesPoint point : timeSeriesPoints) {
      MovingAveragePoint map = (MovingAveragePoint) point;
      System.out.println("Timestamp: " + point.getDateStr() + " Average: " + map.getAverage());
    }
  }
}
