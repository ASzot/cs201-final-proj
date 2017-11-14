package com.cv;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;

@Controller
@RequestMapping("/test")
public class TestController {

  @CrossOrigin(origins="http://localhost:8081")
  @RequestMapping(method=RequestMethod.GET)
  public @ResponseBody TestData getTest(
      @RequestParam(value="name", required=false) String name) {
	   
//	  TestData td = new TestData("hello", name);
//	  JsonObject data = null;
//	  String market = "ltcbtc";
//	  System.out.println("ALL EXCHANGES");
//	  data = td.getExchanges();
//	  System.out.println(data);
//	  System.out.println("----------------------------------");
//	  System.out.println("SUPPORTED MARKETS");
//	  data = td.getMarkets();
//	  System.out.println(data);
//	  System.out.println("----------------------------------");
//	  System.out.println("ALL MARKET DATA URLS");
//	  data = td.getMarketDataURLs(market);
//	  System.out.println(data);
//	  System.out.println("----------------------------------");
//	  System.out.println("LAST PRICE");
//	  data = td.getLastPrice(market);
//	  System.out.println(data);
//	  System.out.println("----------------------------------");
//	  System.out.println("CURRENCY SUMMARY");
//	  data = td.getCurrencySummary(market);
//	  System.out.println(data);
//	  System.out.println("----------------------------------");
//	  System.out.println("TRADE SUMMARY: ID, Timestamp, Price, Amount");
//	  data = td.getCurrencySummary(market);
//	  System.out.println(data);
//	  System.out.println("----------------------------------");
//	  System.out.println("RETRIEVE ORDERS: ");
//	  data = td.getOrders(market);
//	  System.out.println(data);
////	  System.out.println("----------------------------------");
////	  System.out.println("RETRIEVE CANDLESTICK: ");
////	  data = td.getCandlestick(market);
////	  System.out.println(data);
//	  System.out.println("----------------------------------");
//	  System.out.println("RETRIEVE AGGREGATE CURRENCIES: ");
//	  data = td.getAggregateMarketData();
//	  System.out.println(data);
////    System.out.println("----------------------------------");
////    System.out.println("RETRIEVE AGGREGATE SUMMARIES: ");
////    data = td.getAggregateMarketSummaries();
////	  System.out.println(data);
	    
	  
	  
    return new TestData("hello", name);
  }
  
  

  
  
  
  
//  @CrossOrigin(origins="http://localhost:8080")
//  @RequestMapping(method=RequestMethod.GET)
//  public @ResponseBody JsonObject getSupportedExchanges() {
//   TestData td  = new TestData("hello", "Andrew");
//   return td.getSupportedExchanges();
//  }


}
