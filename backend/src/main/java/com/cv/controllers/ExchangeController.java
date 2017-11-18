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

import com.cv.cryptowatch.CryptoWatchApi;
import com.cv.model.CandleStickSeries;
import com.cv.util.DateToUnix;

@Controller
public class ExchangeController {
  @Autowired
  private AppContext appContext;

  private CryptoWatchApi api = null;

  private CryptoWatchApi getApi() {
    if (api == null) {
      api = new CryptoWatchApi(appContext.getCwRest());
    }
    return api;
  }
  
  //NOTE:
  //periodStr is in terms of seconds: 86400 is one day (represents interval of time for candle data)
  //begin and end are in UNIX timestamp format (converter: //https://www.epochconverter.com/)
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

    List<String> periods = Arrays.asList(periodStr.split(","));
    long before = end;
    long after = begin;
    CandleStickSeries candles = getApi().getCandlestick(marketTicket, periods, before, after);
    if (candles == null) {
      throw new IllegalStateException();
    }

	  return candles;
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
