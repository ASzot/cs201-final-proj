package com.cv.movingAverages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.cv.cryptowatch.CryptoWatchApi;
import com.cv.cryptowatch.CryptoWatchRest;
import com.cv.model.CandleStickPoint;
import com.cv.model.CandleStickSeries;
import com.cv.model.MovingAveragePoint;
import com.cv.model.TimeSeries;
import com.cv.model.TimeSeriesPoint;

public class MovingAverageCalculator {
//  @Autowired
//  private AppContext appContext;
  //App Context not working^
  private List<Integer> dayIntervals;
  private long duration; 
  private long startGraphTimestamp;
  private long endGraphTimestamp;
  private String market;
  private CryptoWatchApi cryptoWatchApi;
  private List<String> dayPeriod; //dummy list to only hold epoch value of 1 day for moving average API Callcalculation
  private Map<Integer, TimeSeries> series;
  private String exchange;
  private List<Double> dayPricesCache;
  
  public MovingAverageCalculator(long currentUnixTime, long duration, String exchange, List<Integer> dayIntervals, String fromCur, String toCur) {
    series = new HashMap<Integer, TimeSeries>();
    this.dayIntervals = dayIntervals;
    this.duration = duration;
    this.startGraphTimestamp = currentUnixTime - duration; //graph from 1 year, 1 day ago
    this.endGraphTimestamp =  currentUnixTime; //to 1 day ago    - Constants.DAY_UNIX
    this.market = fromCur + toCur;
    dayPeriod = new ArrayList<String>();
    dayPeriod.add(String.valueOf(Constants.DAY_UNIX));
    this.exchange = exchange; 
    dayPricesCache = new ArrayList<Double>();

    //Begin and execute all threads
    fillDayPriceCache(startGraphTimestamp, endGraphTimestamp);
    System.out.println("DayPrice cache: " + dayPricesCache.size());
    ExecutorService executors = Executors.newCachedThreadPool();
    for (Integer interval : dayIntervals) {
      TimeSeries ts = new TimeSeries();
      series.put(interval, ts);
      MovingAverageThread mat = new MovingAverageThread(exchange, interval, ts, startGraphTimestamp, endGraphTimestamp, fromCur, toCur, dayPricesCache);
      executors.execute(mat);
    }
    executors.shutdown();
    while (!executors.isTerminated()) {
        Thread.yield();
    }
  }

  public Map<Integer, TimeSeries> getSeries() {
    //Remove error series
    for (Integer key : series.keySet()) {
      if (series.get(key).size() == 1 && ((MovingAveragePoint)(series.get(key).getTimeSeriesPoints().get(0))).getAverage() == 0.0) {
        series.put(key, new TimeSeries());
      }
    }
    return series;
  }
  
  //Returns the sum of prices over an interval specified by [startInterval, endInterval]
  private void fillDayPriceCache(long startInterval, long endInterval) {
    CandleStickSeries candles = getCrypowatchApi().getCandlestick(market, exchange, dayPeriod, endInterval, startInterval);
    if (candles == null) return;
    TimeSeries ts = candles.getPeriods().get(Constants.DAY_UNIX);
    List<TimeSeriesPoint> points = ts.getTimeSeriesPoints();
    
    for (TimeSeriesPoint point : points) {
      if (point instanceof CandleStickPoint) {
        CandleStickPoint csp = (CandleStickPoint)point;
        dayPricesCache.add(csp.getClose());
      }
    }
  }
  
//  private Long nextDay(Long timestamp) {
//    return timestamp + Constants.DAY_UNIX;
//  }
  
  private CryptoWatchApi getCrypowatchApi() {
    if (cryptoWatchApi == null) {
      cryptoWatchApi = new CryptoWatchApi(new CryptoWatchRest());
    }
    return cryptoWatchApi;
  }
}

