package com.cv.response;

import java.util.Vector;

public class UserSearchResponse {
  private Vector<String> allUsers;

  public UserSearchResponse(Vector<String> allUsers) {
    this.allUsers = allUsers; 
  }

  public Vector<String> getUsers() {
    return allUsers;
  }
}
