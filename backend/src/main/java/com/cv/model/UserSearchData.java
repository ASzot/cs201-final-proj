package com.cv.model;

public class UserData {
  private String username;

  public UserData(String username) {
    this.username = username;
  }

  public UserData() {
    this("");
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