//  //calculates moving averages for one series
//  public void calculateIntervalSeries(int dayInterval, TimeSeries ts) {
//    Long secondsInterval = dayInterval * Constants.DAY_UNIX;
//    Long startInterval = startGraphTimestamp;
//    Long endInterval = startGraphTimestamp + secondsInterval;
//    Long seriesEnd = endGraphTimestamp;
//    Map<Long, Double> dayPricesCache = new HashMap<Long, Double>();
//
//    Double prevIntervalSum = calculateInitialSum(startInterval, endInterval, dayPricesCache); 
//    Double prevStartPrice = 0.0;
//    if (dayPricesCache.containsKey(startInterval)) {
//      prevStartPrice = dayPricesCache.get(startInterval);
//    }
//    else {
//      prevStartPrice = getPrice(startInterval);
//      dayPricesCache.put(startInterval, prevStartPrice);
//    }
//    Double average = calculateAverage(prevIntervalSum, dayInterval);
//    MovingAveragePoint movingAvgPoint = new MovingAveragePoint(startInterval, average);
//    ts.addPoint(movingAvgPoint);
//    
//    startInterval = nextDay(startInterval);
//    endInterval = nextDay(endInterval);
//
//    while (endInterval <= seriesEnd) {
//      //get the price of the last day for this interval
//      Double endIntervalPrice = getPrice(endInterval);
//      dayPricesCache.put(endInterval, endIntervalPrice);
//      
//      //transform previous interval sum to calculate this interval sum
//      Double currIntervalSum = prevIntervalSum - prevStartPrice + endIntervalPrice;
//      if (currIntervalSum < 0) {
//       currIntervalSum = prevIntervalSum;
//      }
//      Double intervalAverage = calculateAverage(currIntervalSum, dayInterval);
//      MovingAveragePoint nextPoint = new MovingAveragePoint(startInterval, intervalAverage);
//      ts.addPoint(nextPoint);
//      
//      //prepare for next interval
//      prevIntervalSum = currIntervalSum;
//      if (dayPricesCache.containsKey(startInterval)) {
//        prevStartPrice = dayPricesCache.get(startInterval);
//      }
//      else {
//        prevStartPrice = getPrice(startInterval);
//        dayPricesCache.put(startInterval, prevStartPrice);
//      }
//      
//      startInterval = nextDay(startInterval);
//      endInterval = nextDay(endInterval);
//    }
//  }
//  
//  //Returns the sum of prices over an interval specified by [startInterval, endInterval]
//  private Double calculateInitialSum(long startInterval, long endInterval, Map<Long, Double> prices) {
//    CandleStickSeries candles = getCrypowatchApi().getCandlestick(market, dayPeriod, endInterval, startInterval);
//    Double intervalSum = 0.0;
//    long currentTimestamp = startInterval;
//    TimeSeries ts = candles.getPeriods().get(Constants.DAY_UNIX);
//    List<TimeSeriesPoint> points = ts.getTimeSeriesPoints();
//    
//    for (TimeSeriesPoint point : points) {
//      if (point instanceof CandleStickPoint) {
//        CandleStickPoint csp = (CandleStickPoint)point;
//        intervalSum += csp.getClose();
//        prices.put(currentTimestamp, csp.getClose());
//      }
//      else {
//        intervalSum += 0;
//      }
//      currentTimestamp = nextDay(currentTimestamp);
//    }
//    
//    return intervalSum; 
//  }
//  
//  private Long nextDay(Long timestamp) {
//    return timestamp + Constants.DAY_UNIX;
//  }
//  
//  private Double calculateAverage(Double sum, long dayInterval) {
//    return sum / dayInterval;
//  }
//  
//  //Returns the closing price for a single day : [timestamp, timestamp + 1 day]
//  private Double getPrice(long timestamp) {
//    long begin = timestamp;
//    long end = timestamp + Constants.DAY_UNIX;    
//    
//    CandleStickSeries candles = getCrypowatchApi().getCandlestick(market, dayPeriod, end, begin);
//    if (candles == null || candles.getPeriods().size() > 1) {
//      return 0.0;
//    }
//    
//    //Will return 2 values -- only want to take the first one because corresponds to start interval**
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
//  
//  private CryptoWatchApi getCrypowatchApi() {
//    if (cryptoWatchApi == null) {
//      cryptoWatchApi = new CryptoWatchApi(new CryptoWatchRest());
//    }
//    return cryptoWatchApi;
//  }



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