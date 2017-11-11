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

  @CrossOrigin(origins="http://localhost:8080")
  @RequestMapping(method=RequestMethod.GET)
  public @ResponseBody TestData getTest(
      @RequestParam(value="name", required=false) String name) {
	   
	  TestData td = new TestData("hello", name);
	  JsonObject data = null;
	  data = td.retrieveExchanges();
	  System.out.println(data.get("data").getAsJsonArray().get(0));
	  System.out.println("----------------------------------");
	  data = td.retrieveMarkets();
      System.out.println(data.get("data").getAsJsonArray().get(0));
      System.out.println("----------------------------------");
      data = td.retrieveMarketData("BTC/USD");
      System.out.println(data);
	  
    return new TestData("hello", name);
  }
  
  

  
  
  
  
//  @CrossOrigin(origins="http://localhost:8080")
//  @RequestMapping(method=RequestMethod.GET)
//  public @ResponseBody JsonObject getSupportedExchanges() {
//   TestData td  = new TestData("hello", "Andrew");
//   return td.getSupportedExchanges();
//  }


}
