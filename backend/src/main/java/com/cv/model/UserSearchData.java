package com.cv.model;

public class UserSearchData {
  private String username;

  public UserSearchData(String username) {
    this.username = username;
  }

  public UserSearchData() {
    this("");
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
