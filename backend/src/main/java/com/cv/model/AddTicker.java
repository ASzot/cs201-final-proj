package com.cv.model;

public class AddTicker {
	String ticker;
	String userID;
	
	public AddTicker(String ticker, String userID){
		this.ticker = ticker;
		this.userID = userID; 
	}

	public AddTicker() {
    	this("", "");
  	}

	public String getTicker() {
		return ticker;
	}

	public String getUserID(){
		return userID; 
	}
	
	public void setTicker(String ticker){
		this.ticker = ticker; 
	}

	public void setUserID(String userID){
		this.userID = userID; 
	}
}
