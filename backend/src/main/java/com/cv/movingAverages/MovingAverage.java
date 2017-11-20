package com.cv.movingAverages;

import java.util.ArrayList; 
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.cv.AppContext;
import com.cv.cryptowatch.CryptoWatchApi;
import com.cv.cryptowatch.CryptoWatchRest;
import com.cv.model.CandleStickPoint;
import com.cv.model.CandleStickSeries;
import com.cv.model.MovingAveragePoint;
import com.cv.model.TimeSeries;
import com.cv.model.TimeSeriesPoint;

public class MovingAverage {
  @Autowired
  private AppContext appContext;

//  private List<Integer> dayIntervals; //30, 50, 100
  private long graphTimeframe; //pass in one year in epoch
  private long startGraphTimestamp;
  private long endGraphTimestamp;
  private String market;
  private CryptoWatchApi cryptoWatchApi;
  private List<String> dayPeriod;
  private Map<Integer, TimeSeries> series;
  
  //ArrayList<Integer> dayIntervals, 
  public MovingAverage(long graphTimeframe, String fromCur, String toCur) {
    series = new HashMap<Integer, TimeSeries>();
//    this.dayIntervals = dayIntervals;
    this.graphTimeframe = graphTimeframe;
    this.startGraphTimestamp = System.currentTimeMillis()/1000 - Constants.DAY_UNIX - graphTimeframe; //graph from 1 year, 1 day ago
    this.endGraphTimestamp =  System.currentTimeMillis()/1000 - Constants.DAY_UNIX; //to 1 day ago
    
    this.market = fromCur + toCur;
    dayPeriod = new ArrayList<String>();
    dayPeriod.add(String.valueOf(Constants.DAY_UNIX));
  }
  
  public Map<Integer, TimeSeries> calculateSeries(Integer interval) {
//    for (Integer interval : dayIntervals) {
      TimeSeries ts = new TimeSeries();
      series.put(interval, ts);
      calculateIntervalSeries(interval, ts);
//    }
    
    return series;
  }
  
  //calculates moving averages for one series
  public void calculateIntervalSeries(int dayInterval, TimeSeries ts) {
    System.out.println("Checkpoint1");
    Long secondsInterval = dayInterval * Constants.DAY_UNIX;
    Long startInterval = startGraphTimestamp;
    Long endInterval = startGraphTimestamp + secondsInterval;
    Long seriesEnd = endGraphTimestamp;
    Map<Long, Double> dayPrices = new HashMap<Long, Double>();

    //First day calculations
    System.out.println("Checkpoint2");

    Double prevIntervalSum = calculateInitialSum(startInterval, endInterval, dayPrices); 
    Double prevStartPrice = 0.0;
    System.out.println("Does dayPrices contain startInterval?? : " + dayPrices.containsKey(startInterval));
    if (dayPrices.containsKey(startInterval)) {
      prevStartPrice = dayPrices.get(startInterval);
    }
    else {
      Double prevStartPriceRaw = getPrice(startInterval);
      prevStartPrice = (prevStartPriceRaw != null) ? prevStartPriceRaw : 0.0;
      dayPrices.put(startInterval, prevStartPrice);
    }
    Double average = calculateAverage(prevIntervalSum, dayInterval);
    MovingAveragePoint movingAvgPoint = new MovingAveragePoint(startInterval, average);
    ts.addPoint(movingAvgPoint);
    
    startInterval += Constants.DAY_UNIX;
    endInterval += Constants.DAY_UNIX;
    System.out.println("Checkpoint3");

    while (endInterval <= seriesEnd) {
      System.out.println("Checkpoint4");

      Double endIntervalPriceRaw = getPrice(endInterval);
      Double endIntervalPrice = (endIntervalPriceRaw != null) ? endIntervalPriceRaw : 0.0;
      dayPrices.put(endInterval, endIntervalPrice);
//      if (dayPrices.containsKey(endInterval)) {
//        endIntervalPrice = dayPrices.get(endInterval);
//      }
//      else {
//        endIntervalPrice = getPrice(endInterval);
//        dayPrices.put(endInterval, endIntervalPrice);
//      }
      
      Double currIntervalSum = prevIntervalSum - prevStartPrice + endIntervalPrice;
      Double intervalAverage = calculateAverage(currIntervalSum, dayInterval);
      
      //createTimeSeriesPoint
      MovingAveragePoint nextPoint = new MovingAveragePoint(startInterval, intervalAverage);
      ts.addPoint(nextPoint);
      
      prevIntervalSum = currIntervalSum;
      if (dayPrices.containsKey(startInterval)) {
        prevStartPrice = dayPrices.get(startInterval);
      }
      else {
        Double prevStartPriceRaw = getPrice(startInterval);
        prevStartPrice = (prevStartPriceRaw != null) ? prevStartPriceRaw : 0.0;
        dayPrices.put(startInterval, prevStartPrice);
      }
      
      startInterval += Constants.DAY_UNIX;
      endInterval += Constants.DAY_UNIX;
    }
  }
  
