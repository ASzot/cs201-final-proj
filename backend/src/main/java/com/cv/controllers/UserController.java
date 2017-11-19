package com.cv.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cv.AppContext;
import com.cv.DefaultResponse;
import com.cv.jdbc.get.ValidateUser;
import com.cv.jdbc.set.AddUser;
import com.cv.model.UserData;

@Controller
public class UserController {
  @Autowired
  private AppContext appContext;

  @CrossOrigin(origins="http://localhost:8080")
  @RequestMapping(value="/user/login", method=RequestMethod.POST)
  public @ResponseBody DefaultResponse loginUser(
      @RequestBody UserData userData) {
    
    ValidateUser validateUser = new ValidateUser();
    boolean okay = validateUser.validateUsernameAndPassword(userData.getUsername(), userData.getPassword());

    return new DefaultResponse(okay);
  }

  @CrossOrigin(origins="http://localhost:8080")
  @RequestMapping(value="/user/create", method=RequestMethod.POST)
  public @ResponseBody DefaultResponse createUser(
      @RequestBody UserData userData) {

    System.out.println("Got request to createUser");
    AddUser addUser = new AddUser();
    boolean okay = addUser.addUser(userData.getUsername(), userData.getPassword());

    return new DefaultResponse(okay);
  }
  
  @CrossOrigin(origins="http://localhost:8080")
  @RequestMapping(value="/user/search", method=RequestMethod.POST)
  public @ResponseBody DefaultResponse createUser(
      @RequestBody UserSearchData userSearchData) {

    System.out.println("Got request to search for user");
    UserSearch userSearch = new UserSearch(); 
    Vector<String> allUsers = userSearch.searchForSimilarUser(userSearchData.getUsername());
    return new UserSearchResponse(allUsers);
  }
}
