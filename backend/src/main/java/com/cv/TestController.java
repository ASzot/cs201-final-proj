package com.cv;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cv.util.Rest;
import com.google.gson.JsonObject;

@Controller
@RequestMapping("/test")
public class TestController {

  @CrossOrigin(origins="http://localhost:8081")
  @RequestMapping(method=RequestMethod.GET)
  public @ResponseBody TestObj getTest() {
    JsonObject obj = Rest.rest.post("/exchanges");
	  return new TestObj(obj.toString());
  }
  
//  @CrossOrigin(origins="http://localhost:8080")
//  @RequestMapping(method=RequestMethod.GET)
//  public @ResponseBody JsonObject getSupportedExchanges() {
//   TestData td  = new TestData("hello", "Andrew");
//   return td.getSupportedExchanges();
//  }


}
