package com.cv.jdbc.get;

public class CurrencyID_Ticker {
	int ID;
	String ticker;
	
	public CurrencyID_Ticker(int ID, String ticker){
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
