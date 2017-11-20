package com.cv.JDBCTesting;

import java.util.List;
import java.util.Vector;

import com.cv.jdbc.*;
import com.cv.jdbc.get.GetCurrencyInformation;
import com.cv.jdbc.get.ValidateUser;
import com.cv.jdbc.get.WatchListGetters;
import com.cv.jdbc.get.getToFromInformation;
import com.cv.jdbc.scripts.AddToFromExchangeInCurrencyInfo;
import com.cv.jdbc.set.WatchListSetters;
import com.cv.model.Market;
import com.cv.jdbc.set.AddUser;



public class Testing {
	
	public static void main (String [] Args) {
		
		/*WatchListSetters testGetWatchListInt = new WatchListSetters();
		System.out.println(testGetWatchListInt.addTickerStringToWatchList("testUser1","AMC")); //false
		System.out.println(testGetWatchListInt.addTickerStringToWatchList("INVALIDUSER","ATM")); //false
		System.out.println(testGetWatchListInt.addTickerStringToWatchList("TestUser1","ATM"));//false
		System.out.println(testGetWatchListInt.addTickerStringToWatchList("a","GARBAGE"));//false
		System.out.println(testGetWatchListInt.addTickerStringToWatchList("a","BTC"));//false
*/		
		
		
		
		/*WatchListGetters testGetWatchListInt = new WatchListGetters();
		Vector<String> myUsersWatchList = testGetWatchListInt.getUserWatchListString("testUser1");
		for (int i=0; i<myUsersWatchList.size(); i++) {
			System.out.println("testUser1 watch list " + myUsersWatchList.get(i));
		}
		Vector<String> myUsersWatchList1 = testGetWatchListInt.getUserWatchListString("INVALIDUSER");
		for (int i=0; i<myUsersWatchList1.size(); i++) {
			System.out.println("INVALIDUSER watch list " + myUsersWatchList1.get(i));
		}
		Vector<String> myUsersWatchList2 = testGetWatchListInt.getUserWatchListString("a");
		for (int i=0; i<myUsersWatchList2.size(); i++) {
			System.out.println("a watch list " + myUsersWatchList2.get(i));
		}*/
		
		/*AddToFromExchangeInCurrencyInfo AddToFromExchangeTesting = new AddToFromExchangeInCurrencyInfo();
		
		List<Market> testingAllMarketsNotThreeCharTickers = AddToFromExchangeTesting.getMarketsWhereToIsThreeChar();
		System.out.println(testingAllMarketsNotThreeCharTickers.size());
		*/
		
		/*List<Market> testingAllMarkets = gtfi.getAllMarkets();
		System.out.println(testingAllMarkets.size());*/
		
		
		/*ValidateUser myUser = new ValidateUser();
		
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
		
		WatchListSetters wl = new WatchListSetters();
		wl.addToWatchList(1, 101);*/
		
		/*ValidateUser myUser = new ValidateUser();
		
		System.out.println(myUser.validateUsernameAndPassword("testUser4", "testing"));
		System.out.println("Valid user ID? "+myUser.isValidUserID(1));
		System.out.println("Valid user ID? "+myUser.isValidUserID(50));
		
	 	AddUser addNewUser = new AddUser();
		System.out.println("Able to add new User? "+addNewUser.addUser("testUser4", "testing"));
		
		GetCurrencyInformation gci = new GetCurrencyInformation();
		System.out.println(gci.getIDFromTicker("BTC"));
		System.out.println(gci.getTickerFromID(101));
		System.out.println("Is Valid CurrencyID? "+gci.isValidCurrencyID(1));
		System.out.println("Is Valid CurrencyID? "+gci.isValidCurrencyID(0));
		*/
		/*WatchListSetters wl = new WatchListSetters();
		System.out.println(wl.addToWatchList(15, 101));
		System.out.println(wl.addToWatchList(15, 100));
		System.out.println(wl.addToWatchList(14, 100));
		
		
		System.out.println(wl.addToWatchList(13, 100));
		System.out.println(wl.addToWatchList(15, -1));
		System.out.println(wl.addToWatchList(13, -1));
		System.out.println(wl.addToWatchList(15, 222));*/
		
		/*
		
		Vector<CurrencyID_Ticker> testingVector = gci.getAllTickers();
		
		for(int i=0; i<testingVector.size(); i++) {
			System.out.println("Ticker ID: "+testingVector.get(i).getID()+" || "+"Ticker Name: "+testingVector.get(i).getTicker());
		}*/
		
		/*ValidateUser myUser = new ValidateUser();
		System.out.println("The user andy was found" + myUser.isExistingUsername("andy"));
		
		
		System.out.println(myUser.getUserID("andy"));*/
		
		/*Search mySearch = new Search();
		
		Vector <String> mySimilarUsers = mySearch.searchForSimilarUser("u");
		for (int i =0; i<mySimilarUsers.size(); i++) {
			System.out.println(mySimilarUsers.get(i));
		}
		*/
		
		/*WatchListGetters myWatchList = new WatchListGetters();
		System.out.println(myWatchList.isAlreadyInWatchList(11, 101));
		System.out.println(myWatchList.isAlreadyInWatchList(15, 100));
		System.out.println(myWatchList.isAlreadyInWatchList(14, 100));
		System.out.println(myWatchList.isAlreadyInWatchList(14, 101));*/
	
	}
	
	
}

