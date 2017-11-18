package com.cv.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cv.AppContext;
import com.cv.DefaultResponse;
import com.cv.jdbc.get.ValidateUser;
import com.cv.jdbc.set.AddUser;

@Controller
public class UserController {
  @Autowired
  private AppContext appContext;

  @CrossOrigin(origins="http://localhost:8080")
  @RequestMapping(value="/user/login", method=RequestMethod.POST)
  public @ResponseBody DefaultResponse loginUser(
      @RequestParam(value="username", required=true) String username,
      @RequestParam(value="password", required=false) String password) {
    
    boolean okay = ValidateUser.validateUsernameAndPassword(username, password);

    return new DefaultResponse(okay);
  }

  @CrossOrigin(origins="http://localhost:8080")
  @RequestMapping(value="/user/create", method=RequestMethod.POST)
  public @ResponseBody DefaultResponse loginUser(
      @RequestParam(value="username", required=true) String username,
      @RequestParam(value="password", required=false) String password) {

    boolean okay = AddUser.addUser(username, password);

    return new DefaultResponse(okay);
  }
}
