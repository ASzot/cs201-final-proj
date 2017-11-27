package com.cv.controllers;

import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cv.AppContext;
import com.cv.DefaultResponse;
import com.cv.UserSearchResponse;

// import com.cv.jdbc.get.UserSearch;
// import com.cv.jdbc.get.ValidateUser;
// import com.cv.jdbc.set.AddUser;
import com.cv.model.UserData;
import com.cv.model.UserSearchData;
import com.cv.model.AddTicker;
import com.cv.jdbc.set.*; 
import com.cv.jdbc.get.*;
import com.cv.UserWatchListResponse; 
//import com.cv.model.UserSearchResponse;

@Controller
public class UserController {
  @Autowired
  private AppContext appContext;
  private Vector<String> allWatchLists = new Vector<String>(); 

  @CrossOrigin(origins="http://localhost:8080")
  @RequestMapping(value="/user/login", method=RequestMethod.POST)
  public @ResponseBody DefaultResponse loginUser(
      @RequestBody UserData userData) {
    
    System.out.println("Got login request");
    ValidateUser validateUser = new ValidateUser();
    System.out.println("Username and password in login controller: " + userData.getUsername() + " and the password is: " + userData.getPassword());
    boolean okay = validateUser.validateUsernameAndPassword(userData.getUsername(), userData.getPassword());
    System.out.println("Testing boolean: " + okay);
    if(okay == true){
      allWatchLists = new Vector<String>(); 
    }
    return new DefaultResponse(okay);
  }

  @CrossOrigin(origins="http://localhost:8080")
  @RequestMapping(value="/user/create", method=RequestMethod.POST)
  public @ResponseBody DefaultResponse createUser(
      @RequestBody UserData userData) {

    System.out.println("Got request to createUser");
    AddUser addUser = new AddUser();
    System.out.println(userData.getUsername() + " and the password is: " + userData.getPassword());
    boolean okay = addUser.addUser(userData.getUsername(), userData.getPassword());
    System.out.println(okay);
    return new DefaultResponse(okay);
  }
  
 @CrossOrigin(origins="http://localhost:8080")
 @RequestMapping(value="/user/search", method=RequestMethod.POST)
 public @ResponseBody UserSearchResponse createUser(
     @RequestBody UserSearchData userSearchData) {

   System.out.println("Got request to search for user");
   UserSearch userSearch = new UserSearch(); 
   Vector<String> allUsers = userSearch.searchForSimilarUser(userSearchData.getUsername());
   return new UserSearchResponse(allUsers);
 }
 
 @CrossOrigin(origins="http://localhost:8080")
 @RequestMapping(value="/user/addTicker", method=RequestMethod.POST)
 public @ResponseBody DefaultResponse createUser(
     @RequestBody AddTicker addTicker) {
   //String parsedTicker = tickerName.substring(11, tickerName.lastIndexOf('"'));
   // WatchListSetters watchListSetters = new WatchListSetters(); 
   // System.out.println("Printing ticker value: " + addTicker.getTicker() + " printing userid value: " + addTicker.getUserID());
   // boolean okay = watchListSetters.addTickerStringToWatchList(addTicker.getUserID(), addTicker.getTicker()); 
   // System.out.print("testing add return value: " + okay);
   // System.out.println(okay);
   boolean okay = true; 
   for(int i = 0; i < allWatchLists.size(); i++){
      if(allWatchLists.get(i).equals(addTicker.getTicker())){
        okay = false; 
      }
   }
   if(okay == true){
    allWatchLists.add(addTicker.getTicker());
   }
   return new DefaultResponse(okay);
 }

 @CrossOrigin(origins="http://localhost:8080")
 @RequestMapping(value="/user/ViewWatchList", method=RequestMethod.POST)
 public @ResponseBody Vector<String> createUser(
     @RequestBody String username) {
   //String parsedTicker = tickerName.substring(11, tickerName.lastIndexOf('"'));
   // WatchListGetters watchListGetters = new WatchListGetters(); 
   // String parsedUsername = username.substring(13, username.lastIndexOf('"'));
   // System.out.println("Printing username for viewwatchlist: " + parsedUsername);
   // //boolean okay = watchListGetters.addTickerStringToWatchList(addTicker.getUserID(), addTicker.getTicker()); 
   // Vector<String> allCurrencies = watchListGetters.getUserWatchListString(parsedUsername);
   // System.out.println("all currencies size: " + allCurrencies.size());
   // String allStringCurrency = ""; 
   // for(int i = 0; i < allCurrencies.size(); i++){
   //  System.out.println("Following currency: " + allCurrencies.get(i));
   //  allStringCurrency += (allCurrencies.get(i) + ",");
   // }
   // //System.out.print("testing add return value: " + okay);
   //  //System.out.println(allStringCurrency);
   // //return new UserWatchListResponse(allCurrencies);
   //  //return allStringCurrency; 
   //  System.out.println("All Watch Lists: " + allWatchLists); 
    return allWatchLists; 
    //return allCurrencies; 
 }

//vector<String> watchListGetters.getUserWatchListString(String username);

}
