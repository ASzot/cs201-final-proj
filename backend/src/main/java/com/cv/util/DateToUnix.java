package com.cv.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateToUnix {
  //Converts date string in format MM/dd/yyyy to UNIX long value
  static public long convert(String dateStr) {
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    Date date = null;
    try {
      date = dateFormat.parse(dateStr);
    } catch (ParseException e) {
      System.out.println("Incorrect date format");
    }
    long unixTime = (long) date.getTime()/1000;
    
    return unixTime;
  }
}
