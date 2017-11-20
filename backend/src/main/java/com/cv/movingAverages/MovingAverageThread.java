package com.cv.movingAverages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cv.cryptowatch.CryptoWatchApi;
import com.cv.cryptowatch.CryptoWatchRest;
import com.cv.model.CandleStickPoint;
import com.cv.model.CandleStickSeries;
import com.cv.model.MovingAveragePoint;
import com.cv.model.TimeSeries;
import com.cv.model.TimeSeriesPoint;

public class MovingAverageThread implements Runnable {
  private String exchange;
  private int dayInterval;
  private TimeSeries ts;
  private Long secondsInterval;
  private Long startInterval;
  private Long endInterval;
  private Long seriesEnd;
  private List<Double> dayPricesCache;
  private String market;
  private CryptoWatchApi cryptoWatchApi;
  private List<String> dayPeriod; //dummy list to only hold epoch value of 1 day for moving average API Call calculation
  
  public MovingAverageThread(String exchange, int dayInterval, TimeSeries ts, long startGraphTimestamp, long endGraphTimestamp, String fromCur, String toCur, List<Double> dayPricesCache) {
//    System.out.println("dayPricesCache size: " + dayPricesCache.size());
    this.dayPricesCache = dayPricesCache;
//    System.out.println("After copy dayPricesCache size: " + this.dayPricesCache.size());

    this.exchange = exchange; 
    this.dayInterval = dayInterval;
    this.ts = ts;
    secondsInterval = dayInterval * Constants.DAY_UNIX;
    startInterval = startGraphTimestamp;
    endInterval = startGraphTimestamp + secondsInterval;
    seriesEnd = endGraphTimestamp;
    dayPricesCache = new ArrayList<Double>();
    this.market = fromCur + toCur;
    dayPeriod = new ArrayList<String>();
    dayPeriod.add(String.valueOf(Constants.DAY_UNIX));
  }
  
  public void run () {
    Double prevIntervalSum = calculateInitialSum(exchange, dayInterval); 
    Double prevStartPrice = 0.0;
    int startInterval = 0;
    int endInterval = startInterval + dayInterval;
    System.out.println("In run: dayPricesCache size: " + dayPricesCache.size());

    if (dayPricesCache.size() > 0) {
      prevStartPrice = dayPricesCache.get(0);
    }
    else {
      return;
    }
    System.out.println("After first access");
    Double average = calculateAverage(prevIntervalSum, dayInterval);
    MovingAveragePoint movingAvgPoint = new MovingAveragePoint(startInterval, average);
    ts.addPoint(movingAvgPoint);
    
    startInterval++;
    endInterval = startInterval + dayInterval;
    while (endInterval < dayPricesCache.size()) {
      //get the price of the last day for this interval
      Double endIntervalPrice = dayPricesCache.get(endInterval);
      
      //transform previous interval sum to calculate this interval sum
      Double currIntervalSum = prevIntervalSum - prevStartPrice + endIntervalPrice;
      if (currIntervalSum < 0) {
       currIntervalSum = prevIntervalSum;
      }
      Double intervalAverage = calculateAverage(currIntervalSum, dayInterval);
      MovingAveragePoint nextPoint = new MovingAveragePoint(startInterval, intervalAverage);
      ts.addPoint(nextPoint);
      
      //prepare for next interval
      prevIntervalSum = currIntervalSum;
      prevStartPrice = dayPricesCache.get(startInterval);
      
      startInterval++;
      endInterval = startInterval + dayInterval;
    }
  }
  
  //Returns the sum of prices over an interval specified by [startInterval, endInterval]
  private Double calculateInitialSum(String exchange, int dayInterval) {
    Double intervalSum = 0.0;
    
   for (int i = 0; i < dayInterval; i++) {
      Double price = 0.0;
      if (i < dayPricesCache.size()) {
        price = dayPricesCache.get(i);
        intervalSum += price;
      }
     }

    return intervalSum; 
  }
  
//  private Long nextDay(Long timestamp) {
//    return timestamp + Constants.DAY_UNIX;
//  }
  
  private Double calculateAverage(Double sum, long dayInterval) {
    return (sum/dayInterval);
  }
  
//  //Returns the closing price for a single day : [timestamp, timestamp + 1 day]
//  private Double getPrice(String exchange, Long timestamp) {
//    long begin = timestamp;
//    long end = timestamp + Constants.DAY_UNIX;    
//    
//    CandleStickSeries candles = getCrypowatchApi().getCandlestick(market, exchange, dayPeriod, end, begin);
//    if (candles == null || candles.getPeriods().size() > 1) {
//      return 0.0;
//    }
//    
//    //Will return 2 values -- only want to take the first one because corresponds to start interval
//    CandleStickPoint csp = null;
//    if (candles.getPeriods().get(Constants.DAY_UNIX).get(0) instanceof CandleStickPoint) {
//      csp = (CandleStickPoint)candles.getPeriods().get(Constants.DAY_UNIX).get(0);
////      System.out.println("CSP: Timestamp: " + csp.getTimestamp() + " Price: " + csp.getClose());
//    }
//    else {
//      return 0.0; //error
//    }
//
//    return csp.getClose();
//  }
  
  private CryptoWatchApi getCrypowatchApi() {
    if (cryptoWatchApi == null) {
      cryptoWatchApi = new CryptoWatchApi(new CryptoWatchRest());
    }
    return cryptoWatchApi;
  }
}
