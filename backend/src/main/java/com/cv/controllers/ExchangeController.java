package com.cv.controllers;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cv.ApiException;
import com.cv.AppContext;
import com.cv.cryptopia.CryptopiaApi;
import com.cv.cryptowatch.CryptoWatchApi;
import com.cv.jdbc.get.GetCurrencyInformation;
import com.cv.model.CandleStickSeries;
import com.cv.model.CurrencyTicker;
import com.cv.model.Market;
import com.cv.model.MarketSummary;
import com.cv.model.TimeSeries;
import com.cv.model.TradeSeries;
import com.cv.movingAverages.MovingAverageCalculator;

@Controller
public class ExchangeController {
  @Autowired
  private AppContext appContext;

  private CryptoWatchApi cryptoWatchApi = null;
  private CryptopiaApi cryptopiaApi = null;

  private CryptoWatchApi getCrypowatchApi() {
    if (cryptoWatchApi == null) {
      cryptoWatchApi = new CryptoWatchApi(appContext.getCwRest());
    }
    return cryptoWatchApi;
  }
  

  private CryptopiaApi getCryptopiaApi() {
    if (cryptopiaApi == null) {
      cryptopiaApi = new CryptopiaApi(appContext.getCryptopiaRest());
    }
    return cryptopiaApi;
  }
  
  //NOTE:
  //periodStr is in terms of seconds: 86400 is one day (represents interval of time for candle data)
  //begin and end can be taken in in date format: 10/27/1998 (MM/dd/YYYY)
  //begin and end are in UNIX timestamp format (converter: https://www.epochconverter.com/)
  //Assumes hour 0 of each day
  @CrossOrigin(origins="http://localhost:8080")
  @RequestMapping(value="/exchange/candle", method=RequestMethod.GET)
  public @ResponseBody CandleStickSeries getCandle(
      @RequestParam(value="fromCur", required=true) String fromCur,
      @RequestParam(value="toCur", required=true) String toCur,
      @RequestParam(value="exchange", required=true) String exchange,
      @RequestParam(value="period", required=false, defaultValue="") String periodStr,
      @RequestParam(value="begin", required=false, defaultValue="-1") long begin,
      @RequestParam(value="end", required=false, defaultValue="-1") long end) {

    String marketTicket = fromCur + toCur;

    System.out.println("Got request");
    List<String> periods = Arrays.asList(periodStr.split(","));
    CandleStickSeries candles = getCrypowatchApi().getCandlestick(marketTicket, exchange, periods, end, begin);
    if (candles == null) {
      throw new IllegalStateException();
    }

    System.out.println("Got response");

    return candles;
  }
  
  
  //access Trading historical 
  @CrossOrigin(origins="http://localhost:8080")
  @RequestMapping(value="/exchange/historicalData", method=RequestMethod.GET)
  public @ResponseBody TradeSeries getHistoricalData(
      @RequestParam(value="fromCur", required=true) String fromCur,
      @RequestParam(value="toCur", required=true) String toCur) {

    String marketTicket = fromCur + "_" + toCur;

    System.out.println("Got request");
    TradeSeries trades = getCryptopiaApi().getHistoricalData(marketTicket);
    if (trades == null) {
      throw new IllegalStateException();
    }

    System.out.println("Got response");

    return trades;
  }
  
  //print Moving Average Data
  @CrossOrigin(origins="http://localhost:8080")
  @RequestMapping(value="/exchange/movingAverage", method=RequestMethod.GET)
  public @ResponseBody Map<Integer, TimeSeries> getMovingAverage(
      @RequestParam(value="intervals",required=true) String intervals,
      @RequestParam(value="exchange", required=true) String exchange,
      @RequestParam(value="duration",required=true) long duration,
      @RequestParam(value="fromCur", required=true) String fromCur,
      @RequestParam(value="toCur", required=true) String toCur) {

    List<String> intervalStrs = Arrays.asList(intervals.split(","));

    List<Integer> movingAverageIntervals = new ArrayList<Integer>();
    for (String intervalStr : intervalStrs) {
      try {
        Integer addVal = Integer.valueOf(intervalStr);
        movingAverageIntervals.add(addVal);
      }
      catch (NumberFormatException nfe) {
        // Just ignore
      }
    }

    //constructor takes in entire graph's duration
    //can delete!!
    long beforeUnixTime = System.currentTimeMillis();
    long currentUnixTime = System.currentTimeMillis() / 1000;
    
    if (duration <= 0) {
      return new HashMap<Integer, TimeSeries>();
    }
    MovingAverageCalculator movingAverageCalculator = new MovingAverageCalculator(currentUnixTime, duration, exchange, movingAverageIntervals, fromCur, toCur); 
    Map<Integer, TimeSeries> seriesMap = movingAverageCalculator.getSeries();
    
    for (Integer key : seriesMap.keySet()) {
      TimeSeries intervalSeriesArray = seriesMap.get(key);
//      System.out.println("-------------------------------------------------");
//      System.out.println("-------------------------------------------------");
//      System.out.println("-------------------------------------------------");
      System.out.println("TimeSeriesResponse: for moving interval: " + key + " size: " + intervalSeriesArray.size());
//      intervalSeriesArray.print();
    }
    
    System.out.println("Got response");
    long afterUnixTime = System.currentTimeMillis();
    
    System.out.println("Time for moving averages: " + (afterUnixTime - beforeUnixTime) / 1000);
    return seriesMap;
  }

  @CrossOrigin(origins="http://localhost:8080")
  @RequestMapping(value="/exchange/all", method=RequestMethod.GET)
  public @ResponseBody Vector<CurrencyTicker> getAllTickets() {
    System.out.println("Getting all currency tickers");
    GetCurrencyInformation currencyInfo = new GetCurrencyInformation();

    return currencyInfo.getAllTickers();
  }

  @CrossOrigin(origins="http://localhost:8080")
  @RequestMapping(value="/exchange/markets", method=RequestMethod.GET)
  public @ResponseBody List<Market> getMarkets(@RequestParam(value="allowedTos", required=true) String tosAllowedStr) {
    List<Market> markets = getCrypowatchApi().getMarkets();
    if (markets == null) {
      throw new IllegalStateException();
    }

    List<String> allowedTos = Arrays.asList(tosAllowedStr.split(","));

    return Market.filter(markets, allowedTos);
  }

  @CrossOrigin(origins="http://localhost:8080")
  @RequestMapping(value="/exchange/summary", method=RequestMethod.GET)
  public @ResponseBody MarketSummary getMarketSummary(
      @RequestParam(value="fromCur", required=true) String fromCur,
      @RequestParam(value="toCur", required=true) String toCur,
      @RequestParam(value="exchange", required=true) String exchange) {

    return getCrypowatchApi().getMarketSummary(fromCur, toCur, exchange, appContext.getGson());
  }

  @ExceptionHandler(ApiException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public @ResponseBody void handleException(ApiException e) {
    // Do nothing
  }

  @ExceptionHandler(IllegalStateException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) public @ResponseBody void handleException(IllegalStateException e) {
    // Do nothing
  }

}
