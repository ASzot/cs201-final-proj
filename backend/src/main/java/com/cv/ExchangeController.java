package com.cv;

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

import com.cv.model.TradeSeries;
import com.cv.util.Rest;
import com.google.gson.JsonObject;

@Controller
public class ExchangeController {
  @Autowired
  private AppContext appContext;

  @CrossOrigin(origins="http://localhost:8081")
  @RequestMapping(value="/exchange/data", method=RequestMethod.GET)
  public @ResponseBody TradeSeries getMarketHistory(
      @RequestParam(value="fromCur", required=true) String fromCur,
      @RequestParam(value="toCur", required=true) String toCur,
      @RequestParam(value="time", required=true) int timespan) {

    JsonObject response = Rest.rest.get("/GetMarketHistory/" + fromCur + "_" + toCur + "/" + timespan );
    boolean successful = response.get("Success").getAsBoolean();

    if (!successful) {
      throw new IllegalStateException();
    }

    String dataJson = response.get("Data").toString();

    TradeSeries tradeSeries = TradeSeries.fromJson(dataJson, appContext.getGson());

	  return tradeSeries;
  }

  @ExceptionHandler(IllegalStateException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public @ResponseBody void handleException(IllegalStateException e) {
    // Do nothing
  }

}
