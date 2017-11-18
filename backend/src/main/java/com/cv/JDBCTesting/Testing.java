package com.cv.JDBCTesting;

import com.cv.jdbc.*;
import com.cv.jdbc.get.GetCurrencyInformation;
import com.cv.jdbc.get.ValidateUser;
import com.cv.jdbc.set.WatchList;
import com.cv.jdbc.set.AddUser;



public class Testing {
	
	public static void main (String [] Args) {
		ValidateUser myUser = new ValidateUser();
		
		System.out.println(myUser.validateUsernameAndPassword("testUser4", "testing"));
		System.out.println("Valid user ID? "+myUser.isValidUserID(1));
		System.out.println("Valid user ID? "+myUser.isValidUserID(50));
		
	 	AddUser addNewUser = new AddUser();
		System.out.println("Able to add new User? "+addNewUser.addUser("testUser4", "testing"));
		
		GetCurrencyInformation gcIDft = new GetCurrencyInformation();
		System.out.println(gcIDft.getIDFromTicker("BTC"));
		System.out.println(gcIDft.getTickerFromID(101));
		System.out.println("Is Valid CurrencyID? "+gcIDft.isValidCurrencyID(1));
		System.out.println("Is Valid CurrencyID? "+gcIDft.isValidCurrencyID(0));
		
		WatchList wl = new WatchList();
		wl.addToWatchList(1, 101);
	
	}
	
	
}

