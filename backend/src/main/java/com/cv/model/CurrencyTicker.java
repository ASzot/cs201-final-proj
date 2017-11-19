package com.cv.model;

public class CurrencyTicker {
	int ID;
	String ticker;
	
	public CurrencyTicker(int ID, String ticker){
		this.ID = ID;
		this.ticker = ticker;
	}
	
	
	public int getID() {
		return ID;
	}
	
	public String getTicker() {
		return ticker;
	}
	
}
