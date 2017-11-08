package com.cv;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;



@Controller
@RequestMapping("/test")
public class TestController {

  @CrossOrigin(origins="http://localhost:8080")
  @RequestMapping(method=RequestMethod.GET)
  public @ResponseBody TestData getTest(
      @RequestParam(value="name", required=false) String name) {
	   
//	  TestData td = new TestData("hello", name);
	  JsonObject json = getSupportedExchanges();
	  System.out.println(json);
	  
    return new TestData("hello", name);
  }
  
  
  /* --------- Example API Call -------------- */
	private ResponseHandler<JsonObject> responseHandler;
	private CloseableHttpClient httpclient;

	public JsonObject getSupportedExchanges() {
		httpclient = HttpClients.createDefault();
		
		responseHandler = new ResponseHandler<JsonObject>() {
		    @Override
		    public JsonObject handleResponse(
		            final HttpResponse response) throws IOException {
		        StatusLine statusLine = response.getStatusLine();
		        HttpEntity entity = response.getEntity();
		        if (statusLine.getStatusCode() >= 300) {
		            throw new HttpResponseException(
		                    statusLine.getStatusCode(),
		                    statusLine.getReasonPhrase());
		        }
		        if (entity == null) {
		            throw new ClientProtocolException("Response contains no content");
	        }
	        Gson gson = new GsonBuilder().create();
	
	        Reader reader = new InputStreamReader(entity.getContent());
	        return gson.fromJson(reader, JsonObject.class);
	    }
	};
	
		HttpPost httpPost = new HttpPost("https://api.coinigy.com/api/v1/exchanges");
		httpPost.addHeader("content-type", "application/json");
		httpPost.addHeader("X-API-KEY", "211c66b10d53944dcd6e69b181d078d0");
		httpPost.addHeader("X-API-SECRET", "ea5311c878ecdbe7d4587ef668b12d5d");
		
	    
		try {
			JsonObject myjson = null;
			myjson = httpclient.execute(httpPost, responseHandler);
	    		System.out.println(myjson);
	    		
	    		return myjson;
	
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    	
	    	return null;
	}
  
  
  
  
//  @CrossOrigin(origins="http://localhost:8080")
//  @RequestMapping(method=RequestMethod.GET)
//  public @ResponseBody JsonObject getSupportedExchanges() {
//   TestData td  = new TestData("hello", "Andrew");
//   return td.getSupportedExchanges();
//  }


}
