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
  
  private CryptoWatchApi getCrypowatchApi() {
    if (cryptoWatchApi == null) {
      cryptoWatchApi = new CryptoWatchApi(new CryptoWatchRest());
    }
    return cryptoWatchApi;
  }
}
