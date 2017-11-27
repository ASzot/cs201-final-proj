package com.cv.response;

public class DefaultResponse {
  private String msg;
  private boolean okay;

  public DefaultResponse(boolean okay) {
    this(okay, "");
  }

  public DefaultResponse(boolean okay, String msg) {
    this.okay = okay;
    this.msg = msg;
  }

  public String getMsg() {
    return msg;
  }

  public boolean getOkay() {
    return okay;
  }
}
