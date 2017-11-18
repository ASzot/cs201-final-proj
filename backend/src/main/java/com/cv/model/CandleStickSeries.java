package com.cv.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class CandleStickSeries {
  private Map<Long, TimeSeries> periods = new HashMap<Long, TimeSeries>();

  public void addPeriod(long period, TimeSeries timeSeries) {
    periods.put(period, timeSeries);
  }

  public Map<Long, TimeSeries> getPeriods() {
    return periods;
  }
  
  public static CandleStickSeries fromJson(JsonObject windows) {
    Set<String> timePeriods = windows.keySet();

    CandleStickSeries candleStickSeries = new CandleStickSeries();
    for (String timePeriod : timePeriods) {
      JsonArray dataPoints = windows.get(timePeriod).getAsJsonArray();

      TimeSeries periodTimeSeries = new TimeSeries();
      for (JsonElement dataPointEle : dataPoints) {
        JsonArray dataPointObj = dataPointEle.getAsJsonArray();
        
        long timestamp = dataPointObj.get(0).getAsLong();
        double open = dataPointObj.get(1).getAsDouble();
        double high = dataPointObj.get(2).getAsDouble();
        double low = dataPointObj.get(3).getAsDouble();
        double close = dataPointObj.get(4).getAsDouble();
        double volume = dataPointObj.get(5).getAsDouble();
        
        CandleStickPoint csd = new CandleStickPoint(timestamp, open, high, low, close, volume);
        periodTimeSeries.addPoint(csd);
      }

      long timePeriodD = Long.parseLong(timePeriod);

      candleStickSeries.addPeriod(timePeriodD, periodTimeSeries);
    }
    
    return candleStickSeries;
  }
  
}
