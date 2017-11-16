package com.cv.model;

import java.util.HashMap;
import java.util.Map;

public class CandleStickSeries {
  private Map<Long, TimeSeries> periods = new HashMap<Long, TimeSeries>();


  public void addPeriod(long period, TimeSeries timeSeries) {
    periods.put(period, timeSeries);
  }

  public Map<Long, TimeSeries> getPeriods() {
    return periods;
  }
}
