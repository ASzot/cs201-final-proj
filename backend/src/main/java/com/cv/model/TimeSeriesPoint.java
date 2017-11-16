package com.cv.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeSeriesPoint {
  protected long timestamp;
  protected String dateStr;

  public TimeSeriesPoint(long timestamp) {
    this.timestamp = timestamp;
    DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
    Date date = new Date(timestamp * 1000);
    this.dateStr = df.format(date);
  }

  public long getTimestamp() {
    return timestamp;
  }

  public String getDateStr() {
    return dateStr;
  }
}
