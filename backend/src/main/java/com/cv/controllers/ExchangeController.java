package com.cv.controllers;

import java.util.Arrays;
import java.util.List;

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
import com.cv.model.CandleStickSeries;
import com.cv.model.TradeSeries;
import com.cv.util.DateToUnix;

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
      @RequestParam(value="period", required=false, defaultValue="") String periodStr,
      @RequestParam(value="begin", required=false, defaultValue="-1") long begin,
      @RequestParam(value="end", required=false, defaultValue="-1") long end) {

    String marketTicket = fromCur + toCur;

    System.out.println("Got request");
    List<String> periods = Arrays.asList(periodStr.split(","));
    CandleStickSeries candles = getCrypowatchApi().getCandlestick(marketTicket, periods, begin, end);
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