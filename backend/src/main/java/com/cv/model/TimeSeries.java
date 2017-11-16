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
}
