//package com.cv;
//
//import java.util.Vector;
//
//public class UserSearchResponse {
//  private Vector<String> allUsers;
//
//  public DefaultResponse(Vector<String> allUsers) {
//    this.allUsers = allUsers; 
//  }
//
//  public String getUsers() {
//    return allUsers;
//  }
//}
package com.cv;

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
