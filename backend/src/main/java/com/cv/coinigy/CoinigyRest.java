package com.cv.coinigy;

import org.apache.http.client.methods.HttpPost;
import com.cv.util.RestUtil;

public class CoinigyRest extends RestUtil {
  private String apiKey;
  private String apiSecret;
  private String baseURL = "https://api.coinigy.com/api/v1";

  public CoinigyRest(String apiKey, String apiSecret) {
    this.apiKey = apiKey;
    this.apiSecret = apiSecret;
  }

  protected HttpPost generatePost(String endpoint) {
    HttpPost post = new HttpPost(baseURL + endpoint);
	  post.addHeader("Content-Type", "application/json");
	  post.addHeader("X-API-KEY", this.apiKey);
	  post.addHeader("X-API-SECRET", this.apiSecret);

    return post;
  }
}
