package com.cv.model;

public class UserData {
  private String username;
  private String password;

  public UserData(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public UserData() {
    this("", "");
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

public String getPassword() {
    return password;
  }
}
