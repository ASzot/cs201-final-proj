package com.cv.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class RestUtil {
  public enum REQUEST_TYPE {
    POST
  };

  private HttpClient client = HttpClientBuilder.create().build();

  protected HttpPost generatePost(String endpoint) {
    throw new IllegalStateException("No default implementation for RestUtil.generatePost!");
  }

  public JsonObject post(String endpoint) {
    return makeRequest(endpoint, REQUEST_TYPE.POST);
  }

  public JsonObject makeRequest(String endpoint, REQUEST_TYPE type) {
    try {
      HttpUriRequest uriRequest = null;
      if (type == REQUEST_TYPE.POST) {
          uriRequest = generatePost(endpoint);
      }
      else {
        throw new IllegalArgumentException("Invalid request type " + type);
      }

      HttpResponse response = client.execute(uriRequest);

      if (response.getStatusLine().getStatusCode() == 200) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
        StringBuilder builder = new StringBuilder();
        for (String line = null; (line = reader.readLine()) != null;) {
          builder.append(line).append("\n");
        }

        String responseStr = builder.toString();
        JsonObject convertedObject = new Gson().fromJson(responseStr, JsonObject.class);
        return convertedObject;
      }
      else {
        return null;
      }
    }
    catch (Exception e) {
      return null;
    }
  }

}