  //Returns the sum of prices over an interval specified by [startInterval, endInterval]
  private Double calculateInitialSum(long startInterval, long endInterval, Map<Long, Double> prices) {
    System.out.println("Checkpoint5");

    //TODO:
    // Pass in exchange as parameter.
    CandleStickSeries candles = getCrypowatchApi().getCandlestick(market, "gdax", dayPeriod, endInterval, startInterval);
    if (candles.getPeriods().size() > 1) {
      System.out.println("ERROR WITH API Call");
      return null;
    }
    
    //Get TimeSeries from API call
    TimeSeries ts = null;
    Double intervalSum = 0.0;
    
    ts = candles.getPeriods().get(Constants.DAY_UNIX);
    long currentTimestamp = startInterval;
    List<TimeSeriesPoint> points = ts.getTimeSeriesPoints();
    for (TimeSeriesPoint point : points) {
      if (point instanceof CandleStickPoint) {
        CandleStickPoint csp = (CandleStickPoint)point;
//        System.out.println("CurrentTimestamp: " + currentTimestamp + " Price: " + csp.getClose());
        intervalSum += csp.getClose();
        prices.put(currentTimestamp, csp.getClose());
        currentTimestamp += Constants.DAY_UNIX;
      }
      else {
        System.out.println("Error with logic in calc initial sum");
      }
    }
    
    return intervalSum; 
  }
  
  private void incrementDay(Long timestamp) {
    timestamp += Constants.DAY_UNIX;
  }
  
  private Double calculateAverage(Double sum, long dayInterval) {
    return sum / dayInterval;
  }
  
  //Returns the closing price for a single day : [timestamp, timestamp + 1 day]
  private Double getPrice(long timestamp) {
    System.out.println("Checkpoint6");

    long begin = timestamp;
    long end = timestamp + Constants.DAY_UNIX;    
    
    //TODO:
    // Pass in exchange name
    CandleStickSeries candles = getCrypowatchApi().getCandlestick(market, "gdax", dayPeriod, end, begin);
    if (candles == null || candles.getPeriods().size() > 1 || candles.getPeriods().get(Constants.DAY_UNIX).size() > 1) {
      System.out.println("EMPTY RESPONSE OR TOO MANY RESPONSE FOR CANDLESTICK GET PRICE IN MOV AVG");
      return null;
    }
    
    CandleStickPoint csp;
    //at this point, candleStickSeries guaranteed to have only 1 entry for its 1 period
    if (candles.getPeriods().get(Constants.DAY_UNIX).get(0) instanceof CandleStickPoint) {
      csp = (CandleStickPoint)candles.getPeriods().get(Constants.DAY_UNIX).get(0);
    }
    else {
      return null; //error
    }
    
    System.out.println("CSP: Timestamp: " + csp.getTimestamp() + " Price: " + csp.getClose());

    return csp.getClose();
  }
  
  private CryptoWatchApi getCrypowatchApi() {
    if (cryptoWatchApi == null) {
      cryptoWatchApi = new CryptoWatchApi(new CryptoWatchRest());
    }
    return cryptoWatchApi;
  }
}


//
//class SumData {
//  private Double firstDayPrice;
//  private Double intervalSum;
//  
//  public SumData (Double firstDayPrice, Double intervalSum) {
//    this.firstDayPrice = firstDayPrice;
//    this.intervalSum = intervalSum;
//  }
//  
//  public Double getFirstDayPrice() {
//    return firstDayPrice;
//  }
//  public void setFirstDayPrice(Double firstDayPrice) {
//    this.firstDayPrice = firstDayPrice;
//  }
//  public Double getIntervalSum() {
//    return intervalSum;
//  }
//  public void setIntervalSum(Double intervalSum) {
//    this.intervalSum = intervalSum;
//  }
//  
//}
